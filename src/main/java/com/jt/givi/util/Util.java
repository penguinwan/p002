package com.jt.givi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by superman on 7/19/2015.
 */
public final class Util {
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static final String timeToString(Date date) {
        return TIME_FORMAT.format(date);
    }

    public static Date dateTimeFromString(String dateString) throws ParseException {
        return DATE_TIME_FORMAT.parse(dateString);
    }

    public static String dateTimeToString(Date date) {
        return DATE_TIME_FORMAT.format(date);
    }

    public static String dateToString(Date date) {
        return DATE_FORMAT.format(date);
    }
}
