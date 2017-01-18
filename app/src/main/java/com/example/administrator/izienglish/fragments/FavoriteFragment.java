package com.example.administrator.izienglish.fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.SqlHelper;
import com.example.administrator.izienglish.adapters.FavoriteVerbAdapter;
import com.example.administrator.izienglish.model.Verbs;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EFragment
public class FavoriteFragment extends Fragment implements DefiniteVerbFragment.OnCallbackDataListener{
    @FragmentArg
    ArrayList<Verbs> mVerbs = new ArrayList<Verbs>();
    private ArrayList<Verbs> mFavoriteVerbs = new ArrayList<Verbs>();
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private FavoriteVerbAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Typeface mCustomFont;
    @ViewById(R.id.tvVerb1)
    TextView tvVerb1;
    @ViewById(R.id.tvVerb2)
    TextView tvVerb2;
    @ViewById(R.id.tvVerb3)
    TextView tvVerb3;
    private SqlHelper mDb;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDb = new SqlHelper(getActivity().getBaseContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @AfterViews
    public void Init() {
        mCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "gel_pen_heavy.ttf");
        getFontForTv();
        getFavoriteVerb();
        mAdapter = new FavoriteVerbAdapter(mFavoriteVerbs, mCustomFont);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Verbs verb = mFavoriteVerbs.get(position);
                DefiniteVerbFragment frag = new DefiniteVerbFragment_().builder().mVerb(verb).mPosition(position).build();
                frag.setOnCallbackDataListener(FavoriteFragment.this);
                frag.show(getFragmentManager(), "DialogFragment");
            }
        }));
    }

    public void getFavoriteVerb(){
        for(int i=0; i < mVerbs.size();i++){
            Verbs verb = mVerbs.get(i);
            if(verb.getFavorite()==1){
                mFavoriteVerbs.add(verb);
            }
        }
    }

    public void getFontForTv() {
        tvVerb1.setTypeface(mCustomFont);
        tvVerb2.setTypeface(mCustomFont);
        tvVerb3.setTypeface(mCustomFont);
    }

    //Get data From DefiniteVerbFragment
    @Override
    public void updateRecycler(Verbs verb) {
        Log.i("fds","blablablabla");
        mDb.update(verb.getV1(),verb.getFavorite());
        for(int i=0;i<mFavoriteVerbs.size();i++){
            if(mFavoriteVerbs.get(i).getV1().equals(verb.getV1())){
                mFavoriteVerbs.set(i, verb);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final FavoriteFragment.ClickListener clickListener) {
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
