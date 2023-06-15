package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class AddPetResponse {
    @SerializedName("result")
    public String resultCode;

    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}

