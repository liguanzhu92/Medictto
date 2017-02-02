package com.svs.myprojects.mymedicalrecords.patientrecord.makereservation;

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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.svs.myprojects.mymedicalrecords.R;

import java.util.ArrayList;

/**
 * Created by snehalsutar on 3/1/16.
 */

public class FragmentDocSpeciality extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    View mRootView;
    ArrayList<DocSpecItems> arrayList;
    FragmentDocSpecialityAdapter adapter;
    GridView gridView;
    FragmentDocSpecInterface docSpecInterface;
    Spinner spinnerLocation;
    String mLocationSelected;
    ImageView generalDocImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_doc_speciality, container, false);
        docSpecInterface = (FragmentDocSpecInterface) getActivity();
        gridView = (GridView) mRootView.findViewById(R.id.doc_speciality_grid);
        generalDocImg = (ImageView) mRootView.findViewById(R.id.doc_spec_image);
        Picasso.with(getActivity())
                .load("http://www.medicalwastega.com/images/doctors_medical_waste_disposal.jpg")
                .placeholder(R.drawable.pill_baw)
                .fit()
                .centerCrop()
                .into(generalDocImg);
        generalDocImg.setOnClickListener(this);
        arrayList = new ArrayList<>();
        arrayList.add(new DocSpecItems("https://higherlogicdownload.s3.amazonaws.com/NADP/d5e8b59f-3f90-4f2a-88a4-18faaee8a646/UploadedImages/shutterstock_63465202.jpg", "DENTAL"));
        arrayList.add(new DocSpecItems("http://west11thstreetpediatrics.com/wp-content/uploads/2012/02/img-aboutus.jpg", "PEDIATRICIAN"));
        arrayList.add(new DocSpecItems("http://centralortho.com/images/home/home-pic.jpg", "ORTHOPEDIC"));
        arrayList.add(new DocSpecItems("http://internetmedicine.com/wp-content/uploads/2012/09/neurologist1.jpg", "NEUROLOGIST"));
        arrayList.add(new DocSpecItems("http://www.almashospital.com/wp-content/uploads/2013/03/cardiology.jpg", "CARDIOLOGIST"));
        arrayList.add(new DocSpecItems("http://cdn3-www.thefashionspot.com/assets/uploads/2015/08/facial.jpg", "DERMATOLOGIST"));
        arrayList.add(new DocSpecItems("https://static.abdserotec.com/2014/coagulation-and-hematology-antibodies_210_210.jpg", "HEMATOLOGIST"));
        arrayList.add(new DocSpecItems("http://www.iran-daily.com/File/File/125076", "RADIOLOGIST"));
        arrayList.add(new DocSpecItems("https://edc2.healthtap.com/ht-staging/user_answer/avatars/991561/large/open-uri20130331-2947-1ypdfij.jpeg?1386580006", "OPTHOMOLOGIST"));

        adapter = new FragmentDocSpecialityAdapter(arrayList, getActivity());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(FragmentDocSpeciality.this);

        spinnerLocation = (Spinner) mRootView.findViewById(R.id.spinner_location);
        String[] timeArray = getActivity().getResources().getStringArray(R.array.location_name);
        ArrayAdapter<String> adapterST = new ArrayAdapter<>(
                getActivity(), R.layout.spinner_list_item, timeArray);
        spinnerLocation.setAdapter(adapterST);
        spinnerLocation.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mLocationSelected = spinnerLocation.getSelectedItem().toString();
                        Toast.makeText(getActivity(), "You have selected " + spinnerLocation.getSelectedItem().toString(),
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.frag_title_select_loc);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("click", "item click " + position );
        docSpecInterface.callViewDoctorList();
    }

    @Override
    public void onClick(View v) {
        Log.d("click", " click ");
        docSpecInterface.callViewDoctorList();
    }
}

class FragmentDocSpecialityAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<DocSpecItems> itemsArrayList;

    FragmentDocSpecialityAdapter(ArrayList<DocSpecItems> arrayList, Context context) {
        mContext = context;
        itemsArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return itemsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = inflater.inflate(R.layout.fragment_doc_speciality_item, null);

            DocSpecItems docSpecItems = itemsArrayList.get(position);

            // set value into text view
            TextView textView = (TextView) gridView.findViewById(R.id.spec_name);
            textView.setText(docSpecItems.getSpecName());

            // set image based on selected text
            ImageView imageView = (ImageView) gridView.findViewById(R.id.doc_spec_image);
            Picasso.with(mContext)
                    .load(docSpecItems.getImageUrl())
                    .placeholder(R.drawable.pill_baw)
                    .fit()
                    .centerCrop()
                    .into(imageView);

        } else {
            gridView = convertView;
        }

        return gridView;
    }
}
