package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class PetlistResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("species")
    private String species;
    @SerializedName("birth")
    private String birth;
    @SerializedName("gender")
    private String gender;
    @SerializedName("is_neu")
    private boolean is_neu;
    @SerializedName("adoption_date")
    private String adoption_date;





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdoption_date() {
        return adoption_date;
    }

    public void setAdoption_date(String adoption_date) {
        this.adoption_date = adoption_date;
    }

    public boolean isIs_neu() {
        return is_neu;
    }

    public void setIs_neu(boolean is_neu) {
        this.is_neu = is_neu;
    }


}
