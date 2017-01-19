package com.example.administrator.izienglish.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.SqlHelper;
import com.example.administrator.izienglish.adapters.HomePagerAdapter;
import com.example.administrator.izienglish.fragments.AnswerQuizFragment;
import com.example.administrator.izienglish.fragments.FavoriteFragment;
import com.example.administrator.izienglish.fragments.FavoriteFragment_;
import com.example.administrator.izienglish.fragments.GrammarFragment;
import com.example.administrator.izienglish.fragments.GrammarFragment_;
import com.example.administrator.izienglish.fragments.ListQuestionFragment;
import com.example.administrator.izienglish.fragments.ListQuestionFragment_;
import com.example.administrator.izienglish.fragments.QuizFragment;
import com.example.administrator.izienglish.fragments.QuizFragment_;
import com.example.administrator.izienglish.fragments.ResultDialogFragment;
import com.example.administrator.izienglish.fragments.ResultDialogFragment_;
import com.example.administrator.izienglish.fragments.SettingFragment;
import com.example.administrator.izienglish.fragments.SettingFragment_;
import com.example.administrator.izienglish.fragments.VerbFragment;
import com.example.administrator.izienglish.fragments.VerbFragment_;
import com.example.administrator.izienglish.model.Question;
import com.example.administrator.izienglish.model.Verbs;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@EActivity
public class MainActivity extends AppCompatActivity implements AnswerQuizFragment.SendData, QuizFragment.SendData, ResultDialogFragment.OnHeadlineSelectedListener {
    public static final String ROOT_CHILD = "Question";
    public static final String UNDER_CHILD = "Part1";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_BUNDLE = "bundle";
    private Firebase mRoot;
    private ArrayList<Question> mQuestions = new ArrayList<Question>();
    private List<String> mTitles = new ArrayList<String>();
    private HomePagerAdapter mAdapter;
    private FragmentManager mFm;
    public static final String TRUE_ANSWER = "T";
    public static final String FALSE_ANSWER = "F";
    private String[] mResultArray;
    private String[] mSelectedAnswers;
    public static final int QUANTITY_QUESTION = 10;
    public static final String NOTIFY_NULL = "You must answer all questions";
    private int mFlag = 1;
    private SqlHelper mDb;
    private ArrayList<Verbs> mIrreVerbs;
    @ViewById(R.id.tabs)
    TabLayout mTab;
    android.view.View mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @AfterViews
    public void Init() {
        //Dung firebase
        Firebase.setAndroidContext(this);
        mRoot = new Firebase("https://test-firebase-c80fc.firebaseio.com/");
        //khoi tao tieu de va fakeData
        FakeData();
        // khoi tao mang chua cau dung
        InitResultArrays();
        // khoi tao mang save answer
        mSelectedAnswers = new String[QUANTITY_QUESTION];
        // khoi tao mang irregular verb
        getDataIrregularVerb();
        mFm = getSupportFragmentManager();
        InitSelectedAnswers();
        getFirebaseData();
        //tao ra tab
        setupTabIcon();
        /*Khi co them splash*/
//        Intent intent = getIntent();
//        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
//        mQuestions = bundle.getParcelableArrayList(KEY_QUESTION);
//        Log.i("MainACTIVITY",mQuestions.size()+"");

        //initial first page
        GrammarFragment frag = new GrammarFragment_().builder().build();
        mFm.beginTransaction().replace(R.id.Container, frag).commit();
        mTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getTabPosition(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setupTabIcon() {
        mTab.addTab(mTab.newTab().setText("Grammar").setIcon(R.drawable.literature_filled_50));
        mTab.addTab(mTab.newTab().setText("Irregular Verb").setIcon(R.drawable.pencil_48));
        mTab.addTab(mTab.newTab().setText("Favorite").setIcon(R.drawable.ic_favorite_purple_48dp));
        mTab.addTab(mTab.newTab().setText("Quiz").setIcon(R.drawable.test_passed_filled_50));
        mTab.addTab(mTab.newTab().setText("Setting").setIcon(R.drawable.settings_filled_50));

    }

    public void getTabPosition(int position) {
        switch (position) {
            case 0:
                GrammarFragment frag = new GrammarFragment_().builder().build();
                mFm.beginTransaction().replace(R.id.Container, frag).commit();
                break;
            case 1:
                VerbFragment frag1 = new VerbFragment_().builder().mVerbs(mIrreVerbs).build();
                mFm.beginTransaction().replace(R.id.Container, frag1).commit();
                break;
            case 2:
                FavoriteFragment frag2 = new FavoriteFragment_().builder().mVerbs(mIrreVerbs).build();
                mFm.beginTransaction().replace(R.id.Container, frag2).commit();
                break;
            case 3:
                mFlag = 1;
                ListQuestionFragment frag3 = new ListQuestionFragment_().builder().mQuestions(mQuestions).mFlag(mFlag).mSelectedAnswers(mSelectedAnswers).build();
                mFm.beginTransaction().replace(R.id.Container, frag3).commit();
                break;
            case 4:
                SettingFragment frag4 = new SettingFragment_().builder().build();
                mFm.beginTransaction().replace(R.id.Container, frag4).commit();
                break;
        }
    }

    public void getDataIrregularVerb() {
        mIrreVerbs = new ArrayList<Verbs>();
        mDb = new SqlHelper(getBaseContext());
        mIrreVerbs = mDb.getData();
    }

    public void getFirebaseData() {
        Firebase firebase = mRoot.child(ROOT_CHILD).child(UNDER_CHILD);
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Question q = dataSnapshot.getValue(Question.class);
                mQuestions.add(q);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void InitResultArrays() {
        mResultArray = new String[QUANTITY_QUESTION];
        for (int i = 0; i < QUANTITY_QUESTION; i++) {
            mResultArray[i] = "F";
        }
    }

    public void InitSelectedAnswers() {
        for (int i = 0; i < mSelectedAnswers.length; i++) {
            mSelectedAnswers[i] = "abc";
        }
    }

    public void FakeData() {
        mQuestions.add(new Question(1, "Since they are without direct supervision,field managers are expected to be able to find solutions to simple problems by ___ ", "them", "they", "themselves", "their", "themselves"));
        mQuestions.add(new Question(40, "_____until the end of last year did you need a visa to travel to Russia if you are an American Citizen", "none", "no", "not", "nor", "not"));
        mQuestions.add(new Question(41, "All vacationers are invited to take part in the water sports _____ available at the resort", "active", "activities", "actively", "activeness", "activities"));
        mQuestions.add(new Question(42, "Business leaders have strongly _____ the environmental protection law, saying it will cost corporations too much money, leading to higher prices for consumers", "criticism", "criticizing", "critical", "criticized", "criticized"));
        mQuestions.add(new Question(43, "The number of election lawbreakers has increased at an _____ rate,as illegal campaigning for the local elections intensifies", "alarm", "alarmed", "alarming", "alarmingly", "alarming"));
        mQuestions.add(new Question(44, "Despite weak forecasts, the Bradford Group reported an _____ profit growth of 2.3 billion dollars this year", "impression", "impressed", "impressively", "impressive", "impressive"));
        mQuestions.add(new Question(45, "Especially when negotiating sensitive decisions like a merger, it is important to have an acute bussiness _____", "sense", "to sense", "sensing", "sensation", "sense"));
        mQuestions.add(new Question(46, "Because the terms of the new contract were so favorable, the union members were not at all _____ about signing it", "hesitancy", "hesitant", "hesitated", "hesitation", "hesitant"));
        mQuestions.add(new Question(47, "Employees _____ in the time management seminar are learning how to utilize their time more efficiently at work", "participate", "participation", "participating", "participated", "participating"));
        mQuestions.add(new Question(48, "Choosing a carrer or major can seem _____ so we have some practical steps you can take to make a good choice", "confusing", "confusion", "confused", "confusingly", "confusing"));
    }

    //Function from AnswerQuizFragment
    @Override
    public void Send(String chosenKey, int position) {
        mSelectedAnswers[position] = chosenKey;
        if (chosenKey.equals(mQuestions.get(position).getRightAnswer())) {
            mResultArray[position] = TRUE_ANSWER;
        } else {
            mResultArray[position] = FALSE_ANSWER;
        }
    }

    //Function from AnswerQuizFragment
    @Override
    public void ClickFinish() {
        //   if (checkNotNull(mResultArray)) {
        //reset lai mang result
        mFlag = 2;
        QuizFragment frag = new QuizFragment_().builder().mQuestions(mQuestions).mFlag(mFlag).mSelectedAnswers(mSelectedAnswers).build();
        mFm.beginTransaction().replace(R.id.Container, frag).commit();
        ResultDialogFragment frag2 = new ResultDialogFragment_().builder().mResults(mResultArray).build();
        frag2.show(getSupportFragmentManager(), "dialog");
        InitResultArrays();
//        } else {
//            Toast.makeText(getBaseContext(), NOTIFY_NULL, Toast.LENGTH_LONG).show();
//        }
    }

    public boolean checkNotNull(String[] arr) {
        for (int i = 0; i < QUANTITY_QUESTION; i++) {
            if (mResultArray[i] == null) {
                return false;
            }
        }
        return true;
    }

    //Function from QuizFragment
    @Override
    public void SendFromQuizFrag() {
        mFlag = 2;
        QuizFragment frag = new QuizFragment_().builder().mQuestions(mQuestions).mFlag(mFlag).mSelectedAnswers(mSelectedAnswers).build();
        mFm.beginTransaction().replace(R.id.Container, frag).commit();
        ResultDialogFragment frag2 = new ResultDialogFragment_().builder().mResults(mResultArray).build();
        frag2.show(getSupportFragmentManager(), "dialog");
        mResultArray = new String[QUANTITY_QUESTION];
    }

    //Data from ResultFragment
    @Override
    public void sendShareData() {
        //open facebook to share
        initShareIntent("facebook");
    }

    //open facebook to share
    private void initShareIntent(String _text) {
        File filePath = getFileStreamPath("test.jpg");
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, _text);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(String.valueOf(filePath))));  //optional//use this when you want to send an image
        shareIntent.setType("image/jpeg");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "send"));
    }
}
