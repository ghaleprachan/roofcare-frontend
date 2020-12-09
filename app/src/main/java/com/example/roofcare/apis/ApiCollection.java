package com.example.roofcare.apis;

public class ApiCollection {
    public static String baseUrl = "https://roofcare-apis.conveyor.cloud/";

    public static String logInAuthentication = baseUrl + "api/User/LogInAuthorization";

    public static String getALlOffers = baseUrl + "api/offers";

    public static String getUserProfile = baseUrl + "api/User/GetProfileDetails/";

    public static String getUserOffers = baseUrl + "api/Offers/GetUserOffers?username=";
}
