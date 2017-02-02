package com.svs.myprojects.mymedicalrecords.patientrecord.makereservation;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;
import com.svs.myprojects.mymedicalrecords.R;
import com.svs.myprojects.mymedicalrecords.patientrecord.utils.VolleyController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by snehalsutar on 3/1/16.
 */

public class FragmentViewDoctors extends Fragment implements AdapterView.OnItemClickListener {
    private static final String BASE_URL = "http://rjtmobile.com/medictto/find_doctor.php?";
    private View mRootView;
    private ArrayList<DoctorDetail> arrayList;
    private AnimatedExpandableListView listView;
    private ExampleAdapter adapter;
    private Context mContext;
    private String area, speciality;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_view_doctor_list, container, false);
        mContext = getActivity();
        arrayList = new ArrayList<>();
        // get data from bundle
        area = "1";
        speciality = "1";
        getData();
        return mRootView;
    }


    private void getData() {
        final ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.show();
        String url = BASE_URL;
        if (area != null) {
            url += "&location_id=" + area;
        }
        if (speciality != null) {
            url += "&specialization_id=" + speciality;
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                pDialog.dismiss();
                try {
                    JSONArray array = jsonObject.getJSONArray("doctor_details");
                    for (int i = 0; i < array.length(); i++) {
                        DoctorDetail doctorDetail = new DoctorDetail();
                        doctorDetail.setArea(area);
                        doctorDetail.setDoctorSpeciality(speciality);
                        try {
                            JSONObject obj = array.getJSONObject(i);
                            Log.d("volley", obj.toString());
                            try {
                                doctorDetail.setDoctorID(obj.getString("doctor_id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                doctorDetail.setDoctorName(obj.getString("doctor_name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                doctorDetail.setEmail(obj.getString("doctor_email"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                doctorDetail.setPhoneNum(obj.getString("doctor_phone"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                doctorDetail.setExperience(obj.getString("Experience"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                doctorDetail.setDoctorQualification(obj.getString("Qualification"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                doctorDetail.setImageUrl(obj.getString("DoctorsPhoto"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                doctorDetail.setAddress(obj.getString("Address"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                doctorDetail.setRating(obj.getString("Rating"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        arrayList.add(doctorDetail);
                    }
                    fillArrayList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pDialog.dismiss();
                VolleyLog.d("volley", "Error: " + volleyError.getMessage());
            }
        });
        VolleyController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    private void fillArrayList() {
        List<GroupHeaderItems> items = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            GroupHeaderItems item = new GroupHeaderItems();
//            item.title = "Group " + i;
            item.docImageUrl = arrayList.get(i).getImageUrl();
            item.docName = arrayList.get(i).getDoctorName();
            item.qualification = arrayList.get(i).getDoctorQualification();
            item.rating = arrayList.get(i).getRating();
            item.phoneNum = arrayList.get(i).getPhoneNum();
            item.docEmail = arrayList.get(i).getEmail();
            item.doctorID = arrayList.get(i).getDoctorID();
            GroupChildItems child = new GroupChildItems();
            child.address = arrayList.get(i).getAddress();
//            child.hint = "Too awesome";
            item.items.add(child);

            items.add(item);
        }

        adapter = new ExampleAdapter(getActivity());
        adapter.setData(items);

        listView = (AnimatedExpandableListView) mRootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // In order to show animations, we need to use a custom click handler
        // for our ExpandableListView.
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (listView.isGroupExpanded(groupPosition)) {
                    listView.collapseGroupWithAnimation(groupPosition);
                } else {
                    listView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.frag_title_view_doctors);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT)
                .show();
    }
}

class ExampleAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private LayoutInflater inflater;
    private List<GroupHeaderItems> items;
    Context mContext;
    FragmentDocSpecInterface docSpecInterface;

    public ExampleAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        docSpecInterface = (FragmentDocSpecInterface)context;
    }

    public void setData(List<GroupHeaderItems> items) {
        this.items = items;
    }

    @Override
    public GroupChildItems getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition).items.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        GroupChildHolder holder;
        GroupChildItems item = getChild(groupPosition, childPosition);
        final GroupHeaderItems groupHeaderItems = getGroup(groupPosition);
        if (convertView == null) {
            holder = new GroupChildHolder();
            convertView = inflater.inflate(R.layout.frag_doc_list_item, parent, false);
           holder.address = (TextView) convertView.findViewById(R.id.doc_address);
//            holder.hint = (TextView) convertView.findViewById(R.id.textHint);
            holder.buttonBookAppointment = (Button) convertView.findViewById(R.id.button_book_appointment);
            convertView.setTag(holder);
        } else {
            holder = (GroupChildHolder) convertView.getTag();
        }

        holder.address.setText(item.address);
        holder.buttonBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Clicked", Toast.LENGTH_LONG).show();
                docSpecInterface.callMakeAppointment(groupHeaderItems);
            }
        });

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return items.get(groupPosition).items.size();
    }

    @Override
    public GroupHeaderItems getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHeaderHolder holder;
        GroupHeaderItems item = getGroup(groupPosition);
        if (convertView == null) {
            holder = new GroupHeaderHolder();
            convertView = inflater.inflate(R.layout.frag_doc_list_header, parent, false);
            holder.textDocName = (TextView) convertView.findViewById(R.id.doc_name);
            holder.textDocRating = (TextView) convertView.findViewById(R.id.doc_rating);
            holder.textDocQual = (TextView) convertView.findViewById(R.id.doc_qualification);
            holder.textDocPhone = (TextView) convertView.findViewById(R.id.doc_phone);
            holder.imageDocPhoto = (ImageView) convertView.findViewById(R.id.doc_photo);
            convertView.setTag(holder);
        } else {
            holder = (GroupHeaderHolder) convertView.getTag();
        }

        holder.textDocName.setText(item.docName);
        holder.textDocPhone.setText(item.phoneNum);
        holder.textDocQual.setText(item.qualification);
        holder.textDocRating.setText(item.rating);
        Picasso.with(mContext)
                .load(item.docImageUrl)
                .placeholder(R.drawable.pill_baw)
                .fit()
                .centerCrop()
                .into(holder.imageDocPhoto);


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

}


