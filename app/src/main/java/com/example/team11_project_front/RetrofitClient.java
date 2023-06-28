package com.example.team11_project_front;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.example.team11_project_front.API.addPetApi;
import com.example.team11_project_front.API.ansApi;
import com.example.team11_project_front.API.changeHospitalApi;
import com.example.team11_project_front.API.changePetApi;
import com.example.team11_project_front.API.deletePetApi;
import com.example.team11_project_front.API.deleteUserApi;
import com.example.team11_project_front.API.emailApi;
import com.example.team11_project_front.API.emailVerifyApi;
import com.example.team11_project_front.API.getHospitalAdApi;
import com.example.team11_project_front.API.getHospitalApi;
import com.example.team11_project_front.API.hospitalApi;
import com.example.team11_project_front.API.joinApi;
import com.example.team11_project_front.API.loginApi;
import com.example.team11_project_front.API.logoutApi;
import com.example.team11_project_front.API.petlistApi;
import com.example.team11_project_front.API.pictureApi;
import com.example.team11_project_front.API.picturePostApi;
import com.example.team11_project_front.API.postAnsApi;
import com.example.team11_project_front.API.qnaApi;
import com.example.team11_project_front.API.refreshApi;
import com.example.team11_project_front.API.gptApi;
import com.example.team11_project_front.API.userProfileApi;
import com.example.team11_project_front.API.searchApi;

import java.util.concurrent.TimeUnit;

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
    private static com.example.team11_project_front.API.pictureApi pictureApi;
    private static com.example.team11_project_front.API.ansApi ansApi;
    private static hospitalApi hosplitalApi;
    private static getHospitalApi getHospitalApi;
    private static changeHospitalApi changeHospitalApi;
    private static getHospitalAdApi getHospitalAdApi;
    private static com.example.team11_project_front.API.postAnsApi postAnsApi;
    private static picturePostApi picturePostApi;
    private static com.example.team11_project_front.API.addQApi addQApi;
    private static gptApi gptApi;
    private static userProfileApi userProfileApi;
    private static searchApi searchApi;
    //사용하고 있는 서버 BASE 주소
    private static String baseUrl = "http://3.38.191.199/";

    private RetrofitClient() {
        //로그를 보기 위한 Interceptor
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
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
        petlistApi = retrofit.create(petlistApi.class);
        changePetApi = retrofit.create(changePetApi.class);
        deletePetApi = retrofit.create(deletePetApi.class);
        pictureApi = retrofit.create(pictureApi.class);
        ansApi = retrofit.create(ansApi.class);
        hosplitalApi = retrofit.create(hospitalApi.class);
        getHospitalApi = retrofit.create(getHospitalApi.class);
        changeHospitalApi = retrofit.create(changeHospitalApi.class);
        getHospitalAdApi = retrofit.create(getHospitalAdApi.class);
        postAnsApi = retrofit.create(postAnsApi.class);
        picturePostApi = retrofit.create(picturePostApi.class);
        addQApi = retrofit.create(com.example.team11_project_front.API.addQApi.class);
        gptApi = retrofit.create(gptApi.class);
        userProfileApi = retrofit.create(userProfileApi.class);
        searchApi = retrofit.create(searchApi.class);
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
    public static petlistApi getRetrofitPetlistInterface() { return petlistApi; }
    public static deletePetApi getRetrofitDeletePetInterface() { return deletePetApi; }
    public static pictureApi getRetrofitPictureInterface() { return pictureApi; }
    public static ansApi getRetrofitAnswerInterface() { return ansApi; }
    public static hospitalApi getRetrofitHospitalInterface() { return hosplitalApi; }
    public static getHospitalApi getRetrofitGetHospitalInterface() { return getHospitalApi; }
    public static changeHospitalApi getRetrofitChangeHospitalInterface() { return changeHospitalApi; }
    public static getHospitalAdApi getRetrofitGetHospitalAdInterface() { return getHospitalAdApi; }
    public static postAnsApi getRetrofitPostAnswerInterface() { return postAnsApi; }
    public static picturePostApi getRetrofitPostPictureInterface() { return picturePostApi; }
    public static com.example.team11_project_front.API.addQApi getRetrofitAddQInterface() { return addQApi; }
    public static gptApi getRetrofitGPTInterface() { return gptApi; }
    public static userProfileApi getRetrofitUserProfileInterface() { return userProfileApi; }
    public static searchApi getRetrofitSearchInterface() { return searchApi; }
}