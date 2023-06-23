package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.HospitallistResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface hospitallistApi {

    @GET("/accounts/api/hospital")
    Call<ArrayList<HospitallistResponse>> getHospitallistResponse(@Header("Authorization") String auth);
}
