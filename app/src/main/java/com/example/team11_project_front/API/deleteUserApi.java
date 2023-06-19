package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.DeleteUserResponse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface deleteUserApi {
    //@통신 방식("통신 API명")
    @POST("/accounts/user/delete/")
    Call<DeleteUserResponse> getDeleteUserResponse(@Header("my-app-auth") String auth);
}