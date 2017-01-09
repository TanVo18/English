package com.example.administrator.izienglish.Fragment;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.izienglish.Model.Question;
import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.adapter.QuestionRecyclerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment
public class ListQuestionFragment extends Fragment {
    private ArrayList<Question> mQuestions = new ArrayList<Question>();
    private String mInitArray[];
    private List<String> mTitles = new ArrayList<String>();
    private QuestionRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentManager mFragManager;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private Typeface mCustomFont;

    public ListQuestionFragment() {
        // Required empty public constructor
    }

    @AfterViews
    public void Init() {
        FakeData();
        mInitArray = getResources().getStringArray(R.array.array_title);
        for (int i = 0; i < mInitArray.length; i++) {
            mTitles.add(mInitArray[i]);
        }
        mCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "balsam_digit_regular.ttf");
        mAdapter = new QuestionRecyclerAdapter(mTitles, mCustomFont);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                QuizFragment frag = new QuizFragment_().builder().mQuestions(mQuestions).build();
                mFragManager = getActivity().getSupportFragmentManager();
                mFragManager.beginTransaction().replace(R.id.Container, frag).commit();
            }
        }));
    }

    public void FakeData() {
        mQuestions.add(new Question(1, "Since they are without direct supervision,field managers are expected to be able to find solutions to simple problems by ___ ", "them", "they", "themselves", "their", "themselves"));
        mQuestions.add(new Question(40, "_____until the end of last year did you need a visa to travel to Russia if you are an American Citizen", "none", "no", "not", "nor", "not"));
        mQuestions.add(new Question(41, "All vacationers are invited to take part in the water sports _____ available at the resort", "active", "activities", "actively", "activeness", "activities"));
        mQuestions.add(new Question(42, "Business leaders have strongly _____ the environmental protection law, saying it will cost corporations too much money, leading to higher prices for consumers", "criticism", "criticizing", "critical", "criticized", "criticized"));
        mQuestions.add(new Question(43, "The number of election lawbreakers has increased at an _____ rate,as illegal campaigning for the local elections intensifies", "alarm", "alarmed", "alarming", "alarmingly", "alarming"));
        mQuestions.add(new Question(44, "Despite weak forecasts, the Bradford Group reported an _____ profit growth of 2.3 billion dollars this year", "impression", "impressed", "impressively", "impressive", "impressive"));
        mQuestions.add(new Question(45, "Especially when negotiating sensitive decisions like a merger, it is important to have an acute bussiness _____", "sense", "to sense", "sensing", "sensation", "sense"));
        mQuestions.add(new Question(46, "Because the terms of the new contract were so favorable, the union members were not at all _____ about signing it", "hesitancy", "hesitant", "hesitated", "hesitation", "hesitant"));
        mQuestions.add(new Question(47, "Employees _____ in the time management seminar are learning how to utilize their time more efficiently at work", "participate", "participation", "participating", "participated", "participating"));
        mQuestions.add(new Question(48, "Choosing a carrer or major can seem _____ so we have some practical steps you can take to make a good choice", "confusing", "confusion", "confused", "confusingly", "confusing"));
        mQuestions.add(new Question(49, "We need to plan more _____ promotional events to draw customers to our stores", "excited", "exciting", "excitable", "excite", "exciting"));
        mQuestions.add(new Question(50, "According to the Korea Automobile Dítributors asociation (KADA), Korea's alltime _____ foreign auto brand, BWM,fell to third place in the imported car sales derby in June", "favorable", "favor", "favorably", "favorite", "favorite"));
        mQuestions.add(new Question(51, "We can suggest several different options that will reduce the _____ of water infiltration in your basement", "likeable", "liken", "likeness", "likelihood", "likelihood"));
        mQuestions.add(new Question(52, "_____ of the construction project is scheduled for the end of September, barring any unforeseeable problems", "complete", "completed", "completely", "completion", "completion"));
        mQuestions.add(new Question(53, "Only _____ geared employees are allowed on the factory floor near dangerous machinery", "suitable", "suitably", "suit", "suitability", "suitably"));
        mQuestions.add(new Question(54, "Rather than going through expensive trial procedures, many companied choose to use an _____ system to solve legal conflicts", "arbitrating", "arbitrator", "arbitrate", "arbitration", "arbitration"));
        mQuestions.add(new Question(55, "I will have to put up my house as _____ for the loan and agree to give it to the bank if I fail to pay the money back", "collection", "collateral", "collector", "collect", "collateral"));
        mQuestions.add(new Question(56, "The fashionable department store caters to only the most _____ shoppers", "discriminate", "discriminated", "discriminating", "discriminates", "discriminating"));
        mQuestions.add(new Question(57, "Many sellers are opposed to _____ creadit cards because of the fees that credit card companies charge", "accept", "acceptance", "accepting", "accepted", "accepting"));
        mQuestions.add(new Question(58, "Sunrise Technology plans to _____ several of its money-losing subsidiaries in the European region", "liquid", "liquidate", "liquidation", "liquor", "liquidate"));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_question, container, false);
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
