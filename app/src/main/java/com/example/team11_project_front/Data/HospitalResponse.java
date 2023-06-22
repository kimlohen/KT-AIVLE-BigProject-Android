package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class HospitalResponse {
    @SerializedName("id")
    private String hos_id;
    @SerializedName("hos_name")
    private String hos_name;
    @SerializedName("addresss")
    private String addresss;
    @SerializedName("officenumber")
    private String officenumber;
    @SerializedName("introduction")
    private String introduction;
    @SerializedName("host_profile_img")
    private String host_profile_img;

    public String getHos_id() {
        return hos_id;
    }
    public void setHos_id(String hos_id) {
        this.hos_id = hos_id;
    }
    public String getHos_name() {
        return hos_name;
    }
    public void setHos_name(String hos_name) {
        this.hos_name = hos_name;
    }
    public String getAddresss() {
        return addresss;
    }
    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }
    public String getOfficenumber() {
        return officenumber;
    }
    public void setOfficenumber(String officenumber) {
        this.officenumber = officenumber;
    }
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String getHost_profile_img() {
        return host_profile_img;
    }
    public void setHost_profile_img(String host_profile_img) {
        this.host_profile_img = host_profile_img;
    }
}

