package com.example.team11_project_front;

import com.google.gson.annotations.SerializedName;

public class EmailRequest {
    @SerializedName("email")
    public String inputMail;

    public String getInputMail() {
        return inputMail;
    }

    public void setInputMail(String inputMail) {
        this.inputMail = inputMail;
    }

    public EmailRequest(String inputMail) {
        this.inputMail = inputMail;
    }
}