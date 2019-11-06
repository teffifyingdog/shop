package com.wjc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static Date getNextTime(int next) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.add(Calendar.HOUR,next);

        return calendar.getTime();
    }

    public static Date getNow() {
        return getNextTime(0);
    }

    public static String dateScore(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MMddHH");
        return format.format(date);
    }
}
