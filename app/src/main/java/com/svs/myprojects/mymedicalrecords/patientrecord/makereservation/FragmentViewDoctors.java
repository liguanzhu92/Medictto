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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.svs.myprojects.mymedicalrecords.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snehalsutar on 3/1/16.
 */

public class FragmentViewDoctors extends Fragment implements AdapterView.OnItemClickListener {

    View mRootView;
    ArrayList<DoctorDetail> arrayList;
    private AnimatedExpandableListView listView;
    private ExampleAdapter adapter;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_view_doctor_list, container, false);
        mContext = getActivity();
        arrayList = new ArrayList<>();
        fillArrayList();
        return mRootView;
    }

    private void fillArrayList() {
        String[] docName = getActivity().getResources().getStringArray(R.array.doctor_name);
        String[] docEmail = getActivity().getResources().getStringArray(R.array.doctor_email);
        String[] docImage = getActivity().getResources().getStringArray(R.array.doctor_image);
        String[] docSpec = getActivity().getResources().getStringArray(R.array.doctor_speciality);
        String[] docQual = getActivity().getResources().getStringArray(R.array.doctor_qualification);

        for (int i = 0; i < docName.length; i++) {
            arrayList.add(new DoctorDetail(docName[i], docSpec[i], docQual[i],
                    docImage[i], "", "", "+1 888 999 7777", docEmail[i], "5"
            /*this.doctorName = doctorName;
            this.doctorSpeciality = doctorSpeciality;
            this.doctorQualification = doctorQualification;
            this.imageUrl = imageUrl;
            this.operationHours = operationHours;
            this.address = address;
            this.phoneNum = phoneNum;
            this.emailId = emailId;*/
            ));
        }

        List<GroupHeaderItems> items = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            GroupHeaderItems item = new GroupHeaderItems();
//            item.title = "Group " + i;
            item.docImageUrl = arrayList.get(i).getImageUrl();
            item.docName = arrayList.get(i).getDoctorName();
            item.qualification = arrayList.get(i).getDoctorQualification();
            item.rating = arrayList.get(i).getRating();
            item.phoneNum = arrayList.get(i).getPhoneNum();
            item.docEmail = arrayList.get(i).getEmailId();

            GroupChildItems child = new GroupChildItems();
//            child.title = "Awesome item ";
//            child.hint = "Too awesome";
            item.items.add(child);

            items.add(item);
        }
        /*// Populate our list with groups and it's children
        for (int i = 1; i < 100; i++) {
            GroupItem item = new GroupItem();

            item.title = "Group " + i;

            for (int j = 0; j < i; j++) {
                ChildItem child = new ChildItem();
                child.title = "Awesome item " + j;
                child.hint = "Too awesome";

                item.items.add(child);
            }

            items.add(item);
        }*/

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
        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.doctor_name, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);*/
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT)
                .show();
    }
}

class FragmentViewAdapter extends BaseAdapter {

    public FragmentViewAdapter() {
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
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
//            holder.title = (TextView) convertView.findViewById(R.id.textTitle);
//            holder.hint = (TextView) convertView.findViewById(R.id.textHint);
            holder.buttonBookAppointment = (Button) convertView.findViewById(R.id.button_book_appointment);
            convertView.setTag(holder);
        } else {
            holder = (GroupChildHolder) convertView.getTag();
        }

//        holder.title.setText(item.title);
//        holder.hint.setText(item.hint);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

