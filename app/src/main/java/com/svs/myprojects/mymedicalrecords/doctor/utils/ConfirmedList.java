package com.svs.myprojects.mymedicalrecords.doctor.utils;

import java.util.ArrayList;

/**
 * Created by Guanzhu Li on 2/7/2017.
 */
public class ConfirmedList extends ArrayList<AppointmentValues> {
    private static ConfirmedList ourInstance = null;

    public static ConfirmedList getInstance() {
        if (ourInstance == null) {
            ourInstance = new ConfirmedList();
        }
        return ourInstance;
    }

    private ConfirmedList() {
    }
}
