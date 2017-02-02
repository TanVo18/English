package com.example.administrator.izienglish.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
//        FakeData();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(SplashScreenActivity.KEY_BUNDLE);
        mQuestions = bundle.getParcelableArrayList(SplashScreenActivity.KEY_QUESTION);
        Log.i("QUESTION",mQuestions.size()+"");
        // khoi tao mang chua cau dung
        InitResultArrays();
        // khoi tao mang save answer
        mSelectedAnswers = new String[QUANTITY_QUESTION];
        // khoi tao mang irregular verb
        getDataIrregularVerb();
        mFm = getSupportFragmentManager();
        InitSelectedAnswers();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.googlePlay:
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + getPackageName()));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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
        mFlag = 2;
        QuizFragment frag = new QuizFragment_().builder().mQuestions(mQuestions).mFlag(mFlag).mSelectedAnswers(mSelectedAnswers).build();
        mFm.beginTransaction().replace(R.id.Container, frag).commit();
        ResultDialogFragment frag2 = new ResultDialogFragment_().builder().mResults(mResultArray).build();
        frag2.show(getSupportFragmentManager(), "dialog");
        InitResultArrays();
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
