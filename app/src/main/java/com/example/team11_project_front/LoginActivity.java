package com.example.team11_project_front;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.example.team11_project_front.API.emailVerifyApi;
import com.example.team11_project_front.API.joinApi;
import com.example.team11_project_front.Data.EmailRequest;
import com.example.team11_project_front.Data.EmailResponse;
import com.example.team11_project_front.Data.JoinRequest;
import com.example.team11_project_front.Data.JoinResponse;
import com.example.team11_project_front.Data.LoginRequest;
import com.example.team11_project_front.Data.LoginResponse;
import com.google.android.gms.common.SignInButton;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityResultLauncher<Intent> resultLauncher;
    private RetrofitClient retrofitClient;
    private com.example.team11_project_front.API.loginApi loginApi;
    public TextView initPW;
    public EditText idEdit, pwEdit;
    public Button loginBtn, joinBtn;
    public CheckBox checkBox;
    public ImageButton kakaoBtn, naverBtn;
    public SignInButton googleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idEdit = (EditText) findViewById(R.id.editID);
        pwEdit = (EditText) findViewById(R.id.editPW);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        joinBtn = (Button) findViewById(R.id.joinBtn);
        checkBox = findViewById(R.id.autoLogin);
        kakaoBtn = (ImageButton) findViewById(R.id.kakaoLoginBtn);
        naverBtn = (ImageButton) findViewById(R.id.naverLoginBtn);
        googleBtn = (SignInButton) findViewById(R.id.googleLoginBtn);
        initPW =  (TextView) findViewById(R.id.initPW);

        //자동 로그인을 선택한 유저
        if (!getPreferenceString("autoLoginId").equals("") && !getPreferenceString("autoLoginPw").equals("")) {
            checkBox.setChecked(true);
            checkAutoLogin(getPreferenceString("autoLoginId"));
        }

        loginBtn.setOnClickListener(this);
        joinBtn.setOnClickListener(this);
        googleBtn.setOnClickListener(this);
        kakaoBtn.setOnClickListener(this);
        naverBtn.setOnClickListener(this);
        initPW.setOnClickListener(this);

        setGooglePlusButtonText(googleBtn, "구글로 로그인");

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

    // 카카오톡이 설치되어 있는지 확인하는 메서드 , 카카오에서 제공함. 콜백 객체를 이용합.
    Function2<OAuthToken,Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
        @Override
        // 콜백 메서드 ,
        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
            Log.e(TAG,"CallBack Method");
            //oAuthToken != null 이라면 로그인 성공
            if(oAuthToken!=null){
                // 토큰이 전달된다면 로그인이 성공한 것이고 토큰이 전달되지 않으면 로그인 실패한다.
                updateKakaoLoginUi();
            }else {
                //로그인 실패
                Log.e(TAG, "invoke: login fail" );
            }

            return null;
        }
    };

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if (id == R.id.loginBtn) {
            login();
        } else if (id == R.id.joinBtn) {
            Intent intent = new Intent(v.getContext(), RegisterActivity.class); // 회원가입 페이지 만들면 변경
            resultLauncher.launch(intent);
        } else if (id == R.id.googleLoginBtn){
            Intent intent = new Intent(v.getContext(), MainActivity.class); // 구글 기능 구현하면
            intent.putExtra("url", "");
            resultLauncher.launch(intent);
        } else if (id == R.id.kakaoLoginBtn){
            if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
            }else {
                UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                updateKakaoLoginUi();
            }
        } else if (id == R.id.naverLoginBtn){
            Intent intent = new Intent(v.getContext(), WebViewActivity.class); // 네이버 기능 구현하면
            intent.putExtra("url", "43.202.5.122" + "/accounts/naver/login");
            resultLauncher.launch(intent);
        } else if (id == R.id.initPW) {
            Intent intent = new Intent(v.getContext(), initPWActivity.class); // 비밀번호 찾기 페이지 만들면 변경
            resultLauncher.launch(intent);
        }

    }

    void login(){
        try{
            String id = idEdit.getText().toString();
            String pw = pwEdit.getText().toString();
            hideKeyboard();

            if(id.trim().length() == 0 || pw.trim().length() == 0 || id==null || pw==null){
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("알림")
                        .setMessage("로그인 정보를 입력바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else{
                LoginResponse();
            }

        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_LONG).show();
        }
    }

    public void LoginResponse() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String userID = idEdit.getText().toString().trim();
        String userPassword = pwEdit.getText().toString().trim();

        //loginRequest에 사용자가 입력한 id와 pw를 저장
        LoginRequest loginRequest = new LoginRequest(userID, sha256(userPassword));

        //retrofit 생성
        retrofitClient = RetrofitClient.getInstance();
        loginApi = RetrofitClient.getRetrofitLoginInterface();

        //loginRequest에 저장된 데이터와 함께 init에서 정의한 getLoginResponse 함수를 실행한 후 응답을 받음
        loginApi.getLoginResponse(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Log.d("retrofit", "Data fetch success");

                //통신 성공
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
                        String userID = idEdit.getText().toString();
                        String userPassword = pwEdit.getText().toString();

                        //다른 통신을 하기 위해 token 저장
                        setPreference("acessToken",acessToken);
                        setPreference("refreshToken",refreshToken);
                        setPreference("email", email);
                        setPreference("first_name", first_name);
                        setPreference("is_vet", is_vet);
                        setPreference("profile_img", profile_img);

                        //자동 로그인 여부
                        if (checkBox.isChecked()) {
                            setPreference("autoLoginId", userID);
                            try {
                                setPreference("autoLoginPw", sha256(userPassword));
                            } catch (NoSuchAlgorithmException e) {
                                throw new RuntimeException(e);
                            } catch (UnsupportedEncodingException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            setPreference("autoLoginId", "");
                            setPreference("autoLoginPw", "");
                        }

                        Toast.makeText(LoginActivity.this, first_name + "님 환영합니다.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userId", userID);
                        startActivity(intent);
                        LoginActivity.this.finish();

                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("알림")
                                .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();
                    }
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("알림")
                            .setMessage("이메일, 비밀번호가 일치하지 않습니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }
            }

            //통신 실패
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("알림")
                        .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
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

    //키보드 숨기기
    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(idEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(pwEdit.getWindowToken(), 0);
    }

    //화면 터치 시 키보드 내려감
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    //자동 로그인 유저
    public void checkAutoLogin(String id){
        String userID = getPreferenceString("autoLoginId").trim();
        String userPassword = getPreferenceString("autoLoginPw").trim();

        LoginRequest loginRequest = new LoginRequest(userID, userPassword);

        //retrofit 생성
        retrofitClient = RetrofitClient.getInstance();
        loginApi = RetrofitClient.getRetrofitLoginInterface();

        //loginRequest에 저장된 데이터와 함께 init에서 정의한 getLoginResponse 함수를 실행한 후 응답을 받음
        loginApi.getLoginResponse(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Log.d("retrofit", "Data fetch success");
                //통신 성공
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
                        String userID = getPreferenceString("autoLoginId");
                        String userPassword = getPreferenceString("autoLoginPw");

                        //다른 통신을 하기 위해 token 저장
                        setPreference("acessToken",acessToken);
                        setPreference("refreshToken",refreshToken);
                        setPreference("email", email);
                        setPreference("first_name", first_name);
                        setPreference("is_vet", is_vet);
                        setPreference("profile_img", profile_img);

                        //자동 로그인 여부
                        if (checkBox.isChecked()) {
                            setPreference("autoLoginId", userID);
                            setPreference("autoLoginPw", userPassword);
                        } else {
                            setPreference("autoLoginId", "");
                            setPreference("autoLoginPw", "");
                        }

                        Toast.makeText(LoginActivity.this, first_name + "님 환영합니다.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();

                    } else {
                        Toast.makeText(LoginActivity.this, "잘못된 아이디 또는 비밀번호 입니다.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "잘못된 아이디 또는 비밀번호 입니다.", Toast.LENGTH_LONG).show();
                }
            }

            //통신 실패
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "잘못된 아이디 또는 비밀번호 입니다.", Toast.LENGTH_LONG).show();
            }
        });

        //Toast.makeText(this, id + "님 환영합니다.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void updateKakaoLoginUi() {

        // 로그인 여부에 따른 UI 설정
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {

                String email = null;
                if (user != null) {

                    // 유저의 아이디
                    Log.d(TAG, "invoke: id =" + user.getId());
                    // 유저의 이메일
                    Log.d(TAG, "invoke: email =" + user.getKakaoAccount().getEmail());
                    // 유저의 닉네임
                    Log.d(TAG, "invoke: nickname =" + user.getKakaoAccount().getProfile().getNickname());
                    // 유저의 프로필
                    Log.d(TAG, "invoke: nickname =" + user.getKakaoAccount().getProfile().getProfileImageUrl());

                    // 로그인이 되어있으면

                    email = user.getKakaoAccount().getEmail();
                    String name = user.getKakaoAccount().getProfile().getNickname();
                    String profile = user.getKakaoAccount().getProfile().getProfileImageUrl();
                    String pw = name + email;
                    try {
                        pw = sha256(pw);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    EmailRequest emailRequest = new EmailRequest(email);
                    retrofitClient = RetrofitClient.getInstance();
                    emailVerifyApi emailVerifyApi = RetrofitClient.getRetrofitEmailVerifytInterface();
                    String finalEmail = email;
                    String finalPw = pw;
                    emailVerifyApi.getEmailResponse(emailRequest).enqueue(new Callback<EmailResponse>() {
                        @Override
                        public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
                            Log.d("retrofit", "Data fetch success");
                            if (response.isSuccessful() && response.body() != null) {
                                EmailResponse result = response.body();
                                String success = result.getSuccess();
                                if (success.equals("true")) { // 카카오로 처음 로그인
                                    JoinRequest joinRequest = new JoinRequest(name, finalEmail, finalPw, finalPw);
                                    joinApi joinApi = RetrofitClient.getRetrofitJoinInterface();
                                    joinApi.getJoinResponse(joinRequest).enqueue(new Callback<JoinResponse>() {
                                        @Override
                                        public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {

                                            Log.d("retrofit", "Data fetch success");

                                            //통신 성공
                                            if (response.isSuccessful() && response.body() != null) {
                                                JoinResponse result = response.body();

                                                String acessToken = result.getAcessToken();
                                                String refreshToken = result.getRefreshToken();
                                                String email = result.getUser().getEmail();
                                                String first_name = result.getUser().getFirst_name();
                                                String profile_img = result.getUser().getIs_vet();
                                                String is_vet = result.getUser().getProfile_img();

                                                if (acessToken != null) {
                                                    //다른 통신을 하기 위해 token 저장
                                                    setPreference("acessToken", acessToken);
                                                    setPreference("refreshToken", refreshToken);
                                                    setPreference("email", email);
                                                    setPreference("first_name", first_name);
                                                    setPreference("is_vet", is_vet);
                                                    setPreference("profile_img", profile_img);
                                                }

                                                /*
                                                * 프로필 사진 등록 통신이 필요(url로 등록)
                                                * */

                                                //회원가입 전송
                                                Toast.makeText(LoginActivity.this, "회원가입되었습니다.", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                LoginActivity.this.finish();
                                            }
                                        }

                                        //통신 실패
                                        @Override
                                        public void onFailure(Call<JoinResponse> call, Throwable t) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                            builder.setTitle("알림")
                                                    .setMessage("회원가입 중 예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                                                    .setPositiveButton("확인", null)
                                                    .create()
                                                    .show();
                                        }
                                    });
                                } else if (success.equals("false")) { // 카카오로 두번째 로그인 또는 회원가입 아이디가 존재.
                                    //loginRequest에 사용자가 입력한 id와 pw를 저장
                                    LoginRequest loginRequest = new LoginRequest(finalEmail, finalPw);

                                    loginApi = RetrofitClient.getRetrofitLoginInterface();

                                    //loginRequest에 저장된 데이터와 함께 init에서 정의한 getLoginResponse 함수를 실행한 후 응답을 받음
                                    loginApi.getLoginResponse(loginRequest).enqueue(new Callback<LoginResponse>() {
                                        @Override
                                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                                            Log.d("retrofit", "Data fetch success");

                                            //통신 성공
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

                                                    //자동 로그인 여부
                                                    if (checkBox.isChecked()) {
                                                        setPreference("autoLoginId", email);
                                                        setPreference("autoLoginPw", finalPw);
                                                    } else {
                                                        setPreference("autoLoginId", "");
                                                        setPreference("autoLoginPw", "");
                                                    }

                                                    Toast.makeText(LoginActivity.this, first_name + "님 환영합니다.", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    intent.putExtra("userId", email);
                                                    startActivity(intent);
                                                    LoginActivity.this.finish();
                                                } else {

                                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                                    builder.setTitle("알림")
                                                            .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                                                            .setPositiveButton("확인", null)
                                                            .create()
                                                            .show();
                                                }
                                            } else {

                                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                                builder.setTitle("알림")
                                                        .setMessage("카카오로 시작하기로 가입하지 않은 이메일 입니다.")
                                                        .setPositiveButton("확인", null)
                                                        .create()
                                                        .show();
                                            }
                                        }

                                        //통신 실패
                                        @Override
                                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                            builder.setTitle("알림")
                                                    .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                                                    .setPositiveButton("확인", null)
                                                    .create()
                                                    .show();
                                        }
                                    });
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setTitle("알림")
                                        .setMessage("")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<EmailResponse> call, Throwable t) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("알림")
                                    .setMessage("서버와 통신에 실패하였습니다. 네트워크를 확인해주세요.")
                                    .setPositiveButton("확인", null)
                                    .create()
                                    .show();
                        }
                    });


                } else {
                    // 로그인 되어있지 않으면
                }
                return null;
            }
        });
    }

    public static String sha256(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes("UTF-8"));
        byte[] bytes = md.digest();
        String hash = String.format("%64x", new BigInteger(1, bytes));
        return hash;
    }
}