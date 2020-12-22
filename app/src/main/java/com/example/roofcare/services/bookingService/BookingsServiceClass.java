package com.example.roofcare.services.bookingService;

import com.example.roofcare.models.bookingResponse.BookingResponseModel;

public class BookingsServiceClass {
    public static BookingResponseModel responseModel;

    public static boolean addBookingResponse(BookingResponseModel responseModel) {
        BookingsServiceClass.responseModel = responseModel;
        return true;
    }
}
