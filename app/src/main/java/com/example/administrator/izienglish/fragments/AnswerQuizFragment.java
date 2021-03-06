package com.example.administrator.izienglish.fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.activities.MainActivity;
import com.example.administrator.izienglish.model.Question;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_answer_quiz)
public class AnswerQuizFragment extends Fragment {
    @ViewById(R.id.radGroup)
    RadioGroup mRadGroup;
    @ViewById(R.id.radA)
    RadioButton mRadA;
    @ViewById(R.id.radB)
    RadioButton mRadB;
    @ViewById(R.id.radC)
    RadioButton mRadC;
    @ViewById(R.id.radD)
    RadioButton mRadD;
    @ViewById(R.id.tvQuestion)
    TextView mTvQuestion;
    @ViewById(R.id.btnFinish)
    Button mBtnFinish;
    @FragmentArg
    Question mQuestion;
    @FragmentArg
    int mPosition;
    @FragmentArg
    int mFlag;
    @FragmentArg
    String mSelectedAnswer;
    @ViewById(R.id.fragment_answer_quiz)
    RelativeLayout mView;
    private Typeface mCustomFont;
    private RadioButton mRadAnswer;
    private SendData mCallback;

    //test sendtoFragment
    SendToFragment mOnCallbackDataListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void Init() {
        if (mPosition < MainActivity.QUANTITY_QUESTION - 1) {
            mBtnFinish.setVisibility(View.INVISIBLE);
        } else {
            mBtnFinish.setVisibility(View.VISIBLE);
        }
        if (mFlag == 1) {
            ResetColor();
            SettingData();
        } else if (mFlag == 2) {
            SettingData();
            HighLight();
        }
        mRadGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                i = mRadGroup.getCheckedRadioButtonId();
                mRadAnswer = (RadioButton) mView.findViewById(i);
                mCallback.Send(mRadAnswer.getText().toString(), mPosition);
                if (mOnCallbackDataListener != null) {
                    mOnCallbackDataListener.Pass(mPosition);
                }
            }
        });
    }

    public void SettingData() {
        mCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "roboto_bold.ttf");
        mTvQuestion.setText(mQuestion.getQuestion().toString());
        mRadA.setText(mQuestion.getAnswer1().toString());
        mRadB.setText(mQuestion.getAnswer2().toString());
        mRadC.setText(mQuestion.getAnswer3().toString());
        mRadD.setText(mQuestion.getAnswer4().toString());
        mTvQuestion.setTypeface(mCustomFont);
        mRadA.setTypeface(mCustomFont);
        mRadB.setTypeface(mCustomFont);
        mRadC.setTypeface(mCustomFont);
        mRadD.setTypeface(mCustomFont);
    }

    public void HighLight() {
        if (mRadA.getText().toString().equals(mQuestion.getRightAnswer().toString())) {
            mRadA.setTextColor(getActivity().getResources().getColor(R.color.colorRightAnswer));
        } else if (mRadB.getText().toString().equals(mQuestion.getRightAnswer().toString())) {
            mRadB.setTextColor(getActivity().getResources().getColor(R.color.colorRightAnswer));
        } else if (mRadC.getText().toString().equals(mQuestion.getRightAnswer().toString())) {
            mRadC.setTextColor(getActivity().getResources().getColor(R.color.colorRightAnswer));
        } else if (mRadD.getText().toString().equals(mQuestion.getRightAnswer().toString())) {
            mRadD.setTextColor(getActivity().getResources().getColor(R.color.colorRightAnswer));
        }
        MarkSelectedAnswer();
    }

    public void ResetColor() {
        mRadA.setTextColor(getActivity().getResources().getColor(R.color.frag_answer_textColor));
        mRadB.setTextColor(getActivity().getResources().getColor(R.color.frag_answer_textColor));
        mRadC.setTextColor(getActivity().getResources().getColor(R.color.frag_answer_textColor));
        mRadD.setTextColor(getActivity().getResources().getColor(R.color.frag_answer_textColor));
    }

    public void MarkSelectedAnswer() {
        if (mRadA.getText().toString().equals(mSelectedAnswer)) {
            mRadA.setChecked(true);
        } else if (mRadB.getText().toString().equals(mSelectedAnswer)) {
            mRadB.setChecked(true);
        } else if (mRadC.getText().toString().equals(mSelectedAnswer)) {
            mRadC.setChecked(true);
        } else if (mRadD.getText().toString().equals(mSelectedAnswer)) {
            mRadD.setChecked(true);
        }
    }

    @Click(R.id.btnFinish)
    void ActionFinish() {
        mCallback.ClickFinish();
    }

    public interface SendData {
        public void Send(String chosenKey, int position);
        public void ClickFinish();
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

    //gui truc tiep qua fragment
    /**
     * this function work to change color when we click radioButton in a question
     */
    public interface SendToFragment {
        public void Pass(int position);
    }

    public void setOnCallbackDataListener(SendToFragment onCallbackDataListener) {
        mOnCallbackDataListener = onCallbackDataListener;
    }

}
