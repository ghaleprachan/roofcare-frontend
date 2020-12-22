package com.example.roofcare.services.bookingService;

import com.example.roofcare.models.bookingResponse.BookingResponseModel;

public class BookingRequestServiceClass {
    public static BookingResponseModel responseModel;

    public static boolean addBookingResponse(BookingResponseModel responseModel) {
        BookingRequestServiceClass.responseModel = responseModel;
        return true;
    }
}
