
package com.example.homesewa.models.idModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Saved {
    @SerializedName("savedOfferId")
    @Expose
    private Integer savedOfferId;
    @SerializedName("offerId")
    @Expose
    private Integer offerId;

    public Integer getSavedOfferId() {
        return savedOfferId;
    }

    public void setSavedOfferId(Integer savedOfferId) {
        this.savedOfferId = savedOfferId;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }
}
