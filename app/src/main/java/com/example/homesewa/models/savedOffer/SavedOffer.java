
package com.example.homesewa.models.savedOffer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavedOffer {
    @SerializedName("savedOfferId")
    @Expose
    private Integer savedOfferId;
    @SerializedName("offerId")
    @Expose
    private Integer offerId;
    @SerializedName("offerDescription")
    @Expose
    private String offerDescription;
    @SerializedName("offerImage")
    @Expose
    private String offerImage;
    @SerializedName("postedDate")
    @Expose
    private String postedDate;
    @SerializedName("validDate")
    @Expose
    private String validDate;
    @SerializedName("offerUserId")
    @Expose
    private Integer offerUserId;
    @SerializedName("offerUsername")
    @Expose
    private String offerUsername;
    @SerializedName("offerFullName")
    @Expose
    private String offerFullName;
    @SerializedName("offerPhoneNum")
    @Expose
    private String offerPhoneNum;
    @SerializedName("offerUserImage")
    @Expose
    private String offerUserImage;

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

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public Integer getOfferUserId() {
        return offerUserId;
    }

    public void setOfferUserId(Integer offerUserId) {
        this.offerUserId = offerUserId;
    }

    public String getOfferUsername() {
        return offerUsername;
    }

    public void setOfferUsername(String offerUsername) {
        this.offerUsername = offerUsername;
    }

    public String getOfferFullName() {
        return offerFullName;
    }

    public void setOfferFullName(String offerFullName) {
        this.offerFullName = offerFullName;
    }

    public String getOfferPhoneNum() {
        return offerPhoneNum;
    }

    public void setOfferPhoneNum(String offerPhoneNum) {
        this.offerPhoneNum = offerPhoneNum;
    }

    public String getOfferUserImage() {
        return offerUserImage;
    }

    public void setOfferUserImage(String offerUserImage) {
        this.offerUserImage = offerUserImage;
    }

}
