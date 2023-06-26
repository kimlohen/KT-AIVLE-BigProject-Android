package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class PicturePostRequest {
    @SerializedName("pet_id")
    private String pet_id;

    public String getPet_id() {
        return pet_id;
    }

    public void setPet_id(String pet_id) {
        this.pet_id = pet_id;
    }

    public PicturePostRequest(String pet_id){
        this.pet_id = pet_id;
    }
}