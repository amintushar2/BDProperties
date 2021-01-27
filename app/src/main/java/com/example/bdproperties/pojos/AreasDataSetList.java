package com.example.bdproperties.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AreasDataSetList {


    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Dis")
    @Expose
    private String dis;
    @SerializedName("Zone")
    @Expose
    private String zone;
    @SerializedName("Area1")
    @Expose
    private String area1;
    @SerializedName("Code")
    @Expose
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getArea1() {
        return area1;
    }

    public void setArea1(String area1) {
        this.area1 = area1;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
