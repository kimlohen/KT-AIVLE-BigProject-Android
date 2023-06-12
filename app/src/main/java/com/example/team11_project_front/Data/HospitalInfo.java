package com.example.team11_project_front.Data;

public class HospitalInfo {
    private String name;
    private String location;
    private String part;
    private String introduction;
    public HospitalInfo(String name, String location, String part, String introduction) {
        this.name = name;
        this.location =location;
        this.part = part;
        this.introduction = introduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
