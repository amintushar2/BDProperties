package com.example.bdproperties.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProperyDetailsDataSets {


    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("OwnerId")
    @Expose
    private Integer ownerId;
    @SerializedName("PropertyName")
    @Expose
    private String propertyName;
    @SerializedName("Area")
    @Expose
    private Integer area;
    @SerializedName("AreaName")
    @Expose
    private String areaName;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("SalePrice")
    @Expose
    private Integer salePrice;
    @SerializedName("SubPropertyType")
    @Expose
    private Integer subPropertyType;
    @SerializedName("SubTypeName")
    @Expose
    private String subTypeName;
    @SerializedName("TypeId")
    @Expose
    private Integer typeId;
    @SerializedName("TypeName")
    @Expose
    private String typeName;
    @SerializedName("BedRoom")
    @Expose
    private Integer bedRoom;
    @SerializedName("WashRoom")
    @Expose
    private Integer washRoom;
    @SerializedName("Varanda")
    @Expose
    private Integer varanda;
    @SerializedName("DrawingRoom")
    @Expose
    private Boolean drawingRoom;
    @SerializedName("Parking")
    @Expose
    private Boolean parking;
    @SerializedName("FlatSize")
    @Expose
    private Integer flatSize;
    @SerializedName("BulidingYear")
    @Expose
    private Integer bulidingYear;
    @SerializedName("FloorLevel")
    @Expose
    private Integer floorLevel;
    @SerializedName("Preference")
    @Expose
    private String preference;
    @SerializedName("PublishDate")
    @Expose
    private String publishDate;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("StatusName")
    @Expose
    private String statusName;
    @SerializedName("CoverUrl")
    @Expose
    private String coverUrl;
    @SerializedName("Purpose")
    @Expose
    private Integer purpose;
    @SerializedName("OwnerName")
    @Expose
    private String ownerName;
    @SerializedName("OwnerEmail")
    @Expose
    private String ownerEmail;
    @SerializedName("OwnerGerder")
    @Expose
    private String ownerGerder;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("NID")
    @Expose
    private String nID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getSubPropertyType() {
        return subPropertyType;
    }

    public void setSubPropertyType(Integer subPropertyType) {
        this.subPropertyType = subPropertyType;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getBedRoom() {
        return bedRoom;
    }

    public void setBedRoom(Integer bedRoom) {
        this.bedRoom = bedRoom;
    }

    public Integer getWashRoom() {
        return washRoom;
    }

    public void setWashRoom(Integer washRoom) {
        this.washRoom = washRoom;
    }

    public Integer getVaranda() {
        return varanda;
    }

    public void setVaranda(Integer varanda) {
        this.varanda = varanda;
    }

    public Boolean getDrawingRoom() {
        return drawingRoom;
    }

    public void setDrawingRoom(Boolean drawingRoom) {
        this.drawingRoom = drawingRoom;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public Integer getFlatSize() {
        return flatSize;
    }

    public void setFlatSize(Integer flatSize) {
        this.flatSize = flatSize;
    }

    public Integer getBulidingYear() {
        return bulidingYear;
    }

    public void setBulidingYear(Integer bulidingYear) {
        this.bulidingYear = bulidingYear;
    }

    public Integer getFloorLevel() {
        return floorLevel;
    }

    public void setFloorLevel(Integer floorLevel) {
        this.floorLevel = floorLevel;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Integer getPurpose() {
        return purpose;
    }

    public void setPurpose(Integer purpose) {
        this.purpose = purpose;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerGerder() {
        return ownerGerder;
    }

    public void setOwnerGerder(String ownerGerder) {
        this.ownerGerder = ownerGerder;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNID() {
        return nID;
    }

    public void setNID(String nID) {
        this.nID = nID;
    }
}
