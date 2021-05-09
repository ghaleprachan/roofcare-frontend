package com.example.homesewa.services.savedIdService;

import com.example.homesewa.models.idModel.Saved;

import java.util.ArrayList;
import java.util.List;

public class SavedIsListService {
    public static final ArrayList<Saved> models = new ArrayList<>();

    public static void addAll(List<Saved> saves) {
        models.clear();
        models.addAll(saves);
    }

    public static boolean isSaved(Integer userId) {
        boolean isSaved = false;
        for (int i = 0; i < models.size(); i++) {
            if (models.get(i).getOfferId().equals(userId)) {
                isSaved = true;
            }
        }
        return isSaved;
    }
}
