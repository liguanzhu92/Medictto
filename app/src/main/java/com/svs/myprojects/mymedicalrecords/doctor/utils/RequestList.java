package com.svs.myprojects.mymedicalrecords.doctor.utils;

import java.util.ArrayList;

/**
 * Created by Guanzhu Li on 2/7/2017.
 */
public class RequestList extends ArrayList<AppointmentValues>{
    private static RequestList ourInstance = null;

    public static RequestList getInstance() {
        if (ourInstance == null) {
            ourInstance = new RequestList();
        }
        return ourInstance;
    }

    private RequestList() {
    }
}
