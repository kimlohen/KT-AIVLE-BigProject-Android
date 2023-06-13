package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("result")
    public String resultCode;

    @SerializedName("access")
    public String accessToken;

    @SerializedName("refresh")
    public String refreshToken;

    @SerializedName("email")
    public String email;
    @SerializedName("first_name")
    public String first_name;
    @SerializedName("last_name")
    public String last_name;

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


    public String getEmail() {
        return email;
    }
    public String getFirst_name() {
        return first_name;
    }
    public String getLast_name() {
        return last_name;
    }


    public void setAcessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setFirst_name(String first_name){
        this.first_name = first_name;
    }
    public void setLast_name(String last_name){
        this.last_name = last_name;
    }


}

