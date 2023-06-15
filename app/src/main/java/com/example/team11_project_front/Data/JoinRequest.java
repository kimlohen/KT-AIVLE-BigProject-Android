package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class JoinRequest {

    @SerializedName("first_name")
    public String inputName;
    @SerializedName("email")
    public String inputId;
    @SerializedName("password1")
    public String inputPw;
    @SerializedName("password2")
    public String inputPw2;



    @SerializedName("hospitalCode")
    public String inputHospitalCode;

    @SerializedName("hospitalName")
    public String inputHospitalName;


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
    public String getInputHospitalCode() { return inputHospitalCode; }
    public String getInputHospitalName() { return inputHospitalName; }

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
    public void setInputHospitalCode(String inputHospitalCode) { this.inputHospitalCode = inputHospitalCode; }
    public void setInputHospitalName(String inputHospitalName) { this.inputHospitalName = inputHospitalName; }
    public JoinRequest(String inputName, String inputId, String inputPw, String inputPw2) {
        this.inputName = inputName;
        this.inputId = inputId;
        this.inputPw = inputPw;
        this.inputPw2 = inputPw2;
    }

    public JoinRequest(String inputName, String inputId, String inputPw, String inputPw2, String inputHospitalName, String inputHospitalCode) {
        this.inputName = inputName;
        this.inputId = inputId;
        this.inputPw = inputPw;
        this.inputPw2 = inputPw2;
        this.inputHospitalName = inputHospitalName;
        this.inputHospitalCode = inputHospitalCode;
    }
}