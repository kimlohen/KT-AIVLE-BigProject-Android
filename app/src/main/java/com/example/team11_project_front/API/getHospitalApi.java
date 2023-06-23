package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.HospitalResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface getHospitalApi {
    //@통신 방식("통신 API명")
    @GET("/accounts/api/hospital/")
    Call<HospitalResponse> getHospitalResponse(@Header("Authorization") String auth);
}