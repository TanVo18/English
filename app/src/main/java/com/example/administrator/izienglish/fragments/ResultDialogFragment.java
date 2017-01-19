package com.example.administrator.izienglish.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.izienglish.R;
import com.github.lzyzsd.circleprogress.DonutProgress;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@EFragment(R.layout.fragment_result_dialog)
public class ResultDialogFragment extends DialogFragment {
    @ViewById(R.id.tvResult)
    TextView mTv;
    private int mCount=0;
    @FragmentArg
    String[] mResults;
    @ViewById(R.id.donutProgress)
    DonutProgress mProgressBar;
    @ViewById(R.id.imgViewShare)
    ImageView mImgView;
    @ViewById(R.id.fragment_result)
    RelativeLayout mContent;
    private OnHeadlineSelectedListener mCallback;
    public ResultDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void Init() {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        for(int i = 0 ; i<mResults.length;i++){
            if(mResults[i].equals("T")){
                mCount++;
            }
        }
        InitProgress();
        mTv.setText("Score: "+mCount+"/10");
    }

    @Click(R.id.btnReview)
    void ClickReview(){
        dismiss();
    }

    public void InitProgress(){
        float percent = (100*mCount)/mResults.length;
        mProgressBar.setFinishedStrokeWidth(70);
        mProgressBar.setUnfinishedStrokeWidth(70);
        mProgressBar.setProgress((int) percent);
    }

    @Click(R.id.imgViewShare)
    void ActionShare(){
        getScreen();
        mCallback.sendShareData();
    }

    public interface OnHeadlineSelectedListener {
        public void sendShareData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    //Function take a screenshot and save into internal storage
    private void getScreen(){
        mContent.setDrawingCacheEnabled(true);
        Bitmap b = mContent.getDrawingCache();
        String extr = Environment.getExternalStorageDirectory().toString();
        File myPath = new File(extr, "test.jpg");
        FileOutputStream fos = null;
        try {
            Log.d("====","onSave");
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), b, "Screen", "screen");
            fos.flush();
            fos.close();
            Log.d("====","onClose");
        }catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i("TAG1","do this function");
        Toast.makeText(getContext(),"Successful",Toast.LENGTH_LONG).show();
    }
}
