package com.example.administrator.izienglish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.izienglish.adapter.HomeScreenRecyclerAdapter;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity
public class MainActivity extends AppCompatActivity {
    public static final String ROOT_CHILD = "Question";
    public static final String UNDER_CHILD = "Part1";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_BUNDLE = "bundle";
    private Firebase mRoot;
    private List<Question> mQuestions = new ArrayList<Question>();
    private List<String> mTitles = new ArrayList<String>();
    private int[] mImages = {R.drawable.book, R.drawable.pencil, R.drawable.wordsecond, R.drawable.exclamation};
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private HomeScreenRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Intent mIntent;

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
        mAdapter = new HomeScreenRecyclerAdapter(mTitles, mImages);
        mLayoutManager = new GridLayoutManager(getBaseContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        /*Khi co them splash*/
//        Intent intent = getIntent();
//        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
//        mQuestions = bundle.getParcelableArrayList(KEY_QUESTION);
//        Log.i("MainACTIVITY",mQuestions.size()+"");

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        mIntent = new Intent(MainActivity.this, QuestionActivity_.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(KEY_QUESTION, (ArrayList) mQuestions);
                        mIntent.putExtra(KEY_BUNDLE, bundle);
                        startActivity(mIntent);
                        break;
                    case 2:
                        mIntent = new Intent(MainActivity.this, IrregularVerbActivity_.class);
                        startActivity(mIntent);
                        break;
                    case 3:
                        break;
                }
            }
        }));
    }

//    @Click(R.id.btnExercise)
//    public void ClickExercise(){
//        Intent intent = new Intent(MainActivity.this,QuestionActivity_.class);
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList(KEY_QUESTION,(ArrayList)mQuestions);
//        intent.putExtra(KEY_BUNDLE,bundle);
//        startActivity(intent);
//    }
//
//    @Click(R.id.btnVerb)
//    public void ClickIrrVerb(){
//        Intent intent = new Intent(MainActivity.this,IrregularVerbActivity_.class);
//        startActivity(intent);
//    }

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

    public interface ClickListener {
        void onClick(android.view.View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            android.view.View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
