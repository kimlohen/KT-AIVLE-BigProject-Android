package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class RefreshRequest {

    @SerializedName("refresh")
    public String refresh;
    public RefreshRequest(String refresh) {
        this.refresh = refresh;
    }
}