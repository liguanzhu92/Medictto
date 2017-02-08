package com.svs.myprojects.mymedicalrecords.patientrecord.makereservation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.svs.myprojects.mymedicalrecords.Constants;
import com.svs.myprojects.mymedicalrecords.R;
import com.svs.myprojects.mymedicalrecords.doctor.utils.AppointmentValues;
import com.svs.myprojects.mymedicalrecords.patientrecord.utils.VolleyController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by snehalsutar on 2/18/16.
 */
public class FragmentMakeAppointment extends Fragment {
    private Button requestAptmntButton;
    private TextView apptmntText, apptmntSlot, mTextDocName, mTextDocEmail, mTextDocPhone;
    private CalendarView mCalendarView;
    private ImageView mImageDoc;
/*    private Spinner spinnerStartTime, spinnerEndTime;*/
    private String mStartTimeSelected, mEndTimeSelected;
    private HorizontalScrollView timeSlots;
    private Context mContext;
    private AppointmentValues mAppointment = new AppointmentValues();
    private String[] mSlots = new String[13];
    private static final String TIME_SLOT = "http://rjtmobile.com/medictto/appointment_available.php?&";
    // http://rjtmobile.com/medictto/appointment_available.php?&doctorID=101&currentdate=2017-02-07
    private static final String APPOINTMENT_URL = "http://rjtmobile.com/medictto/appointment_book.php?&";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_make_appointment, container, false);
        requestAptmntButton = (Button) rootView.findViewById(R.id.req_apptmnt_button);
        apptmntText = (TextView) rootView.findViewById(R.id.apptmnt_text);
        apptmntSlot = (TextView) rootView.findViewById(R.id.apptmnt_time_slot);
/*        spinnerStartTime = (Spinner) rootView.findViewById(R.id.spinner_start_time);
        spinnerEndTime = (Spinner) rootView.findViewById(R.id.spinner_end_time);*/
        timeSlots = (HorizontalScrollView) rootView.findViewById(R.id.time_slots);
        mTextDocName = (TextView) rootView.findViewById(R.id.doctor_name);
        mTextDocEmail = (TextView) rootView.findViewById(R.id.doctor_email);
        mTextDocPhone = (TextView) rootView.findViewById(R.id.doctor_phone);
        mImageDoc = (ImageView) rootView.findViewById(R.id.doctor_image);
        mCalendarView = (CalendarView) rootView.findViewById(R.id.make_appointment_calendar);
        // String date = "2017-02-07";
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(calendar.getTime());
        getInfor(date);
        setDoctorDetails(getArguments());
        return rootView;
    }

    private void getInfor(String date) {
        mAppointment.setPatientID("400");
        mAppointment.setDoctorId("101");
        // get slot infor
        apptmntText.setText(date);
        String url = TIME_SLOT + "&doctorID=" + mAppointment.getDoctorId() + "&currentdate=" + date;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray array = jsonObject.getJSONArray("appointment slot");
                    JSONObject object = array.getJSONObject(0);
                    VolleyLog.d("volley", object);
                    mSlots[1] = object.getString("slot_1");
                    mSlots[2] = object.getString("slot_2");
                    mSlots[3] = object.getString("slot_3");
                    mSlots[4] = object.getString("slot_4");
                    mSlots[5] = object.getString("slot_5");
                    mSlots[6] = object.getString("slot_6");
                    mSlots[7] = object.getString("slot_7");
                    mSlots[8] = object.getString("slot_8");
                    mSlots[9] = object.getString("slot_9");
                    mSlots[10] = object.getString("slot_10");
                    mSlots[11] = object.getString("slot_11");
                    mSlots[12] = object.getString("slot_12");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setTimeSlots();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d("volley", "Error: " + volleyError.getMessage());
                for (int i = 0; i < mSlots.length; i++) {
                    mSlots[i] = "1";
                }
                setTimeSlots();
            }
        });
        VolleyController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.frag_title_book_appointment);
    }

    private void setDoctorDetails(Bundle bundle) {
        String json = bundle.getString(Constants.GROUP_HEADER_ITEMS);
        GroupHeaderItems groupHeaderItems = null;
        Gson gson = new Gson();
        if (json != null) {
            Type type = new TypeToken<GroupHeaderItems>() {}.getType();
            groupHeaderItems = gson.fromJson(json, type);
        }
        if (groupHeaderItems != null) {
            mAppointment.setDoctorId(groupHeaderItems.doctorID);
            mTextDocName.setText(groupHeaderItems.docName);
            mTextDocEmail.setText(groupHeaderItems.docEmail);
            mTextDocPhone.setText(groupHeaderItems.phoneNum);
            Picasso.with(mContext)
                    .load(groupHeaderItems.docImageUrl)
                    .placeholder(R.drawable.pill_baw)
                    .fit()
                    .centerCrop()
                    .into(mImageDoc);
        }
    }

    private void setTimeSlots() {
        // check data in web service
        LinearLayout linearLayoutHor = new LinearLayout(mContext);
        linearLayoutHor.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutHor.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        String[] timings = mContext.getResources().getStringArray(R.array.timings_array);


        for (int i = 0; i < timings.length; i++) {
            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            //ImageView Setup
            ImageView imageView = new ImageView(mContext);
            //setting image resource
//        imageView.setImageResource(R.drawable.pill_baw);
            imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.teal200));
            if (mSlots[i+1].equals("1")) {
                imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.teal900));
            }
            //setting image position
//        imageView.setLayoutParams(new );
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    mContext.getResources().getDimensionPixelSize(R.dimen.image_width),
                    mContext.getResources().getDimensionPixelSize(R.dimen.image_height)));
            imageView.setOnClickListener(clickImage(i, timings[i]));

            TextView timeText = new TextView(mContext);
            timeText.setLayoutParams(new LinearLayout.LayoutParams(mContext.getResources().getDimensionPixelSize(R.dimen.text_width),
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            timeText.setText(timings[i]);
            timeText.setTextColor(ContextCompat.getColor(mContext, R.color.secondary_text));
//        timeText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


            //adding view to layout
            linearLayout.addView(imageView);
            linearLayout.addView(timeText);
            linearLayoutHor.addView(linearLayout);

        }
        timeSlots.removeAllViews();
        timeSlots.addView(linearLayoutHor);
        //make visible to program
//        setContentView(linearLayout);
    }

    private View.OnClickListener clickImage(final int i, final String interval) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(mContext, time, Toast.LENGTH_LONG).show();
                if (mSlots[i].equals("1")) {
                    Toast.makeText(getContext(), "Not Available", Toast.LENGTH_LONG).show();
                    return;
                }
                String[] startTime;
                startTime = getActivity().getResources().getStringArray(R.array.book_time);
                mAppointment.setStartTime(startTime[i]);
                mAppointment.setEndTime(startTime[i+1]);
                apptmntSlot.setText(interval);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        requestAptmntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postAppointment();
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
                mAppointment.setDate(s);
                getInfor(s);
            }
        });
    }

    private void postAppointment() {
        String url = APPOINTMENT_URL + "patientID=" + mAppointment.getPatientID() + "&doctorID=" + mAppointment.getDoctorId()
                + "&startTime=" + mAppointment.getStartTime() + "&endTime=" + mAppointment.getEndTime();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.contains("Confirmed")) {
                    Toast.makeText(mContext,"Appointment Confirmed", Toast.LENGTH_LONG).show();
                    updateTimeSlot();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d("volley", "Error: " + volleyError.getMessage());
            }
        });
        VolleyController.getInstance().addToRequestQueue(stringRequest);
    }

    private void updateTimeSlot() {
        setTimeSlots();
    }

}
