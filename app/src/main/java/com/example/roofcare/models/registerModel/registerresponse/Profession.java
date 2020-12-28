
package com.example.roofcare.models.registerModel.registerresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profession {

    @SerializedName("professionId")
    @Expose
    private Integer professionId;
    @SerializedName("professionName")
    @Expose
    private String professionName;
    @SerializedName("userProfessions")
    @Expose
    private Object userProfessions;

    public Integer getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Integer professionId) {
        this.professionId = professionId;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public Object getUserProfessions() {
        return userProfessions;
    }

    public void setUserProfessions(Object userProfessions) {
        this.userProfessions = userProfessions;
    }

}
