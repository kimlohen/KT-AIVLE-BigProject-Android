package com.example.team11_project_front;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.team11_project_front.API.naverLoginApi;
import com.example.team11_project_front.Data.LoginResponse;

import kotlin.OverloadResolutionByLambdaReturnType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> resultLauncher;
    private WebView mWebView;
    private WebSettings mWebSettings;
    private RetrofitClient retrofitClient;
    private com.example.team11_project_front.API.naverLoginApi naverLoginApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
                String url = request.getUrl().toString();
                Log.d("target", url);

                if(url.contains("http://43.202.5.122/accounts/naver/callback?")){
                    retrofitClient = RetrofitClient.getInstance();
                    naverLoginApi = RetrofitClient.getRetrofitNaverLoginInterface();
                    String target = url.substring(49);
                    target = target.split("&")[0];
                    Log.d("target", target);
                    naverLoginApi.getLoginResponse(target.trim(), "NAVER_LOGIN_STRING").enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            Log.d("retrofit", "Data fetch success");
                            if (response.isSuccessful() && response.body() != null) {

                                //response.body()를 result에 저장
                                LoginResponse result = response.body();

                                //받은 토큰 저장
                                String acessToken = result.getAcessToken();
                                String refreshToken = result.getRefreshToken();

                                String email = result.getUser().getEmail();
                                String first_name = result.getUser().getFirst_name();
                                String is_vet = result.getUser().getIs_vet();
                                String profile_img = result.getUser().getProfile_img();

                                if (acessToken != null) {
                                    //다른 통신을 하기 위해 token 저장
                                    setPreference("acessToken", acessToken);
                                    setPreference("refreshToken", refreshToken);
                                    setPreference("email", email);
                                    setPreference("first_name", first_name);
                                    setPreference("is_vet", is_vet);
                                    setPreference("profile_img", profile_img);
                                    setPreference("autoLoginId", "");
                                    setPreference("autoLoginPw", "");

                                    Toast.makeText(WebViewActivity.this, first_name + "님 환영합니다.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(WebViewActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    WebViewActivity.this.finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(WebViewActivity.this, "로그인에 실패하였습니다. 서버상태를 확인해주세요.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl(url);

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {

                        }
                    }
                }
        );
    }



    // 데이터를 내부 저장소에 저장하기
    public void setPreference(String key, String value){
        SharedPreferences pref = getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    // 내부 저장소에 저장된 데이터 가져오기
    public String getPreferenceString(String key) {
        SharedPreferences pref = getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        return pref.getString(key, "");
    }

    class MyJavaScriptInterface{
        @JavascriptInterface
        fun getHtml(html: String){
            return $html;
        }
    }
}