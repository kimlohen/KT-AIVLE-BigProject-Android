package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.LoginRequest;
import com.example.team11_project_front.Data.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface naverLoginApi {
    //@통신 방식("통신 API명")
    @GET("/accounts/naver/callback/")
    Call<LoginResponse> getLoginResponse(@Query("code") String code, @Query("state") String state);
}