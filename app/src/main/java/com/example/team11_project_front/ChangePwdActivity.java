package com.example.team11_project_front;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePwdActivity extends AppCompatActivity {


    private EditText changedPwd;
    private EditText changedPwdChk;
    private TextView messageChk01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        changedPwd = findViewById(R.id.changedPwd);
        changedPwdChk = findViewById(R.id.changedPwdChk);
        messageChk01 = findViewById((R.id.messageChk01));


    }

    public void onclickPwdChk01(View v) {

        String pwd = changedPwd.getText().toString();
        String pwd2 = changedPwdChk.getText().toString();

        if (!(pwd.equals(pwd2))) {
            messageChk01.setVisibility(View.VISIBLE);
        } else {
            messageChk01.setVisibility(View.INVISIBLE);
        }
    }


}