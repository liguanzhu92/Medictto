package com.svs.myprojects.mymedicalrecords.doctor.request;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.svs.myprojects.mymedicalrecords.R;
import com.svs.myprojects.mymedicalrecords.doctor.utils.AppointmentValues;
import com.svs.myprojects.mymedicalrecords.doctor.utils.ConfirmedList;
import com.svs.myprojects.mymedicalrecords.doctor.utils.RequestList;
import com.svs.myprojects.mymedicalrecords.patientrecord.utils.VolleyController;


/**
 * Created by Guanzhu Li on 2/7/2017.
 */
public class RequestAdapter extends RecyclerView.Adapter<RequestHolder>{

    private Context mContext;
    private RequestList mRequestList = RequestList.getInstance();
    private String[] timeSlots;
    public static final String CONFIRM_URL = "http://rjtmobile.com/medictto/appoint_confirm_by_dr.php?&docID=";
    //http://rjtmobile.com/medictto/appoint_confirm_by_dr.php?&docID=101&appointmentNo=36&isConfirmed=1

    public RequestAdapter(Context context) {
        mContext = context;
        timeSlots =  context.getResources().getStringArray(R.array.timings_array);
    }

    @Override
    public RequestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardview_appointment, parent, false);
        return new RequestHolder(v);
    }

    @Override
    public void onBindViewHolder(RequestHolder holder, final int position) {
        final AppointmentValues  appointment = mRequestList.get(position);
        holder.mTextPatientID.setText(appointment.getPatientID());
        holder.mTextDate.setText(appointment.getDate());
        int p = appointment.getSlots();
        holder.mTextSlot.setText(timeSlots[p]);
        holder.mImageConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appointment.setStatus("1");
                ConfirmedList.getInstance().add(appointment);
                removeItem(position);
                sendRequest(appointment);
            }
        });
        holder.mImageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(position);
            }
        });
    }

    private void sendRequest(AppointmentValues appointment) {
        String url = CONFIRM_URL + appointment.getDoctorId() + "&appointmentNo=" + appointment.getNumber()
                + "&isConfirmed=1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.contains("confirmed")) {
                    Toast.makeText(mContext, "Confirmed", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(mContext, "Volley Error", Toast.LENGTH_LONG).show();
            }
        });
        VolleyController.getInstance().addToRequestQueue(stringRequest);
    }

    private void removeItem(int position) {
        mRequestList.remove(position);
        notifyItemChanged(position, mRequestList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRequestList.size();
    }
}

class RequestHolder extends RecyclerView.ViewHolder{
    TextView mTextPatientID, mTextDate, mTextSlot;
    ImageView mImageConfirm, mImageCancel;

    public RequestHolder(View itemView) {
        super(itemView);
        mTextPatientID = (TextView) itemView.findViewById(R.id.card_patientId);
        mTextDate = (TextView) itemView.findViewById(R.id.card_date);
        mTextSlot = (TextView) itemView.findViewById(R.id.card_slot);
        mImageCancel = (ImageView) itemView.findViewById(R.id.card_cancel);
        mImageConfirm = (ImageView) itemView.findViewById(R.id.card_confirm);
    }
}
