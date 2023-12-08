package com.mjzhud.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
    public static String nowDateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }
}
