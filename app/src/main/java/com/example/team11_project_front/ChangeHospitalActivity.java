package com.example.team11_project_front;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeHospitalActivity extends AppCompatActivity {

// 사용할 레이어 선언
    private EditText editTextText;
    private EditText editTextText2;
    private EditText editTextText3;
    private EditText editTextText4;

    private String PID;
    private Button editButton;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_hospital);
    }
}