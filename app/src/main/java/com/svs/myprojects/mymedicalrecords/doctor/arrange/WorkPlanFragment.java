package com.svs.myprojects.mymedicalrecords.doctor.arrange;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.svs.myprojects.mymedicalrecords.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkPlanFragment extends Fragment {
    private View rootView;


    public WorkPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_work_plan, container, false);
        return rootView;
    }

}
