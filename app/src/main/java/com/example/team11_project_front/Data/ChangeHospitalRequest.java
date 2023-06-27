package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class ChangeHospitalRequest {


    @SerializedName("id")
    public String inputId;

    @SerializedName("hos_name")
    public String hos_name;
    @SerializedName("address")
    public String address;
    @SerializedName("officenumber")
    public String officenumber;

    @SerializedName("introduction")
    public String introduction;


    public String getInputId() {
        return inputId;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
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

    public ChangeHospitalRequest(String inputId, String hos_name, String address, String officenumber, String introduction){


        this.inputId = inputId;
        this.hos_name = hos_name;
        this.address = address;
        this.officenumber = officenumber;
        this.introduction = introduction;
    }


}
