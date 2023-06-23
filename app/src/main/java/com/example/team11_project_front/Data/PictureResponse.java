package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class PictureResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("photo")
    private String photo;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("model_result")
    private String model_result;
    @SerializedName("userid")
    private String userid;
    @SerializedName("pet_id")
    private String pet_id;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getModel_result() {
        return model_result;
    }

    public void setModel_result(String model_result) {
        this.model_result = model_result;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPet_id() {
        return pet_id;
    }

    public void setPet_id(String pet_id) {
        this.pet_id = pet_id;
    }
}

