package com.example.team11_project_front;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team11_project_front.Data.ChangeHospitalRequest;
import com.example.team11_project_front.Data.ChangeHospitalResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeHospitalActivity extends AppCompatActivity {

// 사용할 레이어 선언
    private String HID;
    private EditText HospitalName;
    private EditText HospitalTel;
    private EditText HospitalLoc;
    private EditText HospitalIntro;

    private Button editButton;
    private ImageView backBtn;

    private RetrofitClient retrofitClient;
    private com.example.team11_project_front.API.changeHospitalApi changeHospitalApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_hospital);


        Intent intent = getIntent();
        HID = intent.getStringExtra("id");
        String Hna = intent.getStringExtra("name");
        String Hte = intent.getStringExtra("tel");
        String Hlo = intent.getStringExtra("address");
        String Hin = intent.getStringExtra("introduction");

        HospitalName = findViewById(R.id.HospitalName);
        HospitalTel = findViewById(R.id.HospitalTel);
        HospitalIntro = findViewById(R.id.HospitalIntro);
        HospitalLoc = findViewById(R.id.HospitalLoc);
        editButton = findViewById((R.id.editButton));
        backBtn = findViewById((R.id.petBackBtn));


        HospitalName.setHint(Hna);
        HospitalTel.setHint(Hte);
        HospitalLoc.setHint(Hlo);
        HospitalIntro.setHint(Hin);

        int hintColor = Color.GRAY; // 원하는 색상으로 변경해주세요
        HospitalName.setHintTextColor(hintColor);
        HospitalTel.setHintTextColor(hintColor);
        HospitalLoc.setHintTextColor(hintColor);
        HospitalIntro.setHintTextColor(hintColor);





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

        String HosId = HID;
        String HosName = HospitalName.getText().toString();
        String HosTel = HospitalTel.getText().toString();
        String HosLoc = HospitalLoc.getText().toString();
        String HosIntro = HospitalIntro.getText().toString();




        //// 통신 보내는 부분

        ChangeHospitalRequest changeHospitalRequest = new ChangeHospitalRequest(HosId, HosName, HosLoc, HosTel, HosIntro);

        retrofitClient = RetrofitClient.getInstance();
        com.example.team11_project_front.API.changeHospitalApi changeHospitalApi = RetrofitClient.getRetrofitChangeHospitalInterface();

        changeHospitalApi.getChangeHospitalResponse("Bearer " + getPreferenceString("acessToken"), HID, changeHospitalRequest).enqueue(new Callback<ChangeHospitalResponse>() {

            @Override
            public void onResponse(Call<ChangeHospitalResponse> call, Response<ChangeHospitalResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChangeHospitalActivity.this, "수정되었습니다.", Toast.LENGTH_LONG).show();


                    setPreference("hos_name", HID);
                    setPreference("hos_name", HosName);
                    setPreference("hos_address", HosLoc);
                    setPreference("hos_officenumber", HosTel);
                    setPreference("hos_introduction", HosIntro);

                    onBackPressed();
                } else {
                    Toast.makeText(ChangeHospitalActivity.this, "수정하려면 데이터가 더 필요합니다.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ChangeHospitalResponse> call, Throwable t) {
                Toast.makeText(ChangeHospitalActivity.this, "잘못된 동물 정보입니다.", Toast.LENGTH_LONG).show();
            }
        });

        ////




    }

    public String getPreferenceString(String key) {
        SharedPreferences pref = getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        return pref.getString(key, "");
    }

    public void setPreference(String key, String value){
        SharedPreferences pref = getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }
}