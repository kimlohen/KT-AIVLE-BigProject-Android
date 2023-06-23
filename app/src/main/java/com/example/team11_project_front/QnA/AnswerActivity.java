package com.example.team11_project_front.QnA;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team11_project_front.API.postAnsApi;
import com.example.team11_project_front.Data.AnsResponse;
import com.example.team11_project_front.Data.PostAnsRequest;
import com.example.team11_project_front.R;
import com.example.team11_project_front.RetrofitClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnswerActivity extends AppCompatActivity {
    private RetrofitClient retrofitClient;
    private postAnsApi postAnsApi;
    private ImageView iv_disease, backBtn;
    private TextView questionText;
    private EditText answerText;
    private Button submitBtn;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        iv_disease = (ImageView) findViewById(R.id.diseaseImg2);
        backBtn = (ImageView) findViewById(R.id.backBtn4);
        questionText = (TextView) findViewById(R.id.questionText);
        answerText = (EditText) findViewById(R.id.answerText);
        submitBtn = (Button) findViewById(R.id.answerSubmit);

        questionText.setText(getIntent().getStringExtra("questionText"));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                retrofitClient = RetrofitClient.getInstance();
                postAnsApi = RetrofitClient.getRetrofitPostAnswerInterface();
                Log.e("qId", getIntent().getStringExtra("qId") + "");
                String answer = answerText.getText().toString();
                PostAnsRequest postAnsRequest = new PostAnsRequest(answer);
                postAnsApi.getQnaResponse("Bearer " + getPreferenceString("acessToken"), getIntent().getStringExtra("qId"), postAnsRequest).enqueue(new Callback<AnsResponse>() {
                    @Override
                    public void onResponse(Call<AnsResponse> call, Response<AnsResponse> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "답변이 등록되었습니다!", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }else{
                            Toast.makeText(getApplicationContext(), "답변 등록이 실패하였습니다!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AnsResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "답변 등록이 실패하였습니다!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        Thread mThread = new Thread(){
            @Override
            public void run(){
                try {
                    URL url = new URL(getIntent().getStringExtra("pictureUrl"));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };

        mThread.start();
        try{
            mThread.join();
            iv_disease.setImageBitmap(bitmap.createScaledBitmap(bitmap, 400, 400, false));
        }catch (InterruptedException e){
            e.printStackTrace();
        }

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
}