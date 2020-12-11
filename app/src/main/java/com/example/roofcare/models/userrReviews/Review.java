
package com.example.roofcare.models.userrReviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("feedbackId")
    @Expose
    private Integer feedbackId;
    @SerializedName("feedbackDate")
    @Expose
    private String feedbackDate;
    @SerializedName("feedbackText")
    @Expose
    private String feedbackText;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("byId")
    @Expose
    private Integer byId;
    @SerializedName("byUserName")
    @Expose
    private String byUserName;
    @SerializedName("byFullName")
    @Expose
    private String byFullName;
    @SerializedName("byImage")
    @Expose
    private String byImage;

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getById() {
        return byId;
    }

    public void setById(Integer byId) {
        this.byId = byId;
    }

    public String getByUserName() {
        return byUserName;
    }

    public void setByUserName(String byUserName) {
        this.byUserName = byUserName;
    }

    public String getByFullName() {
        return byFullName;
    }

    public void setByFullName(String byFullName) {
        this.byFullName = byFullName;
    }

    public String getByImage() {
        return byImage;
    }

    public void setByImage(String byImage) {
        this.byImage = byImage;
    }

}
