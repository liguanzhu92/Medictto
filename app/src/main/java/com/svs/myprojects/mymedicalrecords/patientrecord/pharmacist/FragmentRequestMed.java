package com.svs.myprojects.mymedicalrecords.patientrecord.pharmacist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.svs.myprojects.mymedicalrecords.R;

/**
 * Created by snehalsutar on 3/3/16.
 */
public class FragmentRequestMed extends Fragment {

    View mRootView;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mRootView = inflater.inflate(R.layout.fragment_request_med,container,false);
        return mRootView;
    }
}
