package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.PicturePostRequest;
import com.example.team11_project_front.Data.PictureResponse;
import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface picturePostApi {
    //@통신 방식("통신 API명")
    @Multipart
    @POST("/posts/api/picture/")
    Call<PictureResponse> getPictureResponse(@Header("Authorization") String auth, @Part MultipartBody.Part photo, @Part MultipartBody.Part pet_id);
}