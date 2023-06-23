package com.example.team11_project_front;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeHospitalActivity extends AppCompatActivity {

// 사용할 레이어 선언
    private EditText HospitalName;
    private EditText HospitalTel;
    private EditText HospitalLoc;
    private EditText HospitalIntro;

    private Button editButton;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_hospital);

        HospitalName = findViewById(R.id.HospitalName);
        HospitalTel = findViewById(R.id.HospitalTel);
        HospitalIntro = findViewById(R.id.HospitalIntro);
        HospitalLoc = findViewById(R.id.HospitalLoc);

        String Hname = "강의사동물병원";
        String HTel = "031-2233-3378";
        String HLoc = "경기도 수원특별시 영통구";
        String HIntro = "구경하고 가세요~";


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHos();
            }
        });




    }

    void changeHos() {

        String HosName = HospitalName.getText().toString();
        String HosTel = HospitalTel.getText().toString();
        String HosLoc = HospitalLoc.getText().toString();
        String HosIntro = HospitalIntro.getText().toString();
        
        //// 통신 보내는 부분
        
        
        
        
        
        
        ////



    }
}