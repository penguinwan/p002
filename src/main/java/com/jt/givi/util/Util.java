package com.jt.givi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by superman on 7/19/2015.
 */
public final class Util {
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static Date timeFromString(String dateString) throws ParseException {
        return TIME_FORMAT.parse(dateString);
    }

    public static String timeToString(Date date) {
        return TIME_FORMAT.format(date);
    }

    public static String dateToString(Date date) {
        return DATE_FORMAT.format(date);
    }
}
