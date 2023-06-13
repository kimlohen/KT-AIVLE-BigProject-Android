package com.example.team11_project_front.Data;

public class Profile {
    private String image;
    private String name;
    private String type;
    private String email;

    public Profile(String name, String type, String email) {
        this.name = name;
        this.type = type;
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
