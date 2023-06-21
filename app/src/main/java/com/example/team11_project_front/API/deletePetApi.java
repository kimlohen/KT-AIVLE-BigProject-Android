package com.example.team11_project_front.API;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface deletePetApi {
    @DELETE("/accounts/api/pet/{id}/")
    Call<Void> getDeletePetResponse(@Header("Authorization") String auth, @Path("id") String id);
}
