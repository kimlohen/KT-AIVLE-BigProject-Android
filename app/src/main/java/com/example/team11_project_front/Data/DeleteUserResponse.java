package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class DeleteUserResponse {
    @SerializedName("result")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

