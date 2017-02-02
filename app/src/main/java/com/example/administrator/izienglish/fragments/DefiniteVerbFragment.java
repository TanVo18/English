package com.example.administrator.izienglish.fragments;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.model.Verbs;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@EFragment(R.layout.fragment_definite_verb)
public class DefiniteVerbFragment extends DialogFragment {
    @ViewById(R.id.imgViewClose)
    ImageView mImgViewClose;
    @ViewById(R.id.tvVerb1)
    TextView mTvVerb1;
    @ViewById(R.id.tvVerb2)
    TextView mTvVerb2;
    @ViewById(R.id.tvVerb3)
    TextView mTvVerb3;
    @ViewById(R.id.tvIpa1)
    TextView mTvIpa1;
    @ViewById(R.id.tvIpa2)
    TextView mTvIpa2;
    @ViewById(R.id.tvIpa3)
    TextView mTvIpa3;
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
    @FragmentArg
    String mNameOfImage;
    private OnCallbackDataListener mOnCallbackDataListener;
    private TextToSpeech mSpeech;

    public DefiniteVerbFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void Init() {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mImgViewClose.setImageResource(R.drawable.close_50);
        mTvVerb1.setText(mVerb.getV1());
        mTvVerb2.setText(mVerb.getV2());
        mTvVerb3.setText(mVerb.getV3());
        mTvIpa1.setText(mVerb.getIpa1());
        mTvIpa2.setText(mVerb.getIpa2());
        mTvIpa3.setText(mVerb.getIpa3());
        //set sound
        mImgViewSound1.setImageResource(R.drawable.speaker);
        mImgViewSound2.setImageResource(R.drawable.speaker);
        mImgViewSound3.setImageResource(R.drawable.speaker);
        InputStream myInput = null;
        try {
            myInput = getActivity().getAssets().open("picture/" + mNameOfImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (myInput != null) {
            mImgViewVerb.setImageBitmap(BitmapFactory.decodeStream(myInput));
        }
        mTvDefinition.setText(getResources().getString(R.string.definite_frag_definition));
        Typeface CustomFont;
        CustomFont = Typeface.createFromAsset(getActivity().getAssets(), "roboto_bold.ttf");
        mTvDefinition.setTypeface(CustomFont);
        mTvDefineContent.setText(mVerb.getDefinition());
        mTvExample.setText(getResources().getString(R.string.definite_frag_example));
        mTvExample.setTypeface(CustomFont);
        mTvExampleContent.setText(mVerb.getExample());
        // initial textToSpeech
        mSpeech = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    mSpeech.setLanguage(Locale.UK);
                }
            }
        });
    }

    private List<String> getImage(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        //picture la thu muc trong asset
        String[] files = assetManager.list("picture");
        List<String> it = Arrays.asList(files);
        return it;
    }

    @Click(R.id.imgViewClose)
    void ClickCloseIcon() {
        if (mOnCallbackDataListener != null) {
            mOnCallbackDataListener.updateRecycler(mVerb);
        }
        dismiss();
    }

    //0:white 1:blue 2:yellow 3:green 4:red
    @Click(R.id.imgViewWhite)
    void ClickWhiteColor() {
        mVerb.setFavorite(0);
        Toast.makeText(getContext(), "White", Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.imgViewBlue)
    void ClickBlueColor() {
        mVerb.setFavorite(1);
        Toast.makeText(getContext(), "Blue", Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.imgViewYellow)
    void ClickYellowColor() {
        mVerb.setFavorite(2);
        Toast.makeText(getContext(), "Yellow", Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.imgViewGreen)
    void ClickGreenColor() {
        mVerb.setFavorite(3);
        Toast.makeText(getContext(), "Green", Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.imgViewRed)
    void ClickRedColor() {
        mVerb.setFavorite(4);
        Toast.makeText(getContext(), "Red", Toast.LENGTH_SHORT).show();
    }

    //Send to VerbFragment
    public interface OnCallbackDataListener {
        public void updateRecycler(Verbs verb);
    }

    public void setOnCallbackDataListener(OnCallbackDataListener onCallbackDataListener) {
        mOnCallbackDataListener = onCallbackDataListener;
    }

    @Click(R.id.imgViewSound1)
    void SpeakVerb1() {
        mSpeech.speak(mTvVerb1.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Click(R.id.imgViewSound2)
    void SpeakVerb2() {
        mSpeech.speak(mTvVerb2.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Click(R.id.imgViewSound3)
    void SpeakVerb3() {
        mSpeech.speak(mTvVerb3.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }
}
