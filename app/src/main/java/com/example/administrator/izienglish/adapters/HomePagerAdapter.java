package com.example.administrator.izienglish.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.izienglish.fragments.GrammarFragment_;

public class HomePagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"Grammar", "Irregular Verb", "Quiz", "Setting"};
    private Context mContext;

    public HomePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new GrammarFragment_();
            case 1:
                return new GrammarFragment_();
            case 2:
                return new GrammarFragment_();
            case 3:
                return new GrammarFragment_();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
