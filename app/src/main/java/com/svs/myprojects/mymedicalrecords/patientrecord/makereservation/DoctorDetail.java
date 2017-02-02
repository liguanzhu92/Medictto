package com.svs.myprojects.mymedicalrecords.patientrecord.makereservation;

/**
 * Created by snehalsutar on 3/3/16.
 */
public
class DoctorDetail {
    private String mDoctorID;
    private String mDoctorName;
    private String mDoctorSpeciality;
    private String mDoctorQualification;
    private String mImageUrl;
    private String mExperience;
    private String mArea;
    private String mAddress;
    private String mPhoneNum;
    private String mEmail;
    private String mRating;

    public DoctorDetail() {}

    public String getDoctorID() {
        return mDoctorID;
    }

    public String getExperience() {
        return mExperience;
    }

    public void setExperience(String experience) {
        mExperience = experience;
    }

    public void setDoctorID(String doctorID) {
        mDoctorID = doctorID;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getPhoneNum() {
        return mPhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        mPhoneNum = phoneNum;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getDoctorName() {
        return mDoctorName;
    }

    public void setDoctorName(String doctorName) {
        mDoctorName = doctorName;
    }

    public String getDoctorSpeciality() {
        return mDoctorSpeciality;
    }

    public void setDoctorSpeciality(String doctorSpeciality) {
        mDoctorSpeciality = doctorSpeciality;
    }

    public String getDoctorQualification() {
        return mDoctorQualification;
    }

    public void setDoctorQualification(String doctorQualification) {
        this.mDoctorQualification = doctorQualification;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }

    public String getArea() {
        return mArea;
    }

    public void setArea(String area) {
        this.mArea = area;
    }
}