package com.example.administrator.izienglish.Fragment;


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

import com.example.administrator.izienglish.Question;
import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.adapter.QuestionRecyclerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment
public class ListQuestionFragment extends Fragment {
    private ArrayList<Question> mQuestions = new ArrayList<Question>();
    private String mInitArray[];
    private List<String> mTitles = new ArrayList<String>();
    private QuestionRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentManager mFragManager;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private Typeface mCustomFont;
    public ListQuestionFragment() {
        // Required empty public constructor
    }

    @AfterViews
    public void Init() {
        FakeData();
        mInitArray = getResources().getStringArray(R.array.array_title);
        for(int i = 0; i< mInitArray.length; i++){
            mTitles.add(mInitArray[i]);
        }
        mCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "balsam_digit_regular.ttf");
        mAdapter = new QuestionRecyclerAdapter(mTitles,mCustomFont);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                QuizFragment frag = new QuizFragment_().builder().mQuestions(mQuestions).build();
                mFragManager = getActivity().getSupportFragmentManager();
                mFragManager.beginTransaction().replace(R.id.Container,frag).commit();
            }
        }));
    }

    public void FakeData(){
        mQuestions.add(new Question(1,"Since they are without direct supervision,field managers are expected to be able to find solutions to simple problems by ___ ","them","they","themselves","their","themselves"));
        mQuestions.add(new Question(1,"Since they are without direct supervision,field managers are expected to be able to find solutions to simple problems by ___ ","them","they","themselves","their","themselves"));
        mQuestions.add(new Question(1,"Since they are without direct supervision,field managers are expected to be able to find solutions to simple problems by ___ ","them","they","themselves","their","themselves"));
        mQuestions.add(new Question(1,"Since they are without direct supervision,field managers are expected to be able to find solutions to simple problems by ___ ","them","they","themselves","their","themselves"));
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
