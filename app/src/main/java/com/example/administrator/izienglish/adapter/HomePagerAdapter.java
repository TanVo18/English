package com.example.administrator.izienglish.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.example.administrator.izienglish.Fragment.GrammarFragment_;
import com.example.administrator.izienglish.R;

public class HomePagerAdapter extends FragmentPagerAdapter  {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Grammar", "Irregular Verb", "Quiz","Setting" };
    private int mIcon = R.drawable.pencil;
    private Context mContext;
    public HomePagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
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

//    @Override
//    public CharSequence getPageTitle(int position) {
//        // Generate title based on item position
//        return tabTitles[position];
//    }

//    @Override
//    public int getPageIconResId(int position) {
//        return mIcon;
//    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        Drawable image = mContext.getResources().getDrawable(mIcon);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        // Replace blank spaces with image icon
        SpannableString sb = new SpannableString("   " + tabTitles[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}
