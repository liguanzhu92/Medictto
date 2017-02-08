package com.svs.myprojects.mymedicalrecords.doctor.appointment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import static com.svs.myprojects.mymedicalrecords.doctor.request.RequestAdapter.CONFIRM_URL;

/**
 * Created by Guanzhu Li on 2/7/2017.
 */
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentHolder> {

    private Context mContext;
    private ConfirmedList mConfirmedList = ConfirmedList.getInstance();
    private String[] timeSlots;

    public AppointmentAdapter(Context context) {
        mContext = context;
        timeSlots =  context.getResources().getStringArray(R.array.timings_array);
    }

    @Override
    public AppointmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardview_appointment, parent, false);
        return new AppointmentHolder(v);
    }

    @Override
    public void onBindViewHolder(AppointmentHolder holder, final int position) {
        final AppointmentValues appointment = mConfirmedList.get(position);
        Log.d("set", appointment.toString());
        holder.mTextPatientID.setText(appointment.getPatientID());
        holder.mTextDate.setText(appointment.getDate());
        int p = appointment.getSlots();
        holder.mTextSlot.setText(timeSlots[p]);
        holder.mImageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(position);
                appointment.setStatus("0");
                RequestList.getInstance().add(appointment);
                sendRequest(appointment);
            }
        });
    }

    private void sendRequest(AppointmentValues appointment) {
        String url = CONFIRM_URL + appointment.getDoctorId() + "&appointmentNo=" + appointment.getNumber()
                + "&isConfirmed=0";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.contains("confirmed")) {
                    Toast.makeText(mContext, "Appointment Cancel", Toast.LENGTH_LONG).show();
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
        mConfirmedList.remove(position);
        notifyItemChanged(position, mConfirmedList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mConfirmedList.size();
    }
}

class AppointmentHolder extends RecyclerView.ViewHolder {
    TextView mTextPatientID, mTextDate, mTextSlot;
    ImageView mImageConfirm, mImageCancel;

    public AppointmentHolder(View itemView) {
        super(itemView);
        mTextPatientID = (TextView) itemView.findViewById(R.id.card_patientId);
        mTextDate = (TextView) itemView.findViewById(R.id.card_date);
        mTextSlot = (TextView) itemView.findViewById(R.id.card_slot);
        mImageCancel = (ImageView) itemView.findViewById(R.id.card_cancel);
        mImageConfirm = (ImageView) itemView.findViewById(R.id.card_confirm);
        mImageConfirm.setVisibility(View.GONE);
    }
}
