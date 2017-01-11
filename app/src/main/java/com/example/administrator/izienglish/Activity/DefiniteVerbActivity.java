package com.example.administrator.izienglish.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.izienglish.Fragment.VerbFragment;
import com.example.administrator.izienglish.Model.Verbs;
import com.example.administrator.izienglish.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class DefiniteVerbActivity extends AppCompatActivity {
    @ViewById(R.id.imgViewShare)
    ImageView mImgViewShare;
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
    private Verbs mVerb;
    private Typeface mCustomFont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definite_verb);
    }

    @AfterViews
    public void Init() {
        getData();
        mImgViewShare.setImageResource(R.drawable.share_50);
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
        mCustomFont = Typeface.createFromAsset(getAssets(), "roboto_bold.ttf");
        mTvDefinition.setTypeface(mCustomFont);
        mTvDefineContent.setText(mVerb.getDefinition().toString());
        mTvExample.setText("Example");
        mTvExample.setTypeface(mCustomFont);
        mTvExampleContent.setText(getResources().getString(R.string.example_for_verb1) + "\n\n" + getResources().getString(R.string.example_for_verb2) + "\n\n" + getResources().getString(R.string.example_for_verb3));
    }

    public void getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(VerbFragment.KEY_BUNDLE);
        mVerb = bundle.getParcelable(VerbFragment.KEY_BUNDLE);
    }

    @Click(R.id.imgViewClose)
    void ClickCloseIcon() {
        finish();
    }

    @Click(R.id.imgViewShare)
    void ClickShareIcon() {

    }

    public void checkFavorite(){
        if(mVerb.getFavorite()==0){
            mImgViewFav.setImageResource(R.drawable.ic_favorite_border_red_48dp);
        }
        else{
            mImgViewFav.setImageResource(R.drawable.ic_favorite_red_48dp);
        }
    }
}
