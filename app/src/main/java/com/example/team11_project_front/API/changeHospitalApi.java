package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.ChangeHospitalRequest;
import com.example.team11_project_front.Data.ChangeHospitalResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface changeHospitalApi {

    @PATCH("/accounts/api/hospital/{id}/")
    Call<ChangeHospitalResponse> getChangeHospitalResponse (@Header("Authorization") String auth, @Path("id") String id, @Body ChangeHospitalRequest changeHospitalRequest);

}
