package com.example.administrator.izienglish.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.services.AlarmService;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment
public class SettingFragment extends Fragment {
    @ViewById(R.id.noti_switch)
    Switch mSwitch;
    @ViewById(R.id.btnOpenStore)
    Button mBtnOpen;
    private int mSwitchEnable = 0;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Click(R.id.btnOpenStore)
    void OpenPlayStore(){
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + getContext().getPackageName()));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Click(R.id.noti_switch)
    void pushSwitch(){
        mSwitchEnable++;
        if(mSwitchEnable%2!=0){
            Toast.makeText(getContext(),"Turn On Remind Word",Toast.LENGTH_LONG).show();
           getActivity().startService(new Intent(getContext(), AlarmService.class));
        }
        else{
            Toast.makeText(getContext(),"Turn Off Remind Word",Toast.LENGTH_LONG).show();
            getActivity().stopService(new Intent(getContext(), AlarmService.class));
        }
    }
}
