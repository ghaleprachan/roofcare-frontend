package com.example.iwnews.services;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    public static String formatDate(String stringDate, String dateFormat) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date;
            try {
                date = sdf.parse(stringDate);
            } catch (Exception ex) {
                return null;
            }
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf2 = new SimpleDateFormat(dateFormat);
            assert date != null;
            return sdf2.format(date);
        } catch (Exception ex) {
            return null;
        }
    }
}
