package com.example.roofcare.apis;


public class ApiCollection {
    public static String baseUrl = "http://bb792e91d82e.ngrok.io/";

    public static String logInAuthentication = baseUrl + "api/User/LogInAuthorization";

    public static String getALlOffers = baseUrl + "api/offers";

    public static String getUserProfile = baseUrl + "api/User/GetProfileDetails/";

    public static String getUserOffers = baseUrl + "api/Offers/GetUserOffers?username=";

    public static String getUserReviews = baseUrl + "api/Review?userId=";

    public static String getSearchResult = baseUrl + "api/Search?searchItem=";

    public static String postFeedback = baseUrl + "api/Review";

    public static String deleteOffer = baseUrl + "api/offers?offerId=";

    public static String saveOffer = baseUrl + "api/UserSaved";

    public static String getSavedOfferId = baseUrl + "GetId?userId=";

    public static String getSavedOffers = baseUrl + "api/UserSaved?userId=";

    public static String removeSaved = baseUrl + "api/UserSaved?savedId=";

    public static String sendBookingRequest = baseUrl + "api/BookingRequest";

    public static String getBookingAndRequests = baseUrl + "api/Booking";

    public static String deleteBooking = baseUrl + "api/Booking?bookingId=";

    public static String markBookingCompleted = baseUrl + "api/Booking?bookingId=";

    public static String getBookingHistory = baseUrl + "api/Booking/GetBookingHistory?userId=";

    public static String registerUser = baseUrl + "api/Register";

    public static String addUserProfession = baseUrl + "api/Register/AddProfession?";

    public static String addProfileImage = baseUrl + "api/Register/AddProfileImage";
}
