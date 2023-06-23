package com.example.team11_project_front.Data;

public class HospitalInfo {
    private String id;
    private String name;
    private String address;
    private String tel;
    private String introduction;
    private String hos_profile_img;
    public HospitalInfo(String name, String location, String part, String introduction, String hos_profile_img) {
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.introduction = introduction;
        this.hos_profile_img = hos_profile_img;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String photo) {
        this.photo = photo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }



    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getHos_profile_img() {
        return hos_profile_img;
    }
    public void setHos_profile_img(String hos_profile_img) {
        this.hos_profile_img = hos_profile_img;
    }
}
