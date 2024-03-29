package com.example.homesewa.helper.populateMoreSection;

import com.example.homesewa.R;
import com.example.homesewa.services.moreOptionService.MoreOptionsService;
import com.example.homesewa.enumClasses.MoreOptionsId;
import com.example.homesewa.models.moreOptionModel.MoreOptionsModel;

public class PopulateMoreOptions {
    public static void addOptions() {
        MoreOptionsModel modelOne = new MoreOptionsModel(MoreOptionsId.BOOKINGS, "Bookings", R.drawable.ic_baseline_book_24);
        MoreOptionsModel modelTwo = new MoreOptionsModel(MoreOptionsId.BOOKING_REQUESTS, "Booking Requests", R.drawable.ic_baseline_post_add_24);
        MoreOptionsModel modelThree = new MoreOptionsModel(MoreOptionsId.BOOKING_HISTORY, "Booking History", R.drawable.ic_baseline_history_24);
        MoreOptionsModel modelFour = new MoreOptionsModel(MoreOptionsId.SETTINGS, "Settings", R.drawable.ic_baseline_settings_24);
        MoreOptionsModel modelFive = new MoreOptionsModel(MoreOptionsId.SAVED, "Saved", R.drawable.ic_baseline_bookmark_24);
        //MoreOptionsModel modelSeven = new MoreOptionsModel(MoreOptionsId.SWITCH_ACCOUNT, "Switch Account", R.drawable.ic_baseline_switch_left_24);
        MoreOptionsModel modelEight = new MoreOptionsModel(MoreOptionsId.SIGN_OUT, "Sign Out", R.drawable.ic_baseline_power_settings_new_24);

        MoreOptionsService.addOptions(modelOne);
        MoreOptionsService.addOptions(modelTwo);
        MoreOptionsService.addOptions(modelThree);
        MoreOptionsService.addOptions(modelFour);
        MoreOptionsService.addOptions(modelFive);
        // MoreOptionsService.addOptions(modelSeven);
        MoreOptionsService.addOptions(modelEight);
    }
}
