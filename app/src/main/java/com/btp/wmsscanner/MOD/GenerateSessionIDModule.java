package com.btp.wmsscanner.MOD;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class GenerateSessionIDModule {
    //-----------------------------------------
    //Generate formatted session ID
    //-----------------------------------------
    public static String getCurrentSession() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmmss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }
}
