package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.HospitalRequest;
import com.example.team11_project_front.Data.HospitalResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface hospitalApi {
    //@통신 방식("통신 API명")
    @POST("/accounts/api/hospital/")
    Call<HospitalResponse> getJoinResponse(@Header("Authorization") String auth, @Body HospitalRequest HospitalRequest);
}