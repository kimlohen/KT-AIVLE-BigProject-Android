package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class LogoutResponse {

    @SerializedName("detail")
    private String detail;

    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
}

