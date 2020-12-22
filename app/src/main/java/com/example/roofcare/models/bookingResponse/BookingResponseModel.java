
package com.example.roofcare.models.bookingResponse;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingResponseModel {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("userImage")
    @Expose
    private String userImage;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("iBooked")
    @Expose
    private List<IBooked> iBooked = null;
    @SerializedName("imBooked")
    @Expose
    private List<ImBooked> imBooked = null;

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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<IBooked> getIBooked() {
        return iBooked;
    }

    public void setIBooked(List<IBooked> iBooked) {
        this.iBooked = iBooked;
    }

    public List<ImBooked> getImBooked() {
        return imBooked;
    }

    public void setImBooked(List<ImBooked> imBooked) {
        this.imBooked = imBooked;
    }

}
