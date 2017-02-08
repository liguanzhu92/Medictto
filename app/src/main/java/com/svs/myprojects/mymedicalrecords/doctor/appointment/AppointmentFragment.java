package com.svs.myprojects.mymedicalrecords.doctor.appointment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import com.svs.myprojects.mymedicalrecords.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentFragment extends Fragment {
    private View rootView;
    private RecyclerView mRecyclerView;
    private TextView mTextYear, mTextDay;
    private ImageView mImageCalendar;
    private CalendarView mCalendarView;

    public AppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_appointment, container, false);
        initialView();
        setContent();
        mRecyclerView.setAdapter(new AppointmentAdapter(getContext()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

    private void setContent() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, MMM d, yyyy");
        String s = dateFormat.format(cal.getTime());
        mTextDay.setText(s);
        mImageCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCalendarView.getVisibility() == View.GONE) {
                    mCalendarView.setVisibility(View.VISIBLE);
                    return;
                }
                if (mCalendarView.getVisibility() == View.VISIBLE) {
                    mCalendarView.setVisibility(View.GONE);
                    return;
                }
            }
        });
    }

    private void initialView() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.apnt_container);
        mTextYear = (TextView) rootView.findViewById(R.id.apnt_year);
        mTextDay = (TextView) rootView.findViewById(R.id.apnt_day);
        mImageCalendar = (ImageView) rootView.findViewById(R.id.apnt_show);
        mCalendarView = (CalendarView) rootView.findViewById(R.id.apnt_calendar);
    }

}
