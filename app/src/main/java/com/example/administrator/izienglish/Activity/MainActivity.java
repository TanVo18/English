package com.example.administrator.izienglish.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.administrator.izienglish.Fragment.AnswerQuizFragment;
import com.example.administrator.izienglish.Fragment.GrammarFragment;
import com.example.administrator.izienglish.Fragment.GrammarFragment_;
import com.example.administrator.izienglish.Fragment.ListQuestionFragment;
import com.example.administrator.izienglish.Fragment.ListQuestionFragment_;
import com.example.administrator.izienglish.Fragment.QuizFragment;
import com.example.administrator.izienglish.Fragment.QuizFragment_;
import com.example.administrator.izienglish.Fragment.ResultDialogFragment;
import com.example.administrator.izienglish.Fragment.ResultDialogFragment_;
import com.example.administrator.izienglish.Fragment.VerbFragment;
import com.example.administrator.izienglish.Fragment.VerbFragment_;
import com.example.administrator.izienglish.Model.Question;
import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.adapter.HomePagerAdapter;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.izienglish.R.id.pager;

@EActivity
public class MainActivity extends AppCompatActivity implements AnswerQuizFragment.SendData {
    public static final String ROOT_CHILD = "Question";
    public static final String UNDER_CHILD = "Part1";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_BUNDLE = "bundle";
    private Firebase mRoot;
    private ArrayList<Question> mQuestions = new ArrayList<Question>();
    private List<String> mTitles = new ArrayList<String>();
    private PagerSlidingTabStrip mTabs;
    private ViewPager mPager;
    private HomePagerAdapter mAdapter;
    private FragmentManager mFm;
    public static final String TRUE_ANSWER = "T";
    public static final String FALSE_ANSWER = "F";
    private String[] mResultArray;
    private String[] mSelectedAnswers;
    public static final int QUANTITY_QUESTION = 10;
    public static final String NOTIFY_NULL = "You must answer all questions";
    private int mFlag = 1;

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
        InitTitle();
        FakeData();
        // khoi tao mang chua cau dung
        mResultArray = new String[QUANTITY_QUESTION];
        // khoi tao mang save answer
        mSelectedAnswers = new String[QUANTITY_QUESTION];
        InitSelectedAnswers();
        getFirebaseData();
        mPager = (ViewPager) findViewById(pager);
        mAdapter = new HomePagerAdapter(getSupportFragmentManager(), getBaseContext());
        mPager.setAdapter(mAdapter);
        mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPager.onTouchEvent(event);
                return false;
            }
        });
        // Bind the tabs to the ViewPager
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);
        mTabs.setIndicatorColor(getResources().getColor(R.color.Main_TabStrip_Color));
        mFm = getSupportFragmentManager();
        mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        GrammarFragment frag = new GrammarFragment_().builder().build();
                        mFm.beginTransaction().replace(R.id.Container, frag).commit();
                        break;
                    case 1:
                        VerbFragment frag1 = new VerbFragment_().builder().build();
                        mFm.beginTransaction().replace(R.id.Container, frag1).commit();
                        break;
                    case 2:
                        mFlag = 1;
                        ListQuestionFragment frag3 = new ListQuestionFragment_().builder().mQuestions(mQuestions).mFlag(mFlag).mSelectedAnswers(mSelectedAnswers).build();
                        mFm.beginTransaction().replace(R.id.Container, frag3).commit();
                        break;
                    case 3:
                        GrammarFragment frag4 = new GrammarFragment_().builder().build();
                        mFm.beginTransaction().replace(R.id.Container, frag4).commit();
                        break;
                }
                mTabs.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*Khi co them splash*/
//        Intent intent = getIntent();
//        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
//        mQuestions = bundle.getParcelableArrayList(KEY_QUESTION);
//        Log.i("MainACTIVITY",mQuestions.size()+"");

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

    public void InitTitle() {
        mTitles.add("Grammar");
        mTitles.add("Exercise");
        mTitles.add("Irregular Verb");
        mTitles.add("About App");
    }

    public void InitSelectedAnswers(){
        for(int i=0;i<mSelectedAnswers.length;i++){
            mSelectedAnswers[i]="abc";
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
        Log.i("key", chosenKey);
        Log.i("position", position + "");
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
        if (checkNotNull(mResultArray)) {
            mFlag = 2;
            QuizFragment frag = new QuizFragment_().builder().mQuestions(mQuestions).mFlag(mFlag).mSelectedAnswers(mSelectedAnswers).build();
            mFm.beginTransaction().replace(R.id.Container, frag).commit();
            ResultDialogFragment frag2 = new ResultDialogFragment_().builder().mResults(mResultArray).build();
            frag2.show(getSupportFragmentManager(),"dialog");
        } else {
            Toast.makeText(getBaseContext(), NOTIFY_NULL, Toast.LENGTH_LONG).show();
        }
    }

    public boolean checkNotNull(String[] arr) {
        for (int i = 0; i < QUANTITY_QUESTION; i++) {
            if (mResultArray[i] == null) {
                return false;
            }
        }
        return true;
    }
}
