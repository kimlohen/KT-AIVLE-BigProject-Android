package com.example.team11_project_front.API;


import com.example.team11_project_front.Data.AnsResponse;
import com.example.team11_project_front.Data.QnaListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface searchApi {
    //@통신 방식("통신 API명")
    @GET("posts/api/question/")
    Call<QnaListResponse> getQnaResponse(@Header("Authorization") String auth, @Query("search") String searchText);
}