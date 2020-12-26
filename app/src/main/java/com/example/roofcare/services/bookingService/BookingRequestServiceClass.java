package com.example.roofcare.services.bookingService;

import com.example.roofcare.models.bookingResponse.BookingResponseModel;

import java.util.ArrayList;

public class BookingRequestServiceClass {
    public static ArrayList<BookingResponseModel> responseModel = new ArrayList<>(1);

    public static boolean addBookingResponse(BookingResponseModel responseModel) {
        BookingRequestServiceClass.responseModel.clear();
        BookingRequestServiceClass.responseModel.add(responseModel);
        return true;
    }
}
