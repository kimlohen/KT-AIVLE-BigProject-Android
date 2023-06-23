package com.example.team11_project_front.API;


import com.example.team11_project_front.Data.AnsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface postAnsApi {
    //@통신 방식("통신 API명")
    @POST("/posts/api/question/{id}/answer")
    Call<AnsResponse> getQnaResponse(@Header("Authorization") String auth, @Path("id") String qId, @Body String postAnsRequest);
}