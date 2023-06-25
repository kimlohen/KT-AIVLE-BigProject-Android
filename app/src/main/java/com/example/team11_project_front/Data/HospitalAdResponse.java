package com.example.team11_project_front.Data;

import com.google.gson.annotations.SerializedName;

public class HospitalAdResponse {
    @SerializedName("hospital")
    private HospitalResponse hospital;

    public HospitalResponse getHospital() {
        return hospital;
    }

    public void setHospital(HospitalResponse hospital) {
        this.hospital = hospital;
    }
}

