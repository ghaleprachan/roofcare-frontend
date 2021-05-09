
package com.example.homesewa.models.bookingResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IBooked {

    @SerializedName("bookingId")
    @Expose
    private Integer bookingId;
    @SerializedName("bookingById")
    @Expose
    private Integer bookingById;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("userImage")
    @Expose
    private String userImage;
    @SerializedName("serviceType")
    @Expose
    private String serviceType;
    @SerializedName("bookingDate")
    @Expose
    private String bookingDate;
    @SerializedName("serviceDate")
    @Expose
    private String serviceDate;
    @SerializedName("customerAddress")
    @Expose
    private String customerAddress;
    @SerializedName("problemDescription")
    @Expose
    private String problemDescription;
    @SerializedName("serviceCharge")
    @Expose
    private Double serviceCharge;
    @SerializedName("travellingCost")
    @Expose
    private Double travellingCost;
    @SerializedName("discountPercentage")
    @Expose
    private Double discountPercentage;
    @SerializedName("totalCharge")
    @Expose
    private Double totalCharge;
    @SerializedName("issuedDate")
    @Expose
    private String issuedDate;
    @SerializedName("vendorAcceptance")
    @Expose
    private Boolean vendorAcceptance;
    @SerializedName("completedStatus")
    @Expose
    private Boolean completedStatus;

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getBookingById() {
        return bookingById;
    }

    public void setBookingById(Integer bookingById) {
        this.bookingById = bookingById;
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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Double getTravellingCost() {
        return travellingCost;
    }

    public void setTravellingCost(Double travellingCost) {
        this.travellingCost = travellingCost;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(Double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Boolean getVendorAcceptance() {
        return vendorAcceptance;
    }

    public void setVendorAcceptance(Boolean vendorAcceptance) {
        this.vendorAcceptance = vendorAcceptance;
    }

    public Boolean getCompletedStatus() {
        return completedStatus;
    }

    public void setCompletedStatus(Boolean completedStatus) {
        this.completedStatus = completedStatus;
    }
}
