package com.example.administrator.izienglish;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.izienglish.adapter.QuestionRecyclerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.izienglish.MainActivity.KEY_BUNDLE;
import static com.example.administrator.izienglish.MainActivity.KEY_QUESTION;

@EActivity
public class QuestionActivity extends AppCompatActivity {
    private List<Question> mQuestions = new ArrayList<Question>();
    private String mInitArray[];
    private List<String> mTitles = new ArrayList<String>();
    private QuestionRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    Typeface mCustomFont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
    }

    @AfterViews
    public void Init() {
        getArrayQuestion();
        mInitArray = getResources().getStringArray(R.array.array_title);
        for(int i = 0; i< mInitArray.length; i++){
            mTitles.add(mInitArray[i]);
        }
        mCustomFont = Typeface.createFromAsset(getAssets(), "balsam_digit_regular.ttf");
        mAdapter = new QuestionRecyclerAdapter(mTitles,mCustomFont);
        mLayoutManager = new LinearLayoutManager(getBaseContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void getArrayQuestion(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_BUNDLE);
        mQuestions = bundle.getParcelableArrayList(KEY_QUESTION);
        Log.i("QuesActi",mQuestions.size()+"");
    }

}
