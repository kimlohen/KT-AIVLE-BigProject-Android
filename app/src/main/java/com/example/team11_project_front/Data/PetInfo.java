package com.example.team11_project_front.Data;

public class PetInfo {
    private String name;
    private String birth;
    private String species;
    private String gender;

    public PetInfo(String name, String birth, String species, String gender) {
        this.name = name;
        this.birth = birth;
        this.species = species;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    public String getSpecies() {
        return species;
    }

    public String getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
