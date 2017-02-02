package com.example.administrator.izienglish.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.izienglish.fragments.GrammarContentFragment;
import com.example.administrator.izienglish.fragments.GrammarContentFragment_;

/**
 * Created by Administrator on 15/1/2017.
 */

public class GrammarPagerAdapter extends FragmentPagerAdapter {
    private String mTabTitles[];
    private String[] mUrls;

    public GrammarPagerAdapter(FragmentManager fm, String[] titles, String[] urls) {
        super(fm);
        this.mTabTitles = titles;
        this.mUrls = urls;
    }

    @Override
    public Fragment getItem(int position) {
        GrammarContentFragment frag = new GrammarContentFragment_().builder().mUrl(mUrls[position]).build();
        return frag;
    }

    @Override
    public int getCount() {
        return mTabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return mTabTitles[position];
    }
}
