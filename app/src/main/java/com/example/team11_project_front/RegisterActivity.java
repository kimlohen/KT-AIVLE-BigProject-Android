package com.example.team11_project_front;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team11_project_front.Data.JoinRequest;
import com.example.team11_project_front.Data.JoinResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityResultLauncher<Intent> resultLauncher;
    private RetrofitClient retrofitClient;
    private joinApi joinApi;
    private Switch veterinarianBtn;
    private EditText pwEdit, pwEdit2, nameEdit, mailEdit, hospitalNameEdit, hospitalCodeEdit;
    private CheckBox serviceOkBtn;
    private ImageView backBtn;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        backBtn = (ImageView) findViewById(R.id.backBtn3);
        veterinarianBtn = (Switch) findViewById(R.id.veterinarianBtn);
        nameEdit = (EditText) findViewById(R.id.nameEdit);
        mailEdit = (EditText) findViewById(R.id.mailEdit);
        pwEdit = (EditText) findViewById(R.id.pwEdit);
        pwEdit2 = (EditText) findViewById(R.id.pwEdit2);
        hospitalNameEdit = (EditText) findViewById(R.id.hospitalNameEdit);
        hospitalCodeEdit = (EditText) findViewById(R.id.hospitalCodeEdit);
        serviceOkBtn = (CheckBox) findViewById(R.id.serviceOkBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);

        backBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

        veterinarianBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hospitalNameEdit.setVisibility(View.VISIBLE);
                    hospitalCodeEdit.setVisibility(View.VISIBLE);
                } else {
                    hospitalNameEdit.setVisibility(View.GONE);
                    hospitalCodeEdit.setVisibility(View.GONE);
                }
            }
        });

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
        imm.hideSoftInputFromWindow(nameEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mailEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(pwEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(pwEdit2.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(hospitalCodeEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(hospitalNameEdit.getWindowToken(), 0);
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

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.backBtn3) {
            Intent intent = new Intent(v.getContext(), LoginActivity.class);
            resultLauncher.launch(intent);
        } else if (id == R.id.registerBtn) {
            send();
        }
    }

    void send(){
        try {
            String email = mailEdit.getText().toString();
            String name = nameEdit.getText().toString();
            String pw = pwEdit.getText().toString();
            String pw2 = pwEdit2.getText().toString();
            String hospitalName = hospitalNameEdit.getText().toString();
            String hospitalCode = hospitalCodeEdit.getText().toString();

            if (name.trim().length() == 0 || name == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("이름 정보를 입력바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else if (email.trim().length() == 0 || email == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("이메일 정보를 입력바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else if (pw.trim().length() == 0 || pw == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("비밀번호 정보를 입력바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else if (pw2.trim().length() == 0 || pw == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("비밀번호 확인 정보를 입력바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else if (!pw.equals(pw2)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("비밀번호 입력과 확인이 다릅니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else if (!serviceOkBtn.isChecked()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("서비스 약관의 동의를 바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else if (veterinarianBtn.isChecked() && (hospitalName.trim().length() == 0 || hospitalName == null)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("병원명을 입력바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else if (veterinarianBtn.isChecked() && (hospitalCode.trim().length() == 0 || hospitalCode == null)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("병원명을 입력바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else{
                joinResponse();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "회원가입 전송 실패!", Toast.LENGTH_LONG).show();
        }
    }

    public void joinResponse() {
        String email = mailEdit.getText().toString().trim();
        String name = nameEdit.getText().toString().trim();
        String pw = pwEdit.getText().toString().trim();
        String pw2 = pwEdit2.getText().toString().trim();
        String hospitalName = hospitalNameEdit.getText().toString().trim();
        String hospitalCode = hospitalCodeEdit.getText().toString().trim();

        JoinRequest joinRequest = new JoinRequest(name, email, pw, pw2);

        if (veterinarianBtn.isChecked()){
            joinRequest = new JoinRequest(name, email, pw, pw2, hospitalName, hospitalCode);
        }

        retrofitClient = RetrofitClient.getInstance();
        joinApi = RetrofitClient.getRetrofitJoinInterface();

        joinApi.getJoinResponse(joinRequest).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {

                Log.d("retrofit", "Data fetch success");

                //통신 성공
                if (response.isSuccessful()) {
                    //이메일 전송
                    Toast.makeText(RegisterActivity.this, "회원가입되었습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    RegisterActivity.this.finish();
                }
            }

            //통신 실패
            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }

}