package com.example.administrator.izienglish.Fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.izienglish.Question;
import com.example.administrator.izienglish.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment
public class AnswerQuizFragment extends Fragment {
    @ViewById(R.id.radGroup)
    RadioGroup radGroup;
    @ViewById(R.id.radA)
    RadioButton radA;
    @ViewById(R.id.radB)
    RadioButton radB;
    @ViewById(R.id.radC)
    RadioButton radC;
    @ViewById(R.id.radD)
    RadioButton radD;
    @ViewById(R.id.tvQuestion)
    TextView mTvQuestion;
    @FragmentArg
    Question mQuestion;
    private Typeface mCustomFont;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void Init(){
        mCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "roboto_bold.ttf");
        mTvQuestion.setText(mQuestion.getQuestion().toString());
        radA.setText(mQuestion.getAnswer1().toString());
        radB.setText(mQuestion.getAnswer2().toString());
        radC.setText(mQuestion.getAnswer3().toString());
        radD.setText(mQuestion.getAnswer4().toString());
        mTvQuestion.setTypeface(mCustomFont);
        radA.setTypeface(mCustomFont);
        radB.setTypeface(mCustomFont);
        radC.setTypeface(mCustomFont);
        radD.setTypeface(mCustomFont);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_answer_quiz, container, false);
        return view;
    }

}
