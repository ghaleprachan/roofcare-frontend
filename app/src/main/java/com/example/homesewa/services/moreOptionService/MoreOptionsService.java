package com.example.homesewa.services.moreOptionService;

import com.example.homesewa.models.moreOptionModel.MoreOptionsModel;

import java.util.ArrayList;

public class MoreOptionsService {
    public static ArrayList<MoreOptionsModel> options = new ArrayList<>();

    public static void addOptions(MoreOptionsModel optionsModel) {
        options.add(optionsModel);
    }
}
