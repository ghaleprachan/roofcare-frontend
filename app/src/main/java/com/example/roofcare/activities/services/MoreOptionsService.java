package com.example.roofcare.activities.services;

import com.example.roofcare.models.MoreOptionsModel;

import java.util.ArrayList;

public class MoreOptionsService {
    public static ArrayList<MoreOptionsModel> options = new ArrayList<>();

    public static void addOptions(MoreOptionsModel optionsModel) {
        options.add(optionsModel);
    }
}
