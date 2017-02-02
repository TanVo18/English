package com.example.administrator.izienglish.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.izienglish.fragments.AnswerQuizFragment;
import com.example.administrator.izienglish.fragments.AnswerQuizFragment_;
import com.example.administrator.izienglish.model.Question;

import java.util.ArrayList;
import java.util.List;



public class QuizPagerAdapter extends FragmentStatePagerAdapter {
    private List<Question> mQuestions = new ArrayList<Question>();
    private String mTabTitles[] = new String[10];
    private String mSelectedAnswers[];
    private int mFlag;
    AnswerQuizFragment.SendToFragment mQuizFragment;
    public QuizPagerAdapter(AnswerQuizFragment.SendToFragment quizFragment,FragmentManager fm, ArrayList<Question> questions, String[] tabTitle,int flag,String[] SelectedAnswers) {
        super(fm);
        this.mQuestions = questions;
        this.mFlag = flag;
        this.mTabTitles = tabTitle;
        this.mSelectedAnswers = SelectedAnswers;
        mQuizFragment=quizFragment;
    }

    @Override
    public Fragment getItem(int position) {
        Question ques = mQuestions.get(position);
        AnswerQuizFragment frag = new AnswerQuizFragment_().builder().mQuestion(ques).mPosition(position).mFlag(mFlag).mSelectedAnswer(mSelectedAnswers[position]).build();
        //test
        frag.setOnCallbackDataListener(mQuizFragment);
        return frag;
    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return mTabTitles[position];
    }
}
