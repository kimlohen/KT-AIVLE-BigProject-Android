package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.AddQRequest;
import com.example.team11_project_front.Data.AddQResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface addQApi {
    @POST("/accounts/api/question/")
    Call<AddQResponse> getAddQResponse(@Header("Authorization") String auth, @Body AddQRequest addQRequest);

}
