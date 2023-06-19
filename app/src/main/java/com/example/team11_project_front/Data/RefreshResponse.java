package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class RefreshResponse {
    @SerializedName("access_expiration")
    private String access_expiration;
    @SerializedName("access")
    private String accessToken;

    public String getAccess_expiration() {
        return access_expiration;
    }

    public void setAccess_expiration(String access_expiration) {
        this.access_expiration = access_expiration;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

