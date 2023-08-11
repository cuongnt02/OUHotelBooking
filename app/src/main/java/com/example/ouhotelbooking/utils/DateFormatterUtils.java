package com.example.ouhotelbooking.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatterUtils {
    private static SimpleDateFormat simpleDateFormat;

    static
    {
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault());
    }

    public static String formatDate(Date date) {
        return simpleDateFormat.format(date);
    }

    public static Date getDate(String dateText) {
        try {
            Date parsedDate = simpleDateFormat.parse(dateText);
            return parsedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
