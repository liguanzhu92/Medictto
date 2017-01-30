package com.svs.myprojects.mymedicalrecords.patientrecord.makereservation;

/**
 * Created by snehalsutar on 3/3/16.
 */
public
class DoctorDetail {
    String doctorName;
    String doctorSpeciality;
    String doctorQualification;
    String imageUrl;
    String operationHours;
    String address;
    String phoneNum;
    String emailId;
    String rating;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public DoctorDetail(String doctorName, String doctorSpeciality, String doctorQualification,
                        String imageUrl, String operationHours, String address, String phoneNum,
                        String emailId, String rating) {
        this.doctorName = doctorName;
        this.doctorSpeciality = doctorSpeciality;
        this.doctorQualification = doctorQualification;
        this.imageUrl = imageUrl;
        this.operationHours = operationHours;
        this.address = address;
        this.phoneNum = phoneNum;
        this.emailId = emailId;
        this.rating = rating;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSpeciality() {
        return doctorSpeciality;
    }

    public void setDoctorSpeciality(String doctorSpeciality) {
        this.doctorSpeciality = doctorSpeciality;
    }

    public String getDoctorQualification() {
        return doctorQualification;
    }

    public void setDoctorQualification(String doctorQualification) {
        this.doctorQualification = doctorQualification;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOperationHours() {
        return operationHours;
    }

    public void setOperationHours(String operationHours) {
        this.operationHours = operationHours;
    }
}