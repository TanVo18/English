package com.example.administrator.izienglish.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.izienglish.Model.Verbs;
import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.SqlHelper;
import com.example.administrator.izienglish.adapter.IrreVerbAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment
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
    public static final String KEY_BUNDLE = "verb";
    private SqlHelper mDb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDb = new SqlHelper(getActivity().getBaseContext());

    }

    @AfterViews
    public void Init() {
        mCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "roboto_bold.ttf");
        getFontForTv();
        mAdapter = new IrreVerbAdapter(mVerbs, mCustomFont);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Verbs verb = mVerbs.get(position);
                DefiniteVerbFragment frag = new DefiniteVerbFragment_().builder().mVerb(verb).mPosition(position).build();
                frag.setOnCallbackDataListener(VerbFragment.this);
                frag.show(getFragmentManager(), "DialogFragment");
            }
        }));
        //search
        addTextListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verb, container, false);
    }

    public void getFontForTv() {
        tvVerb1.setTypeface(mCustomFont);
        tvVerb2.setTypeface(mCustomFont);
        tvVerb3.setTypeface(mCustomFont);
    }

    //Get data From DefiniteVerbFragment
    @Override
    public void updateRecycler(Verbs verb, int position) {
        mDb.update(verb.getV1(),verb.getFavorite());
        mVerbs.set(position,verb);
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
                final List<Verbs> filteredList = new ArrayList<>();

                for (int i = 0; i < mVerbs.size(); i++) {

                    final String text = mVerbs.get(i).getV1().toLowerCase();
                    if (text.contains(query)) {
                        filteredList.add(mVerbs.get(i));
                    }
                }
                mAdapter = new IrreVerbAdapter(filteredList, mCustomFont);
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
}
