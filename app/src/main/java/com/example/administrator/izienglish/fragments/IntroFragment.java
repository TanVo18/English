package com.example.administrator.izienglish.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.example.administrator.izienglish.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import pl.droidsonroids.gif.GifTextView;

@EFragment(R.layout.fragment_intro)
public class IntroFragment extends Fragment {
    @ViewById(R.id.imgView)
    ImageView mImgView;
    @FragmentArg
    int mImg;
    @FragmentArg
    int mImgWord;
    @ViewById(R.id.gifTv)
    GifTextView mGifTv;

    public IntroFragment() {
        // Required empty public constructor
    }

    public static IntroFragment newInstance(String param1, String param2) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @AfterViews
    void Init() {
        mImgView.setBackgroundResource(mImg);
        mGifTv.setBackgroundResource(mImgWord);
    }
}