package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class HospitalResponse {
    @SerializedName("id")
    private String hos_id;
    @SerializedName("hos_name")
    private String hos_name;
    @SerializedName("address")
    private String address;
    @SerializedName("officenumber")
    private String officenumber;
    @SerializedName("introduction")
    private String introduction;
    @SerializedName("hos_profile_img")
    private String hos_profile_img;
    @SerializedName("user_id")
    private String user_id;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
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
    public String getHos_profile_img() {
        return hos_profile_img;
    }
    public void setHos_profile_img(String hos_profile_img) {
        this.hos_profile_img = hos_profile_img;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

