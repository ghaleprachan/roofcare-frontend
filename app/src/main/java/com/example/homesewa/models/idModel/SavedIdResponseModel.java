
package com.example.homesewa.models.idModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavedIdResponseModel {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("saveds")
    @Expose
    private List<Saved> saveds = null;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Saved> getSaveds() {
        return saveds;
    }

    public void setSaveds(List<Saved> saveds) {
        this.saveds = saveds;
    }

}
