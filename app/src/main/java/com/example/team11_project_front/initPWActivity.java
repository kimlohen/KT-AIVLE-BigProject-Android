package com.example.team11_project_front;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.team11_project_front.Data.EmailRequest;
import com.example.team11_project_front.Data.EmailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class initPWActivity extends AppCompatActivity implements View.OnClickListener{
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

    //키보드 숨기기
    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mailEdit.getWindowToken(), 0);
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

    public void onClick(View v){
        int id = v.getId();
        if (id == R.id.backBtn) {
            Intent intent = new Intent(v.getContext(), LoginActivity.class);
            resultLauncher.launch(intent);
        } else if (id == R.id.sendMailBtn) {
            send();
        }

    }

    void send(){
        try {
            String email = mailEdit.getText().toString();
            if (email.trim().length() == 0 || email == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(initPWActivity.this);
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
                    Toast.makeText(initPWActivity.this, "이메일을 전송하였습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(initPWActivity.this, LoginActivity.class);
                    startActivity(intent);
                    initPWActivity.this.finish();
                }
            }

            //통신 실패
            @Override
            public void onFailure(Call<EmailResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(initPWActivity.this);
                builder.setTitle("알림")
                        .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }
}
