package com.example.administrator.izienglish.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.fragments.IntroFragment;
import com.example.administrator.izienglish.fragments.IntroFragment_;

/**
 * Created by Administrator on 18/1/2017.
 */

public class IntroPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    int mImg[] = {R.drawable.book_home_50, R.drawable.note_home_50, R.drawable.desk_lamp_50};
    int mImgWord[] = {R.drawable.grammar_word, R.drawable.verb_word, R.drawable.quiz_word};

    public IntroPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        IntroFragment frag = new IntroFragment_().builder().mImg(mImg[position]).mImgWord(mImgWord[position]).build();
        return frag;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
