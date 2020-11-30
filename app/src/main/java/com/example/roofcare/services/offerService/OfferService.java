package com.example.roofcare.services.offerService;

import com.example.roofcare.models.offerResponseModel.OfferResponseModel;

import java.util.ArrayList;
import java.util.List;

public class OfferService {
    public static ArrayList<OfferResponseModel> offers = new ArrayList<>();

    public static void addOffers(List<OfferResponseModel> getOffers) {
        if (offers.size() != 0) {
            offers.clear();
        }
        offers.addAll(getOffers);
    }
}
