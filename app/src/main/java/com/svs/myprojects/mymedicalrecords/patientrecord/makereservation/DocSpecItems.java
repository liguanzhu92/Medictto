package com.svs.myprojects.mymedicalrecords.patientrecord.makereservation;

/**
 * Created by snehalsutar on 3/1/16.
 */
public class DocSpecItems {
    String imageUrl;
    String specName;

    public DocSpecItems(String imageUrl, String specName) {
        this.imageUrl = imageUrl;
        this.specName = specName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }
}