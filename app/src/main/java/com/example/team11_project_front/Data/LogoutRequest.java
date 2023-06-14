package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class LogoutRequest {

    @SerializedName("email")
    public String inputId;

    @SerializedName("password")
    public String inputPw;

    public String getInputId() {
        return inputId;
    }

    public String getInputPw() {
        return inputPw;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    public void setInputPw(String inputPw) {
        this.inputPw = inputPw;
    }

    public LogoutRequest(String inputId, String inputPw) {
        this.inputId = inputId;
        this.inputPw = inputPw;
    }
}