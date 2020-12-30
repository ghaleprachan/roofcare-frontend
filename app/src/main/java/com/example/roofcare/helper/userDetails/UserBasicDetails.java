package com.example.roofcare.helper.userDetails;

import android.content.Context;
import android.content.SharedPreferences;

public class UserBasicDetails {
    public static String getUserName(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("LOGIN_DETAILS", 0);
        return preferences.getString("Username", null);
    }

    public static Integer getId(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("LOGIN_DETAILS", 0);
        return preferences.getInt("UserId", 0);
    }

    public static String getUserType(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("LOGIN_DETAILS", 0);
        return preferences.getString("UserType", null);
    }
}
