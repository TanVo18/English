package com.example.administrator.izienglish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.izienglish.adapter.QuizPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import static com.example.administrator.izienglish.MainActivity.KEY_BUNDLE;
import static com.example.administrator.izienglish.MainActivity.KEY_QUESTION;

@EActivity
public class QuizActivity extends AppCompatActivity {
    @ViewById(R.id.viewPager)
    ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private ArrayList<Question> mQuestions = new ArrayList<Question>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
    }
    @AfterViews
    public void Init(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
        mQuestions = bundle.getParcelableArrayList(KEY_QUESTION);
        Log.i("quizActi",mQuestions.size()+"");
        mPagerAdapter = new QuizPagerAdapter(getSupportFragmentManager(),mQuestions);
        mViewPager.setAdapter(mPagerAdapter);
    }
}
