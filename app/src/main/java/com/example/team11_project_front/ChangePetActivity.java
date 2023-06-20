package com.example.team11_project_front;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.team11_project_front.API.changePetApi;
import com.example.team11_project_front.Data.ChangePetRequest;
import com.example.team11_project_front.Data.ChangePetResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePetActivity extends AppCompatActivity {


    private EditText editTextText;
    private EditText editTextText2;
    private EditText editTextText3;
    private EditText editTextText4;

    private String PID;
    private Button editButton;
    private ImageView backBtn;


    private RetrofitClient retrofitClient;
    private com.example.team11_project_front.API.changePetApi changePetApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pet);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String gender = intent.getStringExtra("gender");
        String species = intent.getStringExtra("species");
        String birth = intent.getStringExtra("birth");

        EditText editText = findViewById(R.id.editTextText);
        EditText editText2 = findViewById(R.id.editTextText2);
        EditText editText3 = findViewById(R.id.editTextText3);
        EditText editText4 = findViewById(R.id.editTextText4);
        backBtn = (ImageView) findViewById(R.id.petBackBtn);
        editButton = (Button) findViewById(R.id.editButton);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePet();
            }
        });

// 힌트 텍스트 설정
        editText.setHint(name);
        editText.setHint(gender);
        editText.setHint(birth);
        editText.setHint(species);

// 색상 설정
        int hintColor = Color.BLACK; // 원하는 색상으로 변경해주세요
        editText.setHintTextColor(hintColor);
        editText2.setHintTextColor(hintColor);
        editText3.setHintTextColor(hintColor);
        editText4.setHintTextColor(hintColor);

        // 제출을 눌렀을 때 edittext에 적은 text 내용들 가져와서 DB에 보내기

        PID = id;



    }

    void changePet(){
        String email = getPreferenceString("email");
        String petId = PID;
        String petName = editTextText.getText().toString();
        String petBirth = editTextText2.getText().toString();
        String petGender = editTextText3.getText().toString();
        String petSpecies = editTextText4.getText().toString();

        ChangePetRequest changePetRequest = new ChangePetRequest(email, petName, petBirth, petGender, petSpecies);

        retrofitClient = RetrofitClient.getInstance();
        changePetApi changePetApi = RetrofitClient.getRetrofitChangePetInterface();

        changePetApi.getChangePetResponse(PID,changePetRequest).enqueue(new Callback<ChangePetResponse>() {
            @Override
            public void onResponse(Call<ChangePetResponse> call, Response<ChangePetResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ChangePetActivity.this, "수정되었습니다.", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else{
                    Toast.makeText(ChangePetActivity.this, "잘못된 동물 정보입니다.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ChangePetResponse> call, Throwable t) {
                Toast.makeText(ChangePetActivity.this, "잘못된 동물 정보입니다.", Toast.LENGTH_LONG).show();
            }
        });
    }

    // 내부 저장소에 저장된 데이터 가져오기
    public String getPreferenceString(String key) {
        SharedPreferences pref = getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        return pref.getString(key, "");
    }
}