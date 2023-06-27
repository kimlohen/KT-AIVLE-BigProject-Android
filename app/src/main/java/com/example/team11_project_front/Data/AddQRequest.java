package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class AddQRequest {

    @SerializedName("title")
    public String title;

    @SerializedName("contents")
    public String contents;

    @SerializedName("pictureid")
    public String picture_id;

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

    public String getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(String picture_id) {
        this.picture_id = picture_id;
    }

    public AddQRequest(String title, String contents, String picture_id)
    {
        this.title = title;
        this.contents = contents;
        this.picture_id = picture_id;

    }


}
