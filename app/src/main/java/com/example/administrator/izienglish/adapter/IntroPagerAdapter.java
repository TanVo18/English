package com.example.administrator.izienglish.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.izienglish.Fragment.IntroFragment;
import com.example.administrator.izienglish.Fragment.IntroFragment_;
import com.example.administrator.izienglish.R;

/**
 * Created by Administrator on 8/1/2017.
 */

public class IntroPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    int mImg[] = {R.drawable.grammar, R.drawable.quiz, R.drawable.verb};

    public IntroPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        IntroFragment frag = new IntroFragment_().builder().mImg(mImg[position]).build();
        return frag;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
