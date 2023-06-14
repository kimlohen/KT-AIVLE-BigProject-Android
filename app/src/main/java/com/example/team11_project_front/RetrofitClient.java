package com.example.team11_project_front;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private static loginApi loginApi;
    private static emailApi emailApi;
    private static joinApi joinApi;
    private static logoutApi logoutApi;
    //사용하고 있는 서버 BASE 주소
    private static String baseUrl = "http://13.124.194.227/";


    private RetrofitClient() {
        //로그를 보기 위한 Interceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        //retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) //로그 기능 추가
                .build();

        loginApi = retrofit.create(loginApi.class);
        emailApi = retrofit.create(emailApi.class);
        joinApi = retrofit.create(joinApi.class);
        logoutApi = retrofit.create(logoutApi.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static loginApi getRetrofitLoginInterface() {
        return loginApi;
    }
    public static emailApi getRetrofitEmailInterface() {
        return emailApi;
    }
    public static joinApi getRetrofitJoinInterface() {
        return joinApi;
    }
    public static logoutApi getRetrofitLogoutInterface() {
        return logoutApi;
    }
}