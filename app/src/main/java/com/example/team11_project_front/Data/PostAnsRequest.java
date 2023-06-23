package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class PostAnsRequest {
    @SerializedName("contents")
    public String contents;

    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }
}