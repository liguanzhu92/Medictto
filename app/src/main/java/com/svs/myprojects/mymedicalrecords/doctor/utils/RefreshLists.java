package com.svs.myprojects.mymedicalrecords.doctor.utils;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.svs.myprojects.mymedicalrecords.patientrecord.utils.VolleyController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Guanzhu Li on 2/7/2017.
 */
public class RefreshLists {
    private static final String BASE_URL = "http://rjtmobile.com/medictto/check_appointment_request.php?&docID=";
    private RequestList mRequestList = RequestList.getInstance();
    private ConfirmedList mConfirmedList = ConfirmedList.getInstance();

    public void refreshData(final String id) {
        String url = BASE_URL + id;
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray array = jsonObject.getJSONArray("Appointment Request");
                    for (int i = 0; i < array.length(); i++) {
                        AppointmentValues appointment = new AppointmentValues();
                        JSONObject obj = array.getJSONObject(i);
                        Log.d("volley", obj.toString());
                        appointment.setDoctorId(id);
                        appointment.setNumber(obj.getString("AppointmentNo"));
                        appointment.setPatientID(obj.getString("PatientID"));
                        String time = obj.getString("StartTime");
                        separateTime(time, appointment);
                        String s = obj.getString("IsConfirmed");
                        appointment.setStatus(s);
                        if (s.equals("1")) {
                            mConfirmedList.add(appointment);
                        } else {
                            mRequestList.add(appointment);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("volley", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d("volley", "Error: " + volleyError.getMessage());
            }
        });
        VolleyController.getInstance().addToRequestQueue(objectRequest);
    }

    private void separateTime(String time, AppointmentValues appointment) {
        int position = time.indexOf(' ');
        String date = time;
        appointment.setDate(date.substring(0, position));
        String getTime =time.substring(position + 1, position + 3);
        Log.d("refresh", time);
        switch (getTime) {
            case "07":
                appointment.setSlots(0);
                break;
            case "08":
                appointment.setSlots(1);
                break;
            case "09":
                appointment.setSlots(2);
                break;
            case "10":
                appointment.setSlots(3);
                break;
            case "11":
                appointment.setSlots(4);
                break;
            case "12":
                appointment.setSlots(5);
                break;
            case "13":
                appointment.setSlots(6);
                break;
            case "14":
                appointment.setSlots(7);
                break;
            case "15":
                appointment.setSlots(8);
                break;
            case "16":
                appointment.setSlots(9);
                break;
            case "17":
                appointment.setSlots(10);
                break;
            case "18":
                appointment.setSlots(11);
                break;
            default:
                appointment.setSlots(12);
                break;
        }
    }
}
