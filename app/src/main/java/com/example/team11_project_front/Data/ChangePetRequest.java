package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class ChangePetRequest {

    @SerializedName("id")
    public String inputId;

    @SerializedName("name")
    public String inputName;
    @SerializedName("birth")
    public String inputBirth;
    @SerializedName("gender")
    public String inputGender;
    @SerializedName("species")
    public String inputSpecies;

    public String getInputId() {
        return inputId;
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


    public void setInputId(String id) {
        this.inputId = inputId;
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

    public ChangePetRequest(String inputId, String inputName, String inputBirth, String inputGender, String inputSpecies){

        this.inputId = inputId;
        this.inputName = inputName;
        this.inputBirth = inputBirth;
        this.inputGender = inputGender;
        this.inputSpecies = inputSpecies;
    }
}
