package com.example.administrator.izienglish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.adapter.IntroPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import me.relex.circleindicator.CircleIndicator;

@EActivity
public class IntroductionActivity extends AppCompatActivity {
    @ViewById(R.id.pager)
    ViewPager mViewPager;
    @ViewById(R.id.indicator)
    CircleIndicator mIndicator;
    private IntroPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
    }

    @AfterViews
    void Init() {
        mAdapter = new IntroPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager);

    }

    @Click(R.id.btnStart)
    void OpenMainActivity() {
        Intent intent = new Intent(IntroductionActivity.this, MainActivity_.class);
        startActivity(intent);
    }
}
