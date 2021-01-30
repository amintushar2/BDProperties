package com.example.bdproperties.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyImages {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("PropertyId")
    @Expose
    private Integer propertyId;
    @SerializedName("FilePath")
    @Expose
    private String filePath;
    @SerializedName("Details")
    @Expose
    private String details;

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
