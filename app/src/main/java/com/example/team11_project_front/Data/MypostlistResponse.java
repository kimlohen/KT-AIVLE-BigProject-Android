package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class MypostlistResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("contents")
    private String contents;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("userid")
    private String userid;
    @SerializedName("pictureid")
    private String pictureid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPictureid() {
        return pictureid;
    }

    public void setPictureid(String pictureid) {
        this.pictureid = pictureid;
    }
}
