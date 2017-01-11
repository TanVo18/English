package com.example.administrator.izienglish.Fragment;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.izienglish.Model.Question;
import com.example.administrator.izienglish.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment
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
    @FragmentArg
    Question mQuestion;
    @FragmentArg
    int mPosition;
    @FragmentArg
    int mFlag;
    @FragmentArg
    String mSelectedAnswer;
    private Typeface mCustomFont;
    private RadioButton mRadAnswer;
    private View mView;
    private SendData mCallback;

    //test sendtoFragment
    SendToFragment mOnCallbackDataListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void Init() {
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
                mOnCallbackDataListener.Pass();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_answer_quiz, container, false);
        return mView;
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

    //test gui truc tiep qua fragment
    public interface SendToFragment {
        public void Pass();
    }

    public void setOnCallbackDataListener(SendToFragment onCallbackDataListener) {
        mOnCallbackDataListener = onCallbackDataListener;
    }

}
