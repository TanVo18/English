package com.example.administrator.izienglish.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.izienglish.model.Verbs;
import com.example.administrator.izienglish.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

@EFragment
public class DefiniteVerbFragment extends DialogFragment {
    @ViewById(R.id.imgViewClose)
    ImageView mImgViewClose;
    @ViewById(R.id.imgViewFavorite)
    ImageView mImgViewFav;
    @ViewById(R.id.tvVerb1)
    TextView mTvVerb1;
    @ViewById(R.id.tvVerb2)
    TextView mTvVerb2;
    @ViewById(R.id.tvVerb3)
    TextView mTvVerb3;
    @ViewById(R.id.imgViewSound1)
    ImageView mImgViewSound1;
    @ViewById(R.id.imgViewSound2)
    ImageView mImgViewSound2;
    @ViewById(R.id.imgViewSound3)
    ImageView mImgViewSound3;
    @ViewById(R.id.imgViewVerb)
    ImageView mImgViewVerb;
    @ViewById(R.id.tvDefinition)
    TextView mTvDefinition;
    @ViewById(R.id.tvDefineContent)
    TextView mTvDefineContent;
    @ViewById(R.id.tvExample)
    TextView mTvExample;
    @ViewById(R.id.tvExampleContent)
    TextView mTvExampleContent;
    @FragmentArg
    Verbs mVerb;
    @FragmentArg
    int mPosition;
    private Typeface mCustomFont;
    private OnCallbackDataListener mOnCallbackDataListener;
    private TextToSpeech mSpeech;
    public DefiniteVerbFragment() {
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
        return inflater.inflate(R.layout.fragment_definite_verb, container, false);
    }

    @AfterViews
    public void Init() {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mImgViewClose.setImageResource(R.drawable.close_50);
        checkFavorite();
        mTvVerb1.setText(mVerb.getV1().toString());
        mTvVerb2.setText(mVerb.getV2().toString());
        mTvVerb3.setText(mVerb.getV3().toString());
        mImgViewSound1.setImageResource(R.drawable.speaker);
        mImgViewSound2.setImageResource(R.drawable.speaker);
        mImgViewSound3.setImageResource(R.drawable.speaker);
        mImgViewVerb.setImageResource(R.drawable.blow_verb);
        mTvDefinition.setText("Definition");
        mCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "roboto_bold.ttf");
        mTvDefinition.setTypeface(mCustomFont);
        mTvDefineContent.setText(mVerb.getDefinition().toString());
        mTvExample.setText("Example");
        mTvExample.setTypeface(mCustomFont);
        mTvExampleContent.setText(getResources().getString(R.string.example_for_verb1) + "\n\n" + getResources().getString(R.string.example_for_verb2));
        // initial textToSpeech
        mSpeech=new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    mSpeech.setLanguage(Locale.UK);
                }
            }
        });
    }

    @Click(R.id.imgViewClose)
    void ClickCloseIcon() {
        if(mOnCallbackDataListener!=null){
            mOnCallbackDataListener.updateRecycler(mVerb);
        }
        dismiss();
    }

    public void checkFavorite() {
        if (mVerb.getFavorite() == 0) {
            mImgViewFav.setImageResource(R.drawable.ic_favorite_border_red_48dp);
        } else {
            mImgViewFav.setImageResource(R.drawable.ic_favorite_red_48dp);
        }
    }

    @Click(R.id.imgViewFavorite)
    void ClickFavorite() {
        if (mVerb.getFavorite() == 0) {
            mImgViewFav.setImageResource(R.drawable.ic_favorite_red_48dp);
            mVerb.setFavorite(1);
        } else {
            mImgViewFav.setImageResource(R.drawable.ic_favorite_border_red_48dp);
            mVerb.setFavorite(0);
        }
    }

    //Send to VerbFragment
    public interface OnCallbackDataListener{
        public void updateRecycler(Verbs verb);
    }

    public void setOnCallbackDataListener(OnCallbackDataListener onCallbackDataListener) {
        mOnCallbackDataListener = onCallbackDataListener;
    }

    @Click(R.id.imgViewSound1)
    void SpeakVerb1(){
        mSpeech.speak(mTvVerb1.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Click(R.id.imgViewSound2)
    void SpeakVerb2(){
        mSpeech.speak(mTvVerb2.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Click(R.id.imgViewSound3)
    void SpeakVerb3(){
        mSpeech.speak(mTvVerb3.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

}
