package com.example.administrator.izienglish.fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.adapters.QuestionRecyclerAdapter;
import com.example.administrator.izienglish.model.Question;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EFragment
public class ListQuestionFragment extends Fragment {
    @FragmentArg
    ArrayList<Question> mQuestions = new ArrayList<Question>();
    @FragmentArg
    int mFlag;
    @FragmentArg
    String[] mSelectedAnswers;
    private String mInitArray[];
    private String[] mTitles;
    private QuestionRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentManager mFragManager;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private Typeface mCustomFont;
    private ArrayList<Question> mSmallQuestion;
    public ListQuestionFragment() {
        // Required empty public constructor
    }

    @AfterViews
    public void Init() {

        mTitles = getResources().getStringArray(R.array.array_question_titles);
        mCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "balsam_digit_regular.ttf");
        mAdapter = new QuestionRecyclerAdapter(mTitles, mCustomFont);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch (position){
                    case 0:
                        mSmallQuestion = new ArrayList<Question>();
                        for(int i=0;i<10;i++){
                            mSmallQuestion.add(mQuestions.get(i));
                        }
                        break;
                    case 1:
                        mSmallQuestion = new ArrayList<Question>();
                        for(int i=10;i<20;i++){
                            mSmallQuestion.add(mQuestions.get(i));
                        }
                        break;
                    case 2:
                        mSmallQuestion = new ArrayList<Question>();
                        for(int i=20;i<30;i++){
                            mSmallQuestion.add(mQuestions.get(i));
                        }
                        break;
                    case 3:
                        mSmallQuestion = new ArrayList<Question>();
                        for(int i=30;i<40;i++){
                            mSmallQuestion.add(mQuestions.get(i));
                        }
                        break;
                    case 4:
                        mSmallQuestion = new ArrayList<Question>();
                        for(int i=40;i<50;i++){
                            mSmallQuestion.add(mQuestions.get(i));
                        }
                        break;
                }
                QuizFragment frag = new QuizFragment_().builder().mQuestions(mSmallQuestion).mFlag(mFlag).mSelectedAnswers(mSelectedAnswers).build();
                mFragManager = getActivity().getSupportFragmentManager();
                mFragManager.beginTransaction().replace(R.id.Container, frag).commit();
            }
        }));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_question, container, false);
    }

    public interface ClickListener {
        void onClick(View view, int position);
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

            View child = rv.findChildViewUnder(e.getX(), e.getY());
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
