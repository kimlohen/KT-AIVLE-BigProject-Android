package com.example.team11_project_front.Data;

public class PostedList {
    private String title;
    private String pet;
    private String date;

    public PostedList(String title, String pet, String date) {
        this.title = title;
        this.pet = pet;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
