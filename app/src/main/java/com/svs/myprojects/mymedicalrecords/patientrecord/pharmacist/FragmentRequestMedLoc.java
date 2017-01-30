package com.svs.myprojects.mymedicalrecords.patientrecord.pharmacist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.svs.myprojects.mymedicalrecords.R;

import java.util.ArrayList;

/**
 * Created by snehalsutar on 3/3/16.
 */
public class FragmentRequestMedLoc extends Fragment implements View.OnClickListener {

    View mRootView;
    Context mContext;
    Button mNextButton;
    Spinner spinnerPharmaLoc;
    String mLocationSelected;
    RecyclerView mRecyclerViewPharmaLoc;
    RequestMedInterface requestMedInterface;
    ArrayList<String> arrayList;
    FragmentRequestMedLocAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mRootView = inflater.inflate(R.layout.fragment_request_med_loc, container, false);
        setupSpinnerValues();
        getHandlers();


        return mRootView;
    }

    private void getHandlers() {
        mRecyclerViewPharmaLoc = (RecyclerView) mRootView.findViewById(R.id.recyclerViewPharmaLoc);
        mNextButton = (Button) mRootView.findViewById(R.id.button_next);
        mNextButton.setOnClickListener(this);
        arrayList = new ArrayList<>();
        requestMedInterface = (RequestMedInterface) getActivity();
        fillArrayList();
        adapter = new FragmentRequestMedLocAdapter(mContext, arrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerViewPharmaLoc.setLayoutManager(layoutManager);
        mRecyclerViewPharmaLoc.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void fillArrayList() {
        String[] locationArr = mContext.getResources().getStringArray(R.array.location_name);
        for (int i = 1; i < locationArr.length; i++) {
            arrayList.add(locationArr[i]);
        }
    }

    private void setupSpinnerValues() {
        spinnerPharmaLoc = (Spinner) mRootView.findViewById(R.id.spinner_pharma_loc);
        String[] locationArray = getActivity().getResources().getStringArray(R.array.location_name);
        ArrayAdapter<String> adapterST = new ArrayAdapter<>(
                getActivity(), R.layout.spinner_list_item, locationArray);
        spinnerPharmaLoc.setAdapter(adapterST);
        spinnerPharmaLoc.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mLocationSelected = spinnerPharmaLoc.getSelectedItem().toString();
                        Toast.makeText(getActivity(), "You have selected " + mLocationSelected, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_next:
                requestMedInterface.callRequestMedFrag();
                break;
            default:
                break;
        }
    }
}

class FragmentRequestMedLocAdapter extends RecyclerView.Adapter<FragmentRequestMedLocAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<String> mArrayList;

    FragmentRequestMedLocAdapter(Context context, ArrayList<String> arrayList) {
        mContext = context;
        mArrayList = arrayList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTextLocation;
        SparseBooleanArray selectedItems;
        ImageView myBackground;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextLocation = (TextView) itemView.findViewById(R.id.text_pharma_loc);
            myBackground = (ImageView) itemView.findViewById(R.id.my_background);
        }

        @Override
        public void onClick(View v) {
            // Save the selected positions to the SparseBooleanArray
            if (selectedItems.get(getAdapterPosition(), false)) {
                selectedItems.delete(getAdapterPosition());
                myBackground.setSelected(false);
            }
            else {
                selectedItems.put(getAdapterPosition(), true);
                myBackground.setSelected(true);
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_request_med_loc_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // Set the selected state of the row depending on the position
        holder.mTextLocation.setSelected(holder.selectedItems.get(position, false));
        String loc = mArrayList.get(position);
        holder.mTextLocation.setText(loc);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }
}