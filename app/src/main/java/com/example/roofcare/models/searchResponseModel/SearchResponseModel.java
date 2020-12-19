package com.example.roofcare.models.searchResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResponseModel {

    @SerializedName("professionId")
    @Expose
    private Integer professionId;
    @SerializedName("professionName")
    @Expose
    private String professionName;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("verified")
    @Expose
    private Boolean verified;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("userImage")
    @Expose
    private String userImage;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

}