package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class Answer_set
{
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}