package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.LogoutResponse;

import retrofit2.Call;
import retrofit2.http.POST;

public interface logoutApi {
    //@통신 방식("통신 API명")
    @POST("/accounts/logout/")
    Call<LogoutResponse> getLogoutResponse();
}