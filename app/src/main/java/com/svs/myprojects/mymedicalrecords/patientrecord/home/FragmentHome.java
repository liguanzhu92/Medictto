package com.svs.myprojects.mymedicalrecords.patientrecord.home;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svs.myprojects.mymedicalrecords.R;

/**
 * Created by snehalsutar on 2/18/16.
 */

public class FragmentHome extends Fragment {
    TextView mWelcomeText ;
    Context mContext;
    AnimatorSet animatorSetForRadioGroup;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_patient_record, container,false);
        mContext = getActivity();
        mWelcomeText = (TextView) rootView.findViewById(R.id.welcome_text);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_pill);
        fab.setVisibility(View.GONE);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        animatorSetForRadioGroup = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.flip_horizontal);
        animatorSetForRadioGroup.setTarget(mWelcomeText);
        animatorSetForRadioGroup.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        animatorSetForRadioGroup.end();
    }
}
