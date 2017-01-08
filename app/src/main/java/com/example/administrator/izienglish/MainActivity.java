package com.example.administrator.izienglish;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.example.administrator.izienglish.Fragment.GrammarFragment;
import com.example.administrator.izienglish.Fragment.GrammarFragment_;
import com.example.administrator.izienglish.Fragment.ListQuestionFragment;
import com.example.administrator.izienglish.Fragment.ListQuestionFragment_;
import com.example.administrator.izienglish.Fragment.VerbFragment;
import com.example.administrator.izienglish.Fragment.VerbFragment_;
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
public class MainActivity extends AppCompatActivity {
    public static final String ROOT_CHILD = "Question";
    public static final String UNDER_CHILD = "Part1";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_BUNDLE = "bundle";
    private Firebase mRoot;
    private List<Question> mQuestions = new ArrayList<Question>();
    private List<String> mTitles = new ArrayList<String>();

    private PagerSlidingTabStrip mTabs;
    private ViewPager mPager;
    private HomePagerAdapter mAdapter;
    private FragmentManager mFm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @AfterViews
    public void Init() {
        Firebase.setAndroidContext(this);
        mRoot = new Firebase("https://test-firebase-c80fc.firebaseio.com/");
        InitTitle();
        getFirebaseData();

        mPager = (ViewPager) findViewById(pager);
        mAdapter = new HomePagerAdapter(getSupportFragmentManager(),getBaseContext());
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
                switch (position){
                    case 0:
                        GrammarFragment frag = new GrammarFragment_().builder().build();
                        mFm.beginTransaction().replace(R.id.Container,frag).commit();
                        break;
                    case 1:
                        VerbFragment frag1 = new VerbFragment_().builder().build();
                        mFm.beginTransaction().replace(R.id.Container,frag1).commit();
                        break;
                    case 2:
                        ListQuestionFragment frag3 = new ListQuestionFragment_().builder().build();
                        mFm.beginTransaction().replace(R.id.Container,frag3).commit();
                        break;
                    case 3:
                        GrammarFragment frag4 = new GrammarFragment_().builder().build();
                        mFm.beginTransaction().replace(R.id.Container,frag4).commit();
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



}
