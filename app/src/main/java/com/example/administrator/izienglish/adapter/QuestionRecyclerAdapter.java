package com.example.administrator.izienglish.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.izienglish.QuestionActivity_;
import com.example.administrator.izienglish.R;

import java.util.List;

public class QuestionRecyclerAdapter extends RecyclerView.Adapter<QuestionRecyclerAdapter.ViewHolder> {
    private List<String> mTitles;
    public OnFragmentInteractionListener mListener;
    public QuestionRecyclerAdapter(List<String> titles) {
        this.mTitles = titles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.custom_question_item, parent, false);
        mListener = (QuestionActivity_)parent.getContext();
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTv.setText(mTitles.get(position).toString());
        holder.mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction(position);
            }
        });
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position);
    }
}
