package com.example.administrator.izienglish.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.izienglish.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import pl.droidsonroids.gif.GifTextView;

@EFragment
public class IntroFragment extends Fragment {
    @ViewById(R.id.imgView)
    ImageView mImgView;
    @ViewById(R.id.gifTv)
    GifTextView mGifTv;
    @FragmentArg
    int mImg;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

    @AfterViews
    void Init() {
        mGifTv.setBackgroundResource(mImg);
    }
}
