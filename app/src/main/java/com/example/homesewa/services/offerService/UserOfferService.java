package com.example.homesewa.services.offerService;

import com.example.homesewa.models.offerResponseModel.OfferResponseModel;

import java.util.ArrayList;
import java.util.List;

public class UserOfferService {
    public static ArrayList<OfferResponseModel> offers = new ArrayList<>();

    public static void addOffers(List<OfferResponseModel> getOffers) {
        if (offers.size() != 0) {
            offers.clear();
        }
        offers.addAll(getOffers);
    }
}
