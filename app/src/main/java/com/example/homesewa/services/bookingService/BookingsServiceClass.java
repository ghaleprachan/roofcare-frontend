package com.example.homesewa.services.bookingService;

import com.example.homesewa.models.bookingResponse.BookingResponseModel;

public class BookingsServiceClass {
    public static BookingResponseModel responseModel;

    public static boolean addBookingResponse(BookingResponseModel responseModel) {
        BookingsServiceClass.responseModel = responseModel;
        return true;
    }
}
