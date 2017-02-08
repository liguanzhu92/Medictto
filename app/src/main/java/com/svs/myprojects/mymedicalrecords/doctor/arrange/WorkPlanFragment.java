package com.svs.myprojects.mymedicalrecords.doctor.arrange;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.svs.myprojects.mymedicalrecords.R;
import com.svs.myprojects.mymedicalrecords.patientrecord.utils.VolleyController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkPlanFragment extends Fragment {
    private View rootView;
    private CalendarView mCalendarView;
    private Button mButtonConfirm, mButtonCancel;
    private CheckBox mCheck1, mCheck2, mCheck3, mCheck4, mCheck5, mCheck6, mCheck7, mCheck8, mCheck9, mCheck10, mCheck11,mCheck12;
    private String[] timeSlots;
    private static final String TIME_SLOT = "http://rjtmobile.com/medictto/appointment_available.php?&";


    public WorkPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_work_plan, container, false);
        timeSlots =  getContext().getResources().getStringArray(R.array.timings_array);
        initialView();
        setListener();
        String date = "2017-02-07";
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //String date = dateFormat.format(calendar.getTime());
        getInfor(date);
        return rootView;
    }

    private void initialView() {
        mCalendarView = (CalendarView) rootView.findViewById(R.id.work_calendarView);
        mCheck1 = (CheckBox) rootView.findViewById(R.id.work_slot1);
        mCheck2 = (CheckBox) rootView.findViewById(R.id.work_slot2);
        mCheck3 = (CheckBox) rootView.findViewById(R.id.work_slot3);
        mCheck4 = (CheckBox) rootView.findViewById(R.id.work_slot4);
        mCheck5 = (CheckBox) rootView.findViewById(R.id.work_slot5);
        mCheck6 = (CheckBox) rootView.findViewById(R.id.work_slot6);
        mCheck7 = (CheckBox) rootView.findViewById(R.id.work_slot7);
        mCheck8 = (CheckBox) rootView.findViewById(R.id.work_slot8);
        mCheck9 = (CheckBox) rootView.findViewById(R.id.work_slot9);
        mCheck10 = (CheckBox) rootView.findViewById(R.id.work_slot10);
        mCheck11 = (CheckBox) rootView.findViewById(R.id.work_slot11);
        mCheck12 = (CheckBox) rootView.findViewById(R.id.work_slot12);
        mButtonConfirm = (Button) rootView.findViewById(R.id.work_confirm);
        mButtonCancel = (Button) rootView.findViewById(R.id.work_cancel);
    }

    private void setListener(){
        mCheck1.setText(timeSlots[0]);
        mCheck2.setText(timeSlots[1]);
        mCheck3.setText(timeSlots[2]);
        mCheck4.setText(timeSlots[3]);
        mCheck5.setText(timeSlots[4]);
        mCheck6.setText(timeSlots[5]);
        mCheck7.setText(timeSlots[6]);
        mCheck8.setText(timeSlots[7]);
        mCheck9.setText(timeSlots[8]);
        mCheck10.setText(timeSlots[9]);
        mCheck11.setText(timeSlots[10]);
        mCheck12.setText(timeSlots[11]);
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                i1++;
                String month;
                if (i1 < 10) {
                    month = "0" + i1;
                } else {
                    month = String.valueOf(i1);
                }
                String day;
                if (i1 < 10) {
                    day = "0" + i2;
                } else {
                    day = String.valueOf(i2);
                }
                String s = "" + i + "-" + month + "-" + day;
                getInfor(s);
            }
        });
    }

    private void getInfor(String date) {
        String doctorID = "101";
        // get slot infor
        date = "2017-02-07";
        String url = TIME_SLOT + "&doctorID=" + doctorID + "&currentdate=" + date;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray array = jsonObject.getJSONArray("appointment slot");
                    JSONObject object = array.getJSONObject(0);
                    VolleyLog.d("volley", object);
                    String s = object.getString("slot_1");
                    if (s != null) {
                        mCheck1.setChecked(true);
                    }
                    s = object.getString("slot_2");
                    if (s != null) {
                        mCheck2.setChecked(true);
                    }
                    s = object.getString("slot_3");
                    if (s != null) {
                        mCheck3.setChecked(true);
                    }
                    s = object.getString("slot_4");
                    if (s != null) {
                        mCheck4.setChecked(true);
                    }
                    s = object.getString("slot_5");
                    if (s != null) {
                        mCheck5.setChecked(true);
                    }
                    s = object.getString("slot_6");
                    if (s != null) {
                        mCheck6.setChecked(true);
                    }
                    s = object.getString("slot_7");
                    if (s != null) {
                        mCheck7.setChecked(true);
                    }
                    s = object.getString("slot_8");
                    if (s != null) {
                        mCheck8.setChecked(true);
                    }
                    s = object.getString("slot_9");
                    if (s != null) {
                        mCheck9.setChecked(true);
                    }
                    s = object.getString("slot_10");
                    if (s != null) {
                        mCheck10.setChecked(true);
                    }
                    s = object.getString("slot_11");
                    if (s != null) {
                        mCheck11.setChecked(true);
                    }
                    s = object.getString("slot_12");
                    if (s != null) {
                        mCheck12.setChecked(true);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d("volley", "Error: " + volleyError.getMessage());
            }
        });
        VolleyController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
