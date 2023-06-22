package com.example.team11_project_front.API;


import com.example.team11_project_front.Data.AnsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ansApi {
    //@통신 방식("통신 API명")
    @GET("/posts/api/question/{id}/answer")
    Call<List<AnsResponse>> getQnaResponse(@Header("Authorization") String auth, @Path("id") String qId);
}