package com.example.administrator.izienglish.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Switch;
import android.widget.Toast;

import com.example.administrator.izienglish.R;
import com.example.administrator.izienglish.services.AlarmService;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_setting)
public class SettingFragment extends Fragment {
    @ViewById(R.id.noti_switch)
    Switch mSwitch;
    private int mSwitchEnable = 0;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Click(R.id.noti_switch)
    void pushSwitch() {
        mSwitchEnable++;
        if (mSwitchEnable % 2 != 0) {
            Toast.makeText(getContext(), "Turn On Remind Word", Toast.LENGTH_LONG).show();
            getActivity().startService(new Intent(getContext(), AlarmService.class));
        } else {
            Toast.makeText(getContext(), "Turn Off Remind Word", Toast.LENGTH_LONG).show();
            getActivity().stopService(new Intent(getContext(), AlarmService.class));
        }
    }
}
