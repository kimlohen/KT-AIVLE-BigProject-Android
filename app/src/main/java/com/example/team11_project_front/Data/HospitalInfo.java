package com.example.team11_project_front.Data;

public class HospitalInfo {
    private String id;
    private String name;
    private String address;
    private String tel;
    private String introduction;
    private String photo;

    public HospitalInfo(String id, String name, String address, String tel, String introduction,String photo) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.introduction = introduction;
        this.photo = photo;
    }

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

}
