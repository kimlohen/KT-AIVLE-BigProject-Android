package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class JoinRequest {

    @SerializedName("first_name")
    private String inputName;
    @SerializedName("email")
    private String inputId;
    @SerializedName("password1")
    private String inputPw;
    @SerializedName("password2")
    private String inputPw2;
    @SerializedName("is_vet")
    private String is_vet;

    public String getInputName() { return inputName; }
    public String getInputId() {
        return inputId;
    }

    public String getInputPw() {
        return inputPw;
    }
    public String getInputPw2() {
        return inputPw2;
    }

    public void setInputName(String inputName) { this.inputName = inputName; }
    public void setInputId(String inputId) {
        this.inputId = inputId;
    }
    public void setInputPw(String inputPw) {
        this.inputPw = inputPw;
    }
    public void setInputPw2(String inputPw2) {
        this.inputPw2 = inputPw2;
    }
    public JoinRequest(String inputName, String inputId, String inputPw, String inputPw2, String is_vet) {
        this.inputName = inputName;
        this.inputId = inputId;
        this.inputPw = inputPw;
        this.inputPw2 = inputPw2;
        this.is_vet = is_vet;
    }
}