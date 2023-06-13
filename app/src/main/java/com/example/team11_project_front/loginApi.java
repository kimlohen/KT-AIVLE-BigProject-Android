package com.example.team11_project_front;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface loginApi {
    //@통신 방식("통신 API명")
    @POST("/accounts/login/")
    Call<LoginResponse> getLoginResponse(@Body LoginRequest loginRequest);
}