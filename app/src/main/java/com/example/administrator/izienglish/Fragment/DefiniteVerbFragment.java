package com.example.administrator.izienglish.Fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.Verbs;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment
public class DefiniteVerbFragment extends DialogFragment {
    @ViewById(R.id.imgViewShare)
    ImageView mImgViewShare;
    @ViewById(R.id.imgViewClose)
    ImageView mImgViewClose;
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
    private Typeface mCustomFont;
    public DefiniteVerbFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @AfterViews
    public void Init() {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        int width = getResources().getDimensionPixelSize(R.dimen.definiteVerb_frag_width);
        int height = getResources().getDimensionPixelSize(R.dimen.definiteVerb_frag_height);
        getDialog().getWindow().setLayout(width, height);
        mImgViewShare.setImageResource(R.drawable.ic_share_black_24dp);
        mImgViewClose.setImageResource(R.drawable.ic_close_black_24dp);
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
        mTvExampleContent.setText(getResources().getString(R.string.example_for_verb1) +"\n\n"+getResources().getString(R.string.example_for_verb2)+"\n\n"+getResources().getString(R.string.example_for_verb3));
    }

    @Click(R.id.imgViewClose)
    void ClickCloseIcon(){
        dismiss();
    }

    @Click(R.id.imgViewShare)
    void ClickShareIcon(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_definite_verb, container, false);
    }

}
