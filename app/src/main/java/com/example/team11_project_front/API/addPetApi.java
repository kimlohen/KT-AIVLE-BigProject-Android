package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.AddPetRequest;
import com.example.team11_project_front.Data.AddPetResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface addPetApi {
    //@통신 방식("통신 API명")
    @POST("/accounts/api/pet/")
    Call<AddPetResponse> getAddPetResponse(@Header("my-app-auth") String auth, @Body AddPetRequest addPetRequest);
}