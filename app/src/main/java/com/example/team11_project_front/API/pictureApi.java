package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.LoginResponse;
import com.example.team11_project_front.Data.PictureResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface pictureApi {
    //@통신 방식("통신 API명")
    @GET("/posts/api/picture/{picture_id}/")
    Call<PictureResponse> getPictureResponse(@Header("Authorization") String auth, @Path("picture_id") String picture_id);
}