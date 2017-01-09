package com.example.administrator.izienglish.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.Model.Verbs;

import java.util.List;

/**
 * Created by Administrator on 5/1/2017.
 */

public class IrreVerbAdapter extends RecyclerView.Adapter<IrreVerbAdapter.ViewHolder> {
    private List<Verbs> mVerbs;
    private Typeface mCustomFont;

    private int focusedItem = 0;

    public IrreVerbAdapter(List<Verbs> questions, Typeface font) {
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
        holder.itemView.setSelected(focusedItem == position);
    }

    @Override
    public int getItemCount() {
        return mVerbs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvVerb1;
        private TextView mTvVerb2;
        private TextView mTvVerb3;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTvVerb1 = (TextView) itemView.findViewById(R.id.tvVerb1);
            mTvVerb2 = (TextView) itemView.findViewById(R.id.tvVerb2);
            mTvVerb3 = (TextView) itemView.findViewById(R.id.tvVerb3);
        }

        @Override
        public void onClick(View view) {
            notifyItemChanged(focusedItem);
            focusedItem = getLayoutPosition();
            notifyItemChanged(focusedItem);
        }
    }

    //Highlight item Selected in recyclerView
    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        // Handle key up and key down and attempt to move selection
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

                // Return false if scrolled to the bounds and allow focus to move off the list
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        return tryMoveSelection(lm, 1);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        return tryMoveSelection(lm, -1);
                    }
                }
                return false;
            }
        });
    }

    private boolean tryMoveSelection(RecyclerView.LayoutManager lm, int direction) {
        int tryFocusItem = focusedItem + direction;

        // If still within valid bounds, move the selection, notify to redraw, and scroll
        if (tryFocusItem >= 0 && tryFocusItem < getItemCount()) {
            notifyItemChanged(focusedItem);
            focusedItem = tryFocusItem;
            notifyItemChanged(focusedItem);
            lm.scrollToPosition(focusedItem);
            return true;
        }

        return false;
    }

}
