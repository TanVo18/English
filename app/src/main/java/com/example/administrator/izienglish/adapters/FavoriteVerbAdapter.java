package com.example.administrator.izienglish.adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.model.Verbs;

import java.util.List;

/**
 * Created by Administrator on 15/1/2017.
 */

public class FavoriteVerbAdapter extends RecyclerView.Adapter<FavoriteVerbAdapter.ViewHolder> {
    private List<Verbs> mVerbs;
    private Typeface mCustomFont;

    public FavoriteVerbAdapter(List<Verbs> questions, Typeface font) {
        this.mVerbs = questions;
        this.mCustomFont = font;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.custom_favorite_item, parent, false);
        return new FavoriteVerbAdapter.ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Verbs verb = mVerbs.get(position);
        holder.mTvVerb1.setText(verb.getV1().toUpperCase());
        holder.mTvVerb2.setText(verb.getV2().toUpperCase());
        holder.mTvVerb3.setText(verb.getV3().toUpperCase());
        holder.mTvVerb1.setTypeface(mCustomFont);
        holder.mTvVerb2.setTypeface(mCustomFont);
        holder.mTvVerb3.setTypeface(mCustomFont);
        if (verb.getFavorite() == 1) {
            holder.mRelativeLayout.setBackgroundResource(R.color.ItemVerbBlueColor);
        } else if (verb.getFavorite() == 2) {
            holder.mRelativeLayout.setBackgroundResource(R.color.ItemVerbYellowColor);
        } else if (verb.getFavorite() == 3) {
            holder.mRelativeLayout.setBackgroundResource(R.color.ItemVerbGreenColor);
        } else if (verb.getFavorite() == 4) {
            holder.mRelativeLayout.setBackgroundResource(R.color.ItemVerbRedColor);
        } else {
            holder.mRelativeLayout.setBackgroundResource(R.color.ItemVerbWhiteColor);
        }
    }

    @Override
    public int getItemCount() {
        if (mVerbs == null) {
            return 0;
        } else {
            return mVerbs.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvVerb1;
        private TextView mTvVerb2;
        private TextView mTvVerb3;
        private RelativeLayout mRelativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvVerb1 = (TextView) itemView.findViewById(R.id.tvVerb1);
            mTvVerb2 = (TextView) itemView.findViewById(R.id.tvVerb2);
            mTvVerb3 = (TextView) itemView.findViewById(R.id.tvVerb3);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }

    }
}
