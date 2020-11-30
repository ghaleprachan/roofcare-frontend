package com.example.roofcare.services.moreOptionService;

import com.example.roofcare.models.moreOptionModel.MoreOptionsModel;

import java.util.ArrayList;

public class MoreOptionsService {
    public static ArrayList<MoreOptionsModel> options = new ArrayList<>();

    public static void addOptions(MoreOptionsModel optionsModel) {
        options.add(optionsModel);
    }
}
