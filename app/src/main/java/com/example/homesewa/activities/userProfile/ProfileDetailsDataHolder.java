package com.example.homesewa.activities.userProfile;

import com.example.homesewa.models.profileResponse.ProfileResponseModel;

import java.util.ArrayList;

public class ProfileDetailsDataHolder {
    public static ArrayList<ProfileResponseModel> profileDetails = new ArrayList<>();

    public static boolean addData(ProfileResponseModel responseModel) {
        profileDetails.add(0,responseModel);
        return true;
    }
}
