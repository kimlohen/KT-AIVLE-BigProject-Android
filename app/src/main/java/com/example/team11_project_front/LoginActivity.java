package com.example.team11_project_front;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import retrofit2.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private FragmentManager fm = getSupportFragmentManager();
    private FragmentTransaction ft;
    private ActivityResultLauncher<Intent> resultLauncher;
    private RetrofitClient retrofitClient;
    private initMyApi initMyApi;

    public EditText idEdit, pwEdit;
    public Button loginBtn, joinBtn;
    public CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ft = fm.beginTransaction();

        idEdit = (EditText) findViewById(R.id.editID);
        pwEdit = (EditText) findViewById(R.id.editPW);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        joinBtn = (Button) findViewById(R.id.joinBtn);
        checkBox = findViewById(R.id.autoLogin);

        //자동 로그인을 선택한 유저
        if (!getPreferenceString("autoLoginId").equals("") && !getPreferenceString("autoLoginPw").equals("")) {
            checkBox.setChecked(true);
            checkAutoLogin(getPreferenceString("autoLoginId"));
        }

        loginBtn.setOnClickListener(this);
        joinBtn.setOnClickListener(this);

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

    @Override
    public void onClick(View v){
        int id = v.getId();
        if (id == R.id.loginBtn) {
            login();
        } else if (id == R.id.joinBtn) {
            Intent intent = new Intent(v.getContext(), MainActivity.class); // 회원가입 페이지 만들면 회원가입으로
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
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }else{
                LoginResponse();
            }

        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_LONG).show();
        }
    }

    public void LoginResponse(){
        String userID = idEdit.getText().toString().trim();
        String userPassword = pwEdit.getText().toString().trim();

        //loginRequest에 사용자가 입력한 id와 pw를 저장
        LoginRequest loginRequest = new LoginRequest(userID, userPassword);

        //retrofit 생성
        retrofitClient = RetrofitClient.getInstance();
        initMyApi = RetrofitClient.getRetrofitInterface();

        //loginRequest에 저장된 데이터와 함께 init에서 정의한 getLoginResponse 함수를 실행한 후 응답을 받음
        initMyApi.getLoginResponse(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Log.d("retrofit", "Data fetch success");

                //통신 성공
                if (response.isSuccessful() && response.body() != null) {

                    //response.body()를 result에 저장
                    LoginResponse result = response.body();

                    //받은 코드 저장
                    String resultCode = result.getResultCode();

                    //받은 토큰 저장
                    String token = result.getToken();

                    String success = "200"; //로그인 성공
                    String errorId = "300"; //아이디 일치x
                    String errorPw = "400"; //비밀번호 일치x


                    if (resultCode.equals(success)) {
                        String userID = idEdit.getText().toString();
                        String userPassword = pwEdit.getText().toString();

                        //다른 통신을 하기 위해 token 저장
                        setPreference(token,token);

                        //자동 로그인 여부
                        if (checkBox.isChecked()) {
                            setPreference("autoLoginId", userID);
                            setPreference("autoLoginPw", userPassword);
                        } else {
                            setPreference("autoLoginId", "");
                            setPreference("autoLoginPw", "");
                        }

                        Toast.makeText(LoginActivity.this, userID + "님 환영합니다.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userId", userID);
                        startActivity(intent);
                        LoginActivity.this.finish();

                    } else if (resultCode.equals(errorId)) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("알림")
                                .setMessage("아이디가 존재하지 않습니다.\n 고객센터에 문의바랍니다.")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    } else if (resultCode.equals(errorPw)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("알림")
                                .setMessage("비밀번호가 일치하지 않습니다.\n 고객" +
                                        "센터에 문의바랍니다.")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("알림")
                                .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();

                    }
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

    //데이터를 내부 저장소에 저장하기
    public void setPreference(String key, String value){
        SharedPreferences pref = getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    //내부 저장소에 저장된 데이터 가져오기
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

        //Toast.makeText(this, id + "님 환영합니다.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}