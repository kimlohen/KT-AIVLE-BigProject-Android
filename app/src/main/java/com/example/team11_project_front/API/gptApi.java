package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.GPTRequest;
import com.example.team11_project_front.Data.PictureResponse;
import com.example.team11_project_front.Data.QnaListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface gptApi {
    //@통신 방식("통신 API명")
    @PATCH("/posts/api/picture/{pId}/")
    Call<PictureResponse> gptResponse(@Header("Authorization") String auth, @Path("pId") String pId, @Body GPTRequest gptRequest);
}