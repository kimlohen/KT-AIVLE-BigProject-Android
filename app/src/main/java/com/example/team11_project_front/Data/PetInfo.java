package com.example.team11_project_front.Data;

public class PetInfo {
    private String name;
    private String birth;

    private String gender;

    private String species;
    private String id;

    public PetInfo(String id, String name, String birth, String species, String gender) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.species = species;

    }

    public String getId() { return id;}
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

    public void setId(String id) {this.id = id; }
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
