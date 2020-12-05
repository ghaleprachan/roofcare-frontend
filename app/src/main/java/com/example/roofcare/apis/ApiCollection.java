package com.example.roofcare.apis;

public class ApiCollection {
    public static String baseUrl = "https://192.168.1.7:45456/";

    public static String logInAuthentication = baseUrl + "api/User/LogInAuthorization";

    public static String getALlOffers = baseUrl + "api/offers";

    public static String getUserProfile = baseUrl + "api/User/GetProfileDetails/";
}
