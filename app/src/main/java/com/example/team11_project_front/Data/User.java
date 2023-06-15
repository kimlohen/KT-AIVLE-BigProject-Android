package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class User
{
    @SerializedName("pk")
    private String pk;
    @SerializedName("email")
    private String email;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;


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
    public void setLast_name (String last_name)
    {
        this.last_name = last_name;
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
    public String getLast_name ()
    {
        return last_name;
    }
}