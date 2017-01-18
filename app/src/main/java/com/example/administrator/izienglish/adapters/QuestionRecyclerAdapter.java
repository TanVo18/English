package com.example.administrator.izienglish.adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.izienglish.R;

import java.util.List;

public class QuestionRecyclerAdapter extends RecyclerView.Adapter<QuestionRecyclerAdapter.ViewHolder> {
    private List<String> mTitles;
    private Typeface mCustomFont;

    public QuestionRecyclerAdapter(List<String> titles, Typeface customFont) {
        this.mTitles = titles;
        this.mCustomFont = customFont;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.custom_question_item, parent, false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTv.setText(mTitles.get(position).toString());
        holder.mTv.setTypeface(mCustomFont);
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTv;
        private ImageView mImgView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.tv);
            mImgView = (ImageView) itemView.findViewById(R.id.imgView);
        }
    }


}
