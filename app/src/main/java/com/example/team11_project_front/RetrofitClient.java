package com.example.team11_project_front;

import com.example.team11_project_front.API.addPetApi;
import com.example.team11_project_front.API.changePetApi;
import com.example.team11_project_front.API.deletePetApi;
import com.example.team11_project_front.API.deleteUserApi;
import com.example.team11_project_front.API.emailApi;
import com.example.team11_project_front.API.emailVerifyApi;
import com.example.team11_project_front.API.joinApi;
import com.example.team11_project_front.API.loginApi;
import com.example.team11_project_front.API.logoutApi;
import com.example.team11_project_front.API.qnaApi;
import com.example.team11_project_front.API.refreshApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private static com.example.team11_project_front.API.loginApi loginApi;
    private static com.example.team11_project_front.API.emailApi emailApi;
    private static com.example.team11_project_front.API.joinApi joinApi;
    private static com.example.team11_project_front.API.logoutApi logoutApi;
    private static com.example.team11_project_front.API.addPetApi addPetApi;
    private static com.example.team11_project_front.API.emailVerifyApi emailVerifyApi;
    private static com.example.team11_project_front.API.deleteUserApi deleteUserApi;
    private static com.example.team11_project_front.API.qnaApi qnaApi;
    private static com.example.team11_project_front.API.refreshApi refreshApi;
    private static com.example.team11_project_front.API.changePetApi changePetApi;
    private static com.example.team11_project_front.API.petlistApi petlistApi;
    private static com.example.team11_project_front.API.deletePetApi deletePetApi;

    private static com.example.team11_project_front.API.hospitallistApi hospitallistApi;


    //사용하고 있는 서버 BASE 주소
    private static String baseUrl = "http://3.38.104.166/";


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
        addPetApi = retrofit.create(addPetApi.class);
        emailVerifyApi = retrofit.create(emailVerifyApi.class);
        deleteUserApi = retrofit.create(deleteUserApi.class);
        qnaApi = retrofit.create(qnaApi.class);
        refreshApi = retrofit.create(refreshApi.class);
        changePetApi = retrofit.create(com.example.team11_project_front.API.changePetApi.class);
        petlistApi = retrofit.create(com.example.team11_project_front.API.petlistApi.class);
        deletePetApi = retrofit.create(com.example.team11_project_front.API.deletePetApi.class);
        hospitallistApi = retrofit.create(com.example.team11_project_front.API.hospitallistApi.class);

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
    public static addPetApi getRetrofitAddPetInterface() { return addPetApi; }
    public static emailVerifyApi getRetrofitEmailVerifytInterface() { return emailVerifyApi; }
    public static deleteUserApi getRetrofitDeleteUserInterface() { return deleteUserApi; }
    public static qnaApi getRetrofitQnaInterface() { return qnaApi; }
    public static refreshApi getRefreshInterface() { return refreshApi; }
    public static changePetApi getRetrofitChangePetInterface() { return changePetApi; }
    public static com.example.team11_project_front.API.petlistApi getRetrofitPetlistInterface() { return petlistApi; }
    public static deletePetApi getRetrofitDeletePetInterface() { return deletePetApi; }
    public static com.example.team11_project_front.API.hospitallistApi getRetrofitHospitallistInterface() { return hospitallistApi; }
}