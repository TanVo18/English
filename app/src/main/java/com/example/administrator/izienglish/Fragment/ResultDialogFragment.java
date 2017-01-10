package com.example.administrator.izienglish.Fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.administrator.izienglish.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment
public class ResultDialogFragment extends DialogFragment {
    @ViewById(R.id.tvResult)
    TextView mTv;
    private int mCount=0;
    @FragmentArg
    String[] mResults;

    public ResultDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_dialog, container, false);
    }

    @AfterViews
    void Init() {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        for(int i = 0 ; i<mResults.length;i++){
            if(mResults[i].equals("T")){
                mCount++;
            }
        }
        mTv.setText("Score: "+mCount+"/20");
    }

    @Click(R.id.btnReview)
    void ClickReview(){
        dismiss();
    }

}
