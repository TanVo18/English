package com.example.administrator.izienglish.fragments;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.SqlHelper;
import com.example.administrator.izienglish.adapters.IrreVerbAdapter;
import com.example.administrator.izienglish.model.Verbs;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EFragment(R.layout.fragment_verb)
public class VerbFragment extends Fragment implements DefiniteVerbFragment.OnCallbackDataListener {
    @FragmentArg
    ArrayList<Verbs> mVerbs = new ArrayList<Verbs>();
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private IrreVerbAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Typeface mCustomFont;
    @ViewById(R.id.edSearch)
    EditText mEdSearch;
    @ViewById(R.id.tvVerb1)
    TextView tvVerb1;
    @ViewById(R.id.tvVerb2)
    TextView tvVerb2;
    @ViewById(R.id.tvVerb3)
    TextView tvVerb3;
    private SqlHelper mDb;
    private List<Verbs> mTempVerbs;
    private List<String> mListImg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDb = new SqlHelper(getActivity().getBaseContext());
    }

    @AfterViews
    public void Init() {
        //tao mang chua ten file image trong asset
        try {
            mListImg = getImage(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "gel_pen_heavy.ttf");
        getFontForTv();
        mTempVerbs = mVerbs;
        Log.i("======examp", mVerbs.get(2).getExample());
        mAdapter = new IrreVerbAdapter(mVerbs, mCustomFont);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                /* dung 1 mang mTempVerbs de nhan biet khi nao thi search khi nao thi khong search
                *  Tranh truong hop sau khi filter search, click vao item thi van hien thi noi dung cua item cu~
                *  phai phan ra 2 mang
                * */
                Verbs verb;
                if (mTempVerbs.size() < mVerbs.size()) {
                    verb = mTempVerbs.get(position);
                } else {
                    verb = mVerbs.get(position);
                }
                DefiniteVerbFragment frag = new DefiniteVerbFragment_().builder().mVerb(verb).mPosition(position).mNameOfImage(mListImg.get(position)).build();
                frag.setOnCallbackDataListener(VerbFragment.this);
                frag.show(getFragmentManager(), "DialogFragment");
            }
        }));
        //search
        addTextListener();
    }

    public void getFontForTv() {
        tvVerb1.setTypeface(mCustomFont);
        tvVerb2.setTypeface(mCustomFont);
        tvVerb3.setTypeface(mCustomFont);
    }

    //Get data From DefiniteVerbFragment
    @Override
    public void updateRecycler(Verbs verb) {
        mDb.update(verb.getV1(), verb.getFavorite());
        for (int i = 0; i < mVerbs.size(); i++) {
            if (mVerbs.get(i).getV1().equals(verb.getV1())) {
                mVerbs.set(i, verb);
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

    //Search in recyclerView
    public void addTextListener() {
        mEdSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                query = query.toString().toLowerCase();
                mTempVerbs = new ArrayList<>();
                for (int i = 0; i < mVerbs.size(); i++) {

                    final String text = mVerbs.get(i).getV1();
                    final String text2 = mVerbs.get(i).getV2();
                    final String text3 = mVerbs.get(i).getV3();
                    if (text.contains(query) || text2.contains(query) || text3.contains(query)) {
                        mTempVerbs.add(mVerbs.get(i));
                    }
                }
                mAdapter = new IrreVerbAdapter(mTempVerbs, mCustomFont);
                mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    //tao mang chua ten cac phan tu trong folder asset/picture
    private List<String> getImage(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        //picture la thu muc trong asset
        String[] files = assetManager.list("picture");
        List<String> it = Arrays.asList(files);
        return it;
    }
}
