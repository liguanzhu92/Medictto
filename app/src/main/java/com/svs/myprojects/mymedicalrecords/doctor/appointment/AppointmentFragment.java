package com.svs.myprojects.mymedicalrecords.doctor.appointment;


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
public class AppointmentFragment extends Fragment {
    private View rootView;
    private RecyclerView mRecyclerView;

    public AppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_appointment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.apnt_container);
        return rootView;
    }

}
