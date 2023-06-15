package com.example.team11_project_front.API;

import com.example.team11_project_front.Data.EmailRequest;
import com.example.team11_project_front.Data.EmailResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface emailApi {
    //@통신 방식("통신 API명")
    @POST("/accounts/password/reset/")
    Call<EmailResponse> getEmailResponse(@Body EmailRequest emailRequest);
}