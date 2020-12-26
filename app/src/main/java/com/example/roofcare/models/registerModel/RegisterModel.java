package com.example.roofcare.models.registerModel;

import android.graphics.Bitmap;

import java.util.Calendar;

public class RegisterModel {
    private static String username;
    private static String password;
    private static String fullName;
    private static String Gender;
    private static Calendar dob;
    private static String userType;
    private static Bitmap userImage;
    private static String aboutUser;
    private static String contact;
    private static String address;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        RegisterModel.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        RegisterModel.password = password;
    }

    public static  String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        RegisterModel.fullName = fullName;
    }

    public static String getGender() {
        return Gender;
    }

    public static void setGender(String gender) {
        Gender = gender;
    }

    public static Calendar getDob() {
        return dob;
    }

    public static void setDob(Calendar dob) {
        RegisterModel.dob = dob;
    }

    public static String getUserType() {
        return userType;
    }

    public static void setUserType(String userType) {
        RegisterModel.userType = userType;
    }

    public static Bitmap getUserImage() {
        return userImage;
    }

    public static void setUserImage(Bitmap userImage) {
        RegisterModel.userImage = userImage;
    }

    public static  String getAboutUser() {
        return aboutUser;
    }

    public static void setAboutUser(String aboutUser) {
        RegisterModel.aboutUser = aboutUser;
    }

    public static String getContact() {
        return contact;
    }

    public static  void setContact(String contact) {
        RegisterModel.contact = contact;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        RegisterModel.address = address;
    }
}
