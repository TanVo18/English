package com.example.administrator.izienglish.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.Verbs;

import java.util.List;

/**
 * Created by Administrator on 5/1/2017.
 */

public class IrreVerbAdapter extends RecyclerView.Adapter<IrreVerbAdapter.ViewHolder>{
    private List<Verbs> mVerbs;
    private Typeface mCustomFont;

    public IrreVerbAdapter(List<Verbs> questions, Typeface font){
        this.mVerbs = questions;
        this.mCustomFont = font;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.custom_verb_item, parent, false);
        return new IrreVerbAdapter.ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Verbs verb = mVerbs.get(position);
        holder.mTvVerb1.setText(verb.getV1().toString());
        holder.mTvVerb2.setText(verb.getV2().toString());
        holder.mTvVerb3.setText(verb.getV3().toString());
        holder.mTvVerb1.setTypeface(mCustomFont);
        holder.mTvVerb2.setTypeface(mCustomFont);
        holder.mTvVerb3.setTypeface(mCustomFont);
    }

    @Override
    public int getItemCount() {
        return mVerbs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTvVerb1;
        private TextView mTvVerb2;
        private TextView mTvVerb3;
        private SparseBooleanArray selectedItems = new SparseBooleanArray();

        public ViewHolder(View itemView) {
            super(itemView);
            mTvVerb1 = (TextView) itemView.findViewById(R.id.tvVerb1);
            mTvVerb2 = (TextView) itemView.findViewById(R.id.tvVerb2);
            mTvVerb3 = (TextView) itemView.findViewById(R.id.tvVerb3);

        }

        @Override
        public void onClick(View view) {
            if (selectedItems.get(getAdapterPosition(), false)) {
                selectedItems.delete(getAdapterPosition());
                view.setSelected(false);
            }
            else {
                selectedItems.put(getAdapterPosition(), true);
                view.setSelected(true);
            }
        }
    }
}
