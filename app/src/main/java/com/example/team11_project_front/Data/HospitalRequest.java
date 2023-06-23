package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class HospitalRequest {
    @SerializedName("hos_name")
    private String hos_name;
    @SerializedName("address")
    private String address;
    @SerializedName("officenumber")
    private String officenumber;
    @SerializedName("introduction")
    private String introduction;


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

    public HospitalRequest(String hos_name, String address, String officenumber, String introduction){
        this.hos_name = hos_name;
        this.address = address;
        this.officenumber = officenumber;
        this.introduction = introduction;
    }

}