package com.example.administrator.izienglish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.adapters.IntroPagerAdapter;
import com.example.administrator.izienglish.model.Question;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

@EActivity
public class IntroductionActivity extends AppCompatActivity {
    @ViewById(R.id.pager)
    ViewPager mViewPager;
    @ViewById(R.id.indicator)
    CircleIndicator mIndicator;
    private IntroPagerAdapter mAdapter;
    private ArrayList<Question> mQuestions = new ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
    }

    @AfterViews
    void Init() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(SplashScreenActivity.KEY_BUNDLE);
        mQuestions = bundle.getParcelableArrayList(SplashScreenActivity.KEY_QUESTION);
        mAdapter = new IntroPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager);

    }

    @Click(R.id.tvSkip)
    void OpenMainActivity() {
        Intent intent = new Intent(IntroductionActivity.this, MainActivity_.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(SplashScreenActivity.KEY_QUESTION, mQuestions);
        intent.putExtra(SplashScreenActivity.KEY_BUNDLE, bundle);
        startActivity(intent);
    }
}
