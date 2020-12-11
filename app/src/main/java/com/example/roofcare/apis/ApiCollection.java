package com.example.roofcare.apis;

import com.example.roofcare.helper.userDetails.UserBasicDetails;

public class ApiCollection {
    public static String baseUrl = "https://192.168.1.6:45455/";

    public static String logInAuthentication = baseUrl + "api/User/LogInAuthorization";

    public static String getALlOffers = baseUrl + "api/offers";

    public static String getUserProfile = baseUrl + "api/User/GetProfileDetails/";

    public static String getUserOffers = baseUrl + "api/Offers/GetUserOffers?username=";

    public static String getUserReviews = baseUrl + "api/Review?userId=";
}
