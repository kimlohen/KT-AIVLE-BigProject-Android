package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("result")
    private String resultCode;

    @SerializedName("access")
    private String accessToken;

    @SerializedName("refresh")
    private String refreshToken;

    @SerializedName("user")
    private User user;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getAcessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public User getUser() { return user; }

    public void setAcessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
    }
    public void setUser(User user) { this.user = user; }
}

