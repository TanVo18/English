package com.example.administrator.izienglish.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.adapter.GrammarPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment
public class GrammarFragment extends Fragment {
    @ViewById(R.id.tabs)
    TabLayout mTabs;
    @ViewById(R.id.pager)
    ViewPager mViewPager;
    private String[] mTitles;
    private String[] mUrl;
    private GrammarPagerAdapter mAdapter;

    public GrammarFragment() {
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
        return inflater.inflate(R.layout.fragment_grammar, container, false);
    }

    @AfterViews
    void Init(){
        mTitles = getResources().getStringArray(R.array.array_grammar_titles);
        mUrl = getActivity().getResources().getStringArray(R.array.array_grammar_url);
        mAdapter = new GrammarPagerAdapter(getChildFragmentManager(),mTitles,mUrl);
        mViewPager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mViewPager);

    }

}
