package com.example.administrator.izienglish.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.izienglish.R;

import java.util.List;

/**
 * Created by Administrator on 4/1/2017.
 */

public class HomeScreenRecyclerAdapter extends RecyclerView.Adapter<HomeScreenRecyclerAdapter.ViewHolder>{
    private List<String> mTitles;
    private int[] mImages;
    public HomeScreenRecyclerAdapter(List<String> mTitles,int[] images){
        this.mTitles = mTitles;
        this.mImages = images;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.custom_homescreen_item, parent, false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTv.setText(mTitles.get(position).toString());
        holder.mImgView.setImageResource(mImages[position]);
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTv;
        private ImageView mImgView;
        private View mView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView)itemView.findViewById(R.id.tvItem);
            mImgView = (ImageView)itemView.findViewById(R.id.imgView);
            mView=itemView;
        }
    }


}
