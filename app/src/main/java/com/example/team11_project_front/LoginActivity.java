package com.example.team11_project_front;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private FragmentManager fm = getSupportFragmentManager();
    private FragmentTransaction ft;
    private ActivityResultLauncher<Intent> resultLauncher;

    public EditText idEdit, pwEdit;
    public Button loginBtn, joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ft = fm.beginTransaction();

        idEdit = (EditText) findViewById(R.id.editID);
        pwEdit = (EditText) findViewById(R.id.editPW);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        joinBtn = (Button) findViewById(R.id.joinBtn);

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

            Log.w("보낸값", id+", "+pw);
            CustomTask task  = new CustomTask();
            String result = task.execute(id, pw).get();
            Log.w("받은값", result);
            if (result != "1") {
                Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                resultLauncher.launch(intent);
            }else{
                Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_LONG).show();
            }
            finish();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_LONG).show();
        }
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... params){
            try {
                String str;
                URL url = new URL("http://내ip:8080/프로젝트이름/요청값이름");  // 어떤 서버에 요청할지(localhost 안됨.)
                // ex) http://123.456.789.10:8080/hello/android
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");                              //데이터를 POST 방식으로 전송합니다.
                conn.setDoOutput(true);

                // 서버에 보낼 값 포함해 요청함.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id="+params[0]+"&pw="+params[1]; // GET방식으로 작성해 POST로 보냄 ex) "id=admin&pwd=1234";
                osw.write(sendMsg);                           // OutputStreamWriter에 담아 전송
                osw.flush();

                // jsp와 통신이 잘 되고, 서버에서 보낸 값 받음.
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }

    }

}