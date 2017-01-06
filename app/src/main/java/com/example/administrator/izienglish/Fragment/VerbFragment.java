package com.example.administrator.izienglish.Fragment;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.SqlHelper;
import com.example.administrator.izienglish.Verbs;
import com.example.administrator.izienglish.adapter.IrreVerbAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment
public class VerbFragment extends Fragment {
    private List<Verbs> mVerbs = new ArrayList<Verbs>();
    private SqlHelper mDb;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private IrreVerbAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Typeface mCustomFont;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void Init() {
        mCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "roboto_bold.ttf");
        mDb = new SqlHelper(getContext());
        mVerbs = mDb.getData();
        mAdapter = new IrreVerbAdapter(mVerbs, mCustomFont);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Verbs verb = mVerbs.get(position);
                DefiniteVerbFragment frag = new DefiniteVerbFragment_().builder().mVerb(verb).build();
                frag.show(getFragmentManager(),"DialogFragment");
            }
        }));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verb, container, false);
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
