package com.example.administrator.izienglish.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.izienglish.Model.Question;
import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.adapter.QuizPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import static com.example.administrator.izienglish.Activity.MainActivity.QUANTITY_QUESTION;

@EFragment
public class QuizFragment extends Fragment implements AnswerQuizFragment.SendToFragment {
    @FragmentArg
    ArrayList<Question> mQuestions = new ArrayList<Question>();
    @FragmentArg
    int mFlag;
    @FragmentArg
    String mSelectedAnswers[];
    @FragmentArg
    int mFlagChangeColor;
    @ViewById(R.id.tabs)
    TabLayout mTabs;
    @ViewById(R.id.pager)
    ViewPager mViewPager;
    @ViewById(R.id.tvTime)
    TextView mTvTime;
    private QuizPagerAdapter mAdapter;
    private String[] mQuizQuantities;
    private int checks[] = new int[QUANTITY_QUESTION];
    private Handler mHandler;
    private long mSecond = 0;
    private long mMinute = 1;
    private SendData mCallback;

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
        InitArray();
        mQuizQuantities = getActivity().getResources().getStringArray(R.array.array_quiz_quantities);
        mAdapter = new QuizPagerAdapter(this, getChildFragmentManager(), mQuestions, mQuizQuantities, mFlag, mSelectedAnswers);
        mViewPager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mViewPager);
        //start handler
        //countSecond();

    }

    public View setupTab(int title) {
        TextView tab = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tab.setText((title + 1) + "");
        tab.setTextColor(getActivity().getResources().getColor(R.color.colorChooseQuestion));
        return tab;
    }

    //Interface From AnswerQuizFragment
    @Override
    public void Pass(int position) {
        Log.i("Click", "bang");
        if (checkPosition(position)) {
            mTabs.getTabAt(position).setCustomView(setupTab(position));
            checks[position] = position;
        }
    }

    public void InitArray() {
        for (int i = 0; i < QUANTITY_QUESTION; i++) {
            checks[i] = QUANTITY_QUESTION;
        }
    }

    //check truong hop da doi mau` roi lai doi tiep khi quay nguoc lai cau hoi truoc
    public boolean checkPosition(int position) {
        for (int i = 0; i < QUANTITY_QUESTION; i++) {
            if (checks[i] == position) {
                return false;
            }
        }
        return true;
    }

    public void countSecond() {
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.removeCallbacksAndMessages(null);
                if (mMinute == 0 && mSecond == 0) {
                    mCallback.SendFromQuizFrag();
                    mHandler.removeCallbacksAndMessages(null);
                }
                if (mSecond == 0) {
                    mSecond = 60;
                    mMinute--;
                }
                mSecond--;
                mTvTime.setText(mMinute + " : " + mSecond);
                mHandler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    public interface SendData {
        public void SendFromQuizFrag();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {
            mCallback = (SendData) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
