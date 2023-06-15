package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.JoinRequest;
import com.example.team11_project_front.Data.JoinResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface joinApi {
    //@통신 방식("통신 API명")
    @POST("/accounts/registration/")
    Call<JoinResponse> getJoinResponse(@Body JoinRequest joinRequest);
}