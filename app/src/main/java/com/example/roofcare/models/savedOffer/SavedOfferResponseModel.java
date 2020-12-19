
package com.example.roofcare.models.savedOffer;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavedOfferResponseModel {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("savedOffer")
    @Expose
    private List<SavedOffer> savedOffer = null;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<SavedOffer> getSavedOffer() {
        return savedOffer;
    }

    public void setSavedOffer(List<SavedOffer> savedOffer) {
        this.savedOffer = savedOffer;
    }

}
