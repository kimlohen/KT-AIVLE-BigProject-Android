package com.example.team11_project_front;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class initPW extends AppCompatActivity implements View.OnClickListener{
    private ActivityResultLauncher<Intent> resultLauncher;
    private RetrofitClient retrofitClient;
    private emailApi emailApi;
    public EditText mailEdit;
    public Button sendMailBtn;
    public ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_pw);


        mailEdit = (EditText) findViewById(R.id.editEmail);
        sendMailBtn = (Button) findViewById(R.id.sendMailBtn);
        backBtn = (ImageView) findViewById(R.id.backBtn);

        backBtn.setOnClickListener(this);
        sendMailBtn.setOnClickListener(this);

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

    public void onClick(View v){
        int id = v.getId();
        if (id == R.id.backBtn) {
            Intent intent = new Intent(v.getContext(), LoginActivity.class);
            resultLauncher.launch(intent);
        } else if (id == R.id.sendMailBtn) {
            send();
            Intent intent = new Intent(v.getContext(), LoginActivity.class);
            resultLauncher.launch(intent);
        }

    }

    void send(){
        try {
            String email = mailEdit.getText().toString();
            if (email.trim().length() == 0 || email == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(initPW.this);
                builder.setTitle("알림")
                        .setMessage("이메일 정보를 입력바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else{
                emailResponse();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "이메일 전송 실패!", Toast.LENGTH_LONG).show();
        }
    }

    public void emailResponse(){
        String email = mailEdit.getText().toString().trim();

        EmailRequest emailRequest = new EmailRequest(email);

        retrofitClient = RetrofitClient.getInstance();
        emailApi = RetrofitClient.getRetrofitEmailInterface();

        emailApi.getEmailResponse(emailRequest).enqueue(new Callback<EmailResponse>() {
            @Override
            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {

                Log.d("retrofit", "Data fetch success");

                //통신 성공
                if (response.isSuccessful()) {
                    //이메일 전송
                    Toast.makeText(initPW.this, "이메일을 전송하였습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(initPW.this, LoginActivity.class);
                    startActivity(intent);
                    initPW.this.finish();
                }
            }

            //통신 실패
            @Override
            public void onFailure(Call<EmailResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(initPW.this);
                builder.setTitle("알림")
                        .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }
}
