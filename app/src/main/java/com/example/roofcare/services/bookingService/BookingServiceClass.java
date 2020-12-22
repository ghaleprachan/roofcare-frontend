package com.example.roofcare.services.bookingService;

import com.example.roofcare.models.bookingResponse.BookingResponseModel;

public class BookingServiceClass {
    public static BookingResponseModel responseModel;

    public static boolean addBookingResponse(BookingResponseModel responseModel) {
        BookingServiceClass.responseModel = responseModel;
        return true;
    }
}
