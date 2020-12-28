package com.example.roofcare.services.registerprofessions;

import com.example.roofcare.models.registerModel.registerresponse.RegisterResponse;

import java.util.ArrayList;

public class ProfessionsService {
    public static final ArrayList<RegisterResponse> professions = new ArrayList<>(1);

    public static void addProfessions(RegisterResponse professionList) {
        professions.clear();
        professions.add(professionList);
    }

    public static ArrayList<String> getProfessions() {
        ArrayList<String> professionNames = new ArrayList<>();
        for (int i = 0; i < professions.get(0).getProfessions().size(); i++) {
            professionNames.add(professions.get(0).getProfessions().get(i).getProfessionName());
        }
        return professionNames;
    }

    public static Integer getProfessionId(String professionName) {
        Integer professionId = -1;
        for (int i = 0; i < professions.get(0).getProfessions().size(); i++) {
            if (professions.get(0).getProfessions().get(i).getProfessionName().equalsIgnoreCase(professionName)) {
                professionId = professions.get(0).getProfessions().get(i).getProfessionId();
            }
        }
        return professionId;
    }
}
