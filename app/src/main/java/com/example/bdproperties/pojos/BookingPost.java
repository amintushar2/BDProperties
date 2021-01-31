package com.example.bdproperties.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingPost {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("PropertyId")
    @Expose
    private Integer propertyId;
    @SerializedName("OwnerId")
    @Expose
    private Integer ownerId;
    @SerializedName("Time")
    @Expose
    private String time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
