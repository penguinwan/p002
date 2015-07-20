package com.jt.givi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by superman on 7/19/2015.
 */
public final class Util {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("hh:mm:ss");

    public static Date dateFromString(String dateString) throws ParseException {
        return DATE_FORMAT.parse(dateString);
    }

    public static String dateToString(Date date) {
        return DATE_FORMAT.format(date);
    }
}
