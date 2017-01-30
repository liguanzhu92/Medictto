package com.svs.myprojects.mymedicalrecords.patientrecord.makereservation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.svs.myprojects.mymedicalrecords.Constants;
import com.svs.myprojects.mymedicalrecords.R;

import java.lang.reflect.Type;

/**
 * Created by snehalsutar on 2/18/16.
 */
public class FragmentMakeAppointment extends Fragment {
    Firebase mRef;
    Button requestAptmntButton;
    TextView apptmntText, mTextDocName, mTextDocEmail, mTextDocPhone;
    ImageView mImageDoc;
    Spinner spinnerStartTime, spinnerEndTime, spinnerSelectDoc;
    String mStartTimeSelected, mEndTimeSelected;
    String mDocEmailSelected;
    HorizontalScrollView timeSlots;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_make_appointment, container, false);
        requestAptmntButton = (Button) rootView.findViewById(R.id.req_apptmnt_button);
        apptmntText = (TextView) rootView.findViewById(R.id.apptmnt_text);
        spinnerStartTime = (Spinner) rootView.findViewById(R.id.spinner_start_time);
        spinnerEndTime = (Spinner) rootView.findViewById(R.id.spinner_end_time);
        spinnerSelectDoc = (Spinner) rootView.findViewById(R.id.spinner_select_doctor);
        timeSlots = (HorizontalScrollView) rootView.findViewById(R.id.time_slots);
        mTextDocName = (TextView) rootView.findViewById(R.id.doctor_name);
        mTextDocEmail = (TextView) rootView.findViewById(R.id.doctor_email);
        mTextDocPhone = (TextView) rootView.findViewById(R.id.doctor_phone);
        mImageDoc = (ImageView) rootView.findViewById(R.id.doctor_image);
        setSpinnerItems();
        setTimeSlots();
        setDoctorDetails(getArguments());

        return rootView;
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
        ArrayAdapter<String> adapterST = new ArrayAdapter<String>(
                getActivity(), R.layout.spinner_list_item, timeArray);
        spinnerStartTime.setAdapter(adapterST);
        spinnerStartTime.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mStartTimeSelected = spinnerStartTime.getSelectedItem().toString();
                        Toast.makeText(getActivity(),
                                "You have selected " + spinnerStartTime.getSelectedItem().toString(),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        ArrayAdapter<String> adapterET = new ArrayAdapter<String>(
                getActivity(), R.layout.spinner_list_item, timeArray);
        spinnerEndTime.setAdapter(adapterET);
        spinnerEndTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mEndTimeSelected = spinnerEndTime.getSelectedItem().toString();
                Toast.makeText(getActivity(),
                        "You have selected " + spinnerEndTime.getSelectedItem().toString(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> adapterDocList = new ArrayAdapter<String>(
                getActivity(), R.layout.spinner_list_item, getActivity().getResources().getStringArray(R.array.doctor_name));
        final String[] docEmail = getActivity().getResources().getStringArray(R.array.doctor_email);
        spinnerSelectDoc.setAdapter(adapterDocList);
        spinnerSelectDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDocEmailSelected = spinnerSelectDoc.getSelectedItem().toString();
                Toast.makeText(getActivity(),
                        "You have selected " + spinnerSelectDoc.getSelectedItem().toString() + " " + docEmail[position],
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setTimeSlots() {
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
            imageView.setBackgroundColor(mContext.getResources().getColor(R.color.tealA200));
            if (i == 4 || i == 8) {
                imageView.setBackgroundColor(mContext.getResources().getColor(R.color.colorGrey));
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
            timeText.setTextColor(mContext.getResources().getColor(R.color.tealA100));
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

        mRef = new Firebase("https://radiant-torch-9718.firebaseio.com/Doctor/2016/02/20    ");
//        mRef = new Firebase("https://docs-examples.firebaseio.com/web/saving-data/fireblog/posts");

        //Referencing the child node using a .child() on it's parent node
//        mRef.child("fullName").child("svs").setValue("Alan Turing");
//        mRef.child("birthYear").setValue(1912);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dates = "";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AppointmentValues appointment = postSnapshot.getValue(AppointmentValues.class);
                    dates = dates + appointment.getStartTime() + "   " + appointment.getEndTime() + "\n";
                }
                apptmntText.setText(" Booked Timings for seleced date are: \n" + dates);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        requestAptmntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase newRef = mRef.child(mStartTimeSelected);
                AppointmentValues appointmentValues = new AppointmentValues(mStartTimeSelected, mEndTimeSelected, "SVS", "RED", "123", "U r good");
                newRef.setValue(appointmentValues);
            }
        });
    }

    public static class AppointmentValues {
        String endTime;
        String eventTitle;
        String eventColor;
        String key;
        String note;
        String startTime;

        public AppointmentValues() {
        }

        public AppointmentValues(String startTime, String endTime, String eventTitle, String eventColor, String key, String note) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.eventTitle = eventTitle;
            this.eventColor = eventColor;
            this.key = key;
            this.note = note;
        }

        public String getEventColor() {
            return eventColor;
        }

        public void setEventColor(String eventColor) {
            this.eventColor = eventColor;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getEventTitle() {
            return eventTitle;
        }

        public void setEventTitle(String eventTitle) {
            this.eventTitle = eventTitle;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }
    }
}
