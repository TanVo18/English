package com.example.administrator.izienglish.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.example.administrator.izienglish.Model.Question;
import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.adapter.QuizPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EFragment
public class QuizFragment extends Fragment {
    @FragmentArg
    ArrayList<Question> mQuestions = new ArrayList<Question>();
    @ViewById(R.id.tabs)
    PagerSlidingTabStrip mTabs;
    @ViewById(R.id.pager)
    ViewPager mPager;
    private QuizPagerAdapter mAdapter;
    private String[] mQuizQuantities;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @AfterViews
    public void Init() {
        mQuizQuantities = getActivity().getResources().getStringArray(R.array.array_quiz_quantities);
        mAdapter = new QuizPagerAdapter(getChildFragmentManager(), mQuestions, mQuizQuantities);
        Log.i("mQuestion", mQuestions.size() + "");
        mPager.setAdapter(mAdapter);
        mTabs.setViewPager(mPager);
        mTabs.setIndicatorColor(getResources().getColor(R.color.Main_TabStrip_Color));
    }

}
