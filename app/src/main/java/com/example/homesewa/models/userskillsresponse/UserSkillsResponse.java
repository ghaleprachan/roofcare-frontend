package com.example.homesewa.models.userskillsresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSkillsResponse {

    @SerializedName("userProfessionId")
    @Expose
    private Integer userProfessionId;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("professionId")
    @Expose
    private Integer professionId;
    @SerializedName("professionName")
    @Expose
    private String professionName;

    public Integer getUserProfessionId() {
        return userProfessionId;
    }

    public void setUserProfessionId(Integer userProfessionId) {
        this.userProfessionId = userProfessionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

}