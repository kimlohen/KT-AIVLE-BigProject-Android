package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.AddPetRequest;
import com.example.team11_project_front.Data.AddPetResponse;
import com.example.team11_project_front.Data.LoginResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface userProfileApi {
    //@통신 방식("통신 API명")
    @Multipart
    @PATCH("/accounts/user/")
    Call<LoginResponse> getUserProfileResponse(@Header("Authorization") String auth, @Part MultipartBody.Part profile_img);
}