package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class HospitallistResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("hos_name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("officenumber")
    private String tel;
    @SerializedName("introduction")
    private String introduction;
    @SerializedName("hos_profile_img")
    private String photo;





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


}
