package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class AddPetRequest {
    @SerializedName("UserEmail")
    public String inputEmail;
    @SerializedName("name")
    public String inputName;
    @SerializedName("birth")
    public String inputBirth;
    @SerializedName("gender")
    public String inputGender;
    @SerializedName("species")
    public String inputSpecies;
    public String getInputEmail() {
        return inputEmail;
    }
    public String getInputName() {
        return inputName;
    }
    public String getInputBirth() {
        return inputBirth;
    }
    public String getInputGender() {
        return inputGender;
    }
    public String getInputSpecies() {
        return inputSpecies;
    }


    public void setInputEmail(String inputEmail) {
        this.inputEmail = inputEmail;
    }
    public void setInputName(String inputName) {
        this.inputName = inputName;
    }
    public void setInputBirth(String inputBirth) {
        this.inputBirth = inputBirth;
    }
    public void setInputGender(String inputGender) {
        this.inputGender = inputGender;
    }
    public void setInputSpecies(String inputSpecies) {
        this.inputSpecies = inputSpecies;
    }

    public AddPetRequest(String inputEmail, String inputName, String inputBirth, String inputGender, String inputSpecies){
        this.inputEmail = inputEmail;
        this.inputName = inputName;
        this.inputBirth = inputBirth;
        this.inputGender = inputGender;
        this.inputSpecies = inputSpecies;
    }
}