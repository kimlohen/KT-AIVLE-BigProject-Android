package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.PetlistResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface petlistApi {

    @GET("/accounts/api/pet")
    Call<ArrayList<PetlistResponse>> getPetlistResponse(@Header("Authorization") String auth);

}
