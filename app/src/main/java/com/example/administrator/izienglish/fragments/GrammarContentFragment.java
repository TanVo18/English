package com.example.administrator.izienglish.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebView;

import com.example.administrator.izienglish.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_grammar_content)
public class GrammarContentFragment extends Fragment {
    @ViewById(R.id.webView)
    WebView mWebView;
    @FragmentArg
    String mUrl;
    public GrammarContentFragment() {
        // Required empty public constructor
    }

    public static GrammarContentFragment newInstance() {
        GrammarContentFragment fragment = new GrammarContentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void Init() {
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.loadUrl(mUrl);
    }
}
