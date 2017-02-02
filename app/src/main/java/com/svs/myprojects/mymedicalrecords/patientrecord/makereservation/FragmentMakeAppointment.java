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
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.svs.myprojects.mymedicalrecords.Constants;
import com.svs.myprojects.mymedicalrecords.R;
import com.svs.myprojects.mymedicalrecords.patientrecord.utils.VolleyController;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by snehalsutar on 2/18/16.
 */
public class FragmentMakeAppointment extends Fragment {
    private Button requestAptmntButton;
    private TextView apptmntText, mTextDocName, mTextDocEmail, mTextDocPhone;
    private CalendarView mCalendarView;
    private ImageView mImageDoc;
    private Spinner spinnerStartTime, spinnerEndTime;
    private String mStartTimeSelected, mEndTimeSelected;
    private HorizontalScrollView timeSlots;
    private Context mContext;
    private AppointmentValues mAppointment = new AppointmentValues();

    private static final String APPOINTMENT_URL = "http://rjtmobile.com/medictto/appointment_book.php?";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_make_appointment, container, false);
        requestAptmntButton = (Button) rootView.findViewById(R.id.req_apptmnt_button);
        apptmntText = (TextView) rootView.findViewById(R.id.apptmnt_text);
        spinnerStartTime = (Spinner) rootView.findViewById(R.id.spinner_start_time);
        spinnerEndTime = (Spinner) rootView.findViewById(R.id.spinner_end_time);
        timeSlots = (HorizontalScrollView) rootView.findViewById(R.id.time_slots);
        mTextDocName = (TextView) rootView.findViewById(R.id.doctor_name);
        mTextDocEmail = (TextView) rootView.findViewById(R.id.doctor_email);
        mTextDocPhone = (TextView) rootView.findViewById(R.id.doctor_phone);
        mImageDoc = (ImageView) rootView.findViewById(R.id.doctor_image);
        mCalendarView = (CalendarView) rootView.findViewById(R.id.make_appointment_calendar);
        getInfor();
        setSpinnerItems();
        setTimeSlots();
        setDoctorDetails(getArguments());
        return rootView;
    }

    private void getInfor() {
        mAppointment.setPatientID("400");
        mAppointment.setDoctorId("101");
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

    private void setSpinnerItems() {
        String[] timeArray = getActivity().getResources().getStringArray(R.array.book_time);
        ArrayAdapter<String> adapterST = new ArrayAdapter<>(
                getActivity(), R.layout.spinner_list_item, timeArray);
        spinnerStartTime.setAdapter(adapterST);
        ArrayAdapter<String> adapterET = new ArrayAdapter<>(
                getActivity(), R.layout.spinner_list_item, timeArray);
        spinnerEndTime.setAdapter(adapterET);
        spinnerStartTime.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mStartTimeSelected = spinnerStartTime.getSelectedItem().toString();
                        mAppointment.setStartTime(mStartTimeSelected);
                        apptmntText.setText(mAppointment.toString());
                        spinnerEndTime.setSelection(position + 1);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        spinnerEndTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mEndTimeSelected = spinnerEndTime.getSelectedItem().toString();
                spinnerStartTime.setSelection(position - 1);
                mAppointment.setEndTime(mEndTimeSelected);
                apptmntText.setText(mAppointment.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
            if (i == 4 || i == 8) {
                imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.teal900));
            }
            //setting image position
//        imageView.setLayoutParams(new );
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    mContext.getResources().getDimensionPixelSize(R.dimen.image_width),
                    mContext.getResources().getDimensionPixelSize(R.dimen.image_height)));
            imageView.setOnClickListener(clickImage(timings[i]));

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
        timeSlots.addView(linearLayoutHor);
        //make visible to program
//        setContentView(linearLayout);
    }

    private View.OnClickListener clickImage(final String time) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, time, Toast.LENGTH_LONG).show();
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
                String s = "" + i + "-" + i1 + "-" + i2;
                mAppointment.setDate(s);
            }
        });
    }

    private void postAppointment() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPOINTMENT_URL, new Response.Listener<String>() {
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> para = new HashMap<>();
                para.put("patientID", mAppointment.getPatientID());
                para.put("doctorID", mAppointment.getDoctorId());
                para.put("startTime", mAppointment.getDate() + " " + mAppointment.getStartTime());
                para.put("endTime", mAppointment.getDate() + " " + mAppointment.getEndTime());
                return para;
            }
        };
        VolleyController.getInstance().addToRequestQueue(stringRequest);
    }

    private void updateTimeSlot() {
        setTimeSlots();
    }

    public static class AppointmentValues {
        private String mEndTime;
        private String mStartTime;
        private String mDoctorId;
        private String mPatientID;
        private String mDate;

        public String getEndTime() {
            return mEndTime;
        }

        public void setEndTime(String endTime) {
            mEndTime = endTime;
        }

        public String getStartTime() {
            return mStartTime;
        }

        public void setStartTime(String startTime) {
            mStartTime = startTime;
        }

        public String getDoctorId() {
            return mDoctorId;
        }

        public void setDoctorId(String doctorId) {
            mDoctorId = doctorId;
        }

        public String getPatientID() {
            return mPatientID;
        }

        public void setPatientID(String patientID) {
            mPatientID = patientID;
        }

        public String getDate() {
            return mDate;
        }

        public void setDate(String date) {
            mDate = date;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Your Appointment");
            stringBuilder.append("\n");
            stringBuilder.append("Date: " + mDate);
            stringBuilder.append("\n");
            stringBuilder.append("Start: " + mStartTime);
            stringBuilder.append("\n");
            stringBuilder.append("End: " + mEndTime);
            return stringBuilder.toString();
        }
    }
}
