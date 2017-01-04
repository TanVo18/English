package com.example.administrator.izienglish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView mImgView;
    public static final String ROOT_CHILD = "Question";
    public static final String UNDER_CHILD = "Part1";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_BUNDLE = "bundle";
    private Firebase mRoot;
    private List<Question> mQuestions = new ArrayList<Question>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mImgView = (ImageView) findViewById(R.id.imgView);
        mImgView.setImageResource(R.drawable.splashscreen);
        //Firebase
        Firebase.setAndroidContext(this);
        mRoot = new Firebase("https://test-firebase-c80fc.firebaseio.com/");

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    getFirebaseData();
                    sleep(8000);
                    Intent intent = new Intent(getApplicationContext(), MainActivity_.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(KEY_QUESTION,(ArrayList)mQuestions);
                    intent.putExtra(KEY_BUNDLE,bundle);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

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
}
