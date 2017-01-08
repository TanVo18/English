package com.example.administrator.izienglish.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.administrator.izienglish.Fragment.AnswerQuizFragment;
import com.example.administrator.izienglish.Fragment.AnswerQuizFragment_;
import com.example.administrator.izienglish.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/1/2017.
 */

public class QuizPagerAdapter extends FragmentPagerAdapter {
    private List<Question> mQuestions = new ArrayList<Question>();
    private String tabTitles[] = new String[] { "Question1"};

    public QuizPagerAdapter(FragmentManager fm, ArrayList<Question> questions) {
        super(fm);
        this.mQuestions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("position",position+"");
        Question ques = mQuestions.get(position);
        AnswerQuizFragment frag = new AnswerQuizFragment_().builder().mQuestion(ques).build();
        return frag;
    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }

        @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[0];
    }
}
