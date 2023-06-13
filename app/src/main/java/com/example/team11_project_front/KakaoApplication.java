package com.example.team11_project_front;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    private static KakaoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this,"kakao4ea08aa1447cd2212c1beffea7c02a1a");
    }
}