package com.example.roofcare.models.offerResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferResponseModel {

    @SerializedName("offerId")
    @Expose
    private Integer offerId;
    @SerializedName("validDate")
    @Expose
    private String validDate;
    @SerializedName("offerImage")
    @Expose
    private String offerImage;
    @SerializedName("postedDate")
    @Expose
    private String postedDate;
    @SerializedName("offerDescription")
    @Expose
    private String offerDescription;
    @SerializedName("addedByName")
    @Expose
    private String addedByName;
    @SerializedName("addedByUsername")
    @Expose
    private String addedByUsername;
    @SerializedName("addedByContact")
    @Expose
    private String addedByContact;
    @SerializedName("addedByImage")
    @Expose
    private String addedByImage;

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
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

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getAddedByName() {
        return addedByName;
    }

    public void setAddedByName(String addedByName) {
        this.addedByName = addedByName;
    }

    public String getAddedByUsername() {
        return addedByUsername;
    }

    public void setAddedByUsername(String addedByUsername) {
        this.addedByUsername = addedByUsername;
    }

    public String getAddedByContact() {
        return addedByContact;
    }

    public void setAddedByContact(String addedByContact) {
        this.addedByContact = addedByContact;
    }

    public String getAddedByImage() {
        return addedByImage;
    }

    public void setAddedByImage(String addedByImage) {
        this.addedByImage = addedByImage;
    }

}