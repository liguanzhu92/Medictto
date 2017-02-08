package com.svs.myprojects.mymedicalrecords.doctor.utils;

/**
 * Created by Guanzhu Li on 2/7/2017.
 */
public class AppointmentValues {
    private String mNumber;
    private String mEndTime;
    private String mStartTime;
    private String mDoctorId;
    private String mPatientID;
    private String mDate;
    private String mStatus;
    private int mSlots;

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

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

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public int getSlots() {
        return mSlots;
    }

    public void setSlots(int slots) {
        mSlots = slots;
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