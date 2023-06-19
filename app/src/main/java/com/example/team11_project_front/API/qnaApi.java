package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.LoginRequest;
import com.example.team11_project_front.Data.LoginResponse;
import com.example.team11_project_front.Data.QnaResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface qnaApi {
    //@통신 방식("통신 API명")
    @GET("/posts/api/question")
    Call<ArrayList<QnaResponse>> getQnaResponse(@Header("Authorization") String auth);
}