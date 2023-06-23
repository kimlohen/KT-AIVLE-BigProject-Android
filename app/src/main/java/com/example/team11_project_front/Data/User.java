package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class User
{
    @SerializedName("id")
    private String pk;
    @SerializedName("email")
    private String email;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("is_vet")
    private String is_vet;
    @SerializedName("profile_img")
    private String profile_img;
    @SerializedName("avatar")
    private String avatar;
    public void setPk (String pk)
    {
        this.pk = pk;
    }
    public void setEmail (String email)
    {
        this.email = email;
    }
    public void setFirst_name (String first_name)
    {
        this.first_name = first_name;
    }
    public void setIs_vet(String is_vet)
    {
        this.is_vet = is_vet;
    }
    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }
    public String getPk ()
    {
        return pk;
    }
    public String getEmail ()
    {
        return email;
    }
    public String getFirst_name () { return first_name; }
    public String getIs_vet()
    {
        return is_vet;
    }
    public String getProfile_img() {
        return profile_img;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}