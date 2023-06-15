package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class EmailResponse {
    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;
    @SerializedName("detail")
    private String detail;

    public String getSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
    public String getDetail() {
        return detail;
    }
    public void setSuccess(String success) {
        this.success = success;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
}

