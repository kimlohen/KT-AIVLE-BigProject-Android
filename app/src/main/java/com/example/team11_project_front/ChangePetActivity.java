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

import com.example.team11_project_front.API.changePetApi;
import com.example.team11_project_front.Data.ChangePetRequest;
import com.example.team11_project_front.Data.ChangePetResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePetActivity extends AppCompatActivity {

    private EditText editTextText;
    private EditText editTextText2;
    private Button aButton,bButton,cButton,dButton;

    private String PID;
    private Button editButton;
    private ImageView backBtn;

    private int genderButton = 0 , speciesButton = 0;



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

        editTextText = findViewById(R.id.editTextText); // 수정된 부분
        editTextText2 = findViewById(R.id.editTextText2); // 수정된 부분
        aButton = findViewById(R.id.aButton); // 수정된 부분
        bButton = findViewById(R.id.bButton); // 수정된 부분
        cButton = findViewById(R.id.cButton); // 수정된 부분
        dButton = findViewById(R.id.dButton); // 수정된 부분


        backBtn = (ImageView) findViewById(R.id.petBackBtn);
        editButton = (Button) findViewById(R.id.editButton);

        //list.set(index, newValue);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aButton.setBackgroundColor(Color.rgb(124,252,0));
                bButton.setBackgroundColor(Color.TRANSPARENT);
                genderButton = 1;
            }
        });

        bButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bButton.setBackgroundColor(Color.rgb(124,252,0));
                aButton.setBackgroundColor(Color.TRANSPARENT);
                genderButton = 2;
            }
        });


        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cButton.setBackgroundColor(Color.rgb(255,192,203));
                dButton.setBackgroundColor(Color.TRANSPARENT);
                speciesButton = 1;
            }
        });

        dButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cButton.setBackgroundColor(Color.TRANSPARENT);
                dButton.setBackgroundColor(Color.rgb(255,192,203));
                speciesButton = 2;
            }
        });


        List<Integer> list = new ArrayList<>(Collections.nCopies(4, 0));
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_size = editTextText.getText().toString();
                if (name_size.length() > 0) {
                    list.set(0, 1);
                }
                else {
                    list.set(0,0);
                    Toast.makeText(ChangePetActivity.this, "이름을 작성해주세요.", Toast.LENGTH_LONG).show();
                }

                String date_format = editTextText2.getText().toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                try {
                    LocalDate.parse(date_format, formatter);
                    list.set(1,1);
                } catch (DateTimeParseException e) {
                    Toast.makeText(ChangePetActivity.this, "날짜형식이 올바르지 않습니다.", Toast.LENGTH_LONG).show();
                    list.set(1,0);
                }

                if ( (genderButton == 1) || (genderButton == 2) ) {
                    list.set(2, 1);
                }
                else {
                    list.set(2,0);
                    Toast.makeText(ChangePetActivity.this, "성별을 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                }
                if ((speciesButton == 1 ) || (speciesButton == 2)) {
                    list.set(3, 1);
                }
                else {
                    list.set(3,0);
                    Toast.makeText(ChangePetActivity.this, "종을 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                }
                int sum = 0;
                for (int num : list) {
                    sum += num;
                }
                if( sum == 4) {
                    changePet();
                }
            }
        });

// 힌트 텍스트 설정
        int preciousgender,preciousspecies;
        editTextText.setHint(name);
        editTextText2.setHint(birth);
        if (gender == "M") {
            aButton.setBackgroundColor(Color.rgb(124,252,0));
            bButton.setBackgroundColor(Color.TRANSPARENT);
            genderButton = 1;

        }
        else if (gender == "F") {
            bButton.setBackgroundColor(Color.rgb(124,252,0));
            aButton.setBackgroundColor(Color.TRANSPARENT);
            genderButton = 2;
        }
        if (species == "Cat" || species == "cat") {
            cButton.setBackgroundColor(Color.rgb(255,192,203));
            dButton.setBackgroundColor(Color.TRANSPARENT);
            speciesButton = 1;

        }
        else if (species == "Dog" || species == "dog") {
            cButton.setBackgroundColor(Color.TRANSPARENT);
            dButton.setBackgroundColor(Color.rgb(255,192,203));
            speciesButton = 2;

        }

// 색상 설정
        int hintColor = Color.GRAY; // 원하는 색상으로 변경해주세요
        editTextText.setHintTextColor(hintColor);
        editTextText2.setHintTextColor(hintColor);


        // 제출을 눌렀을 때 edittext에 적은 text 내용들 가져와서 DB에 보내기

        PID = id;



    }

    void changePet(){

        String petId = PID;
        String petName = editTextText.getText().toString();
        String petBirth = editTextText2.getText().toString();
        String petGender="";
        String petSpecies="";

        if(genderButton == 1) {
            petGender = "M";
        }
        else if(genderButton == 2) {
            petGender = "F";
        }
        else {
            Toast.makeText(ChangePetActivity.this, "성별이 제대로 이루어지지 않습니다.", Toast.LENGTH_LONG).show();
        }


        if(speciesButton == 1) {
            petSpecies = "Dog";
        }
        else if(speciesButton == 2) {
            petSpecies = "Cat";
        }
        else {
            Toast.makeText(ChangePetActivity.this, "종이 제대로 이루어지지 않았습니다.", Toast.LENGTH_LONG).show();
        }


        ChangePetRequest changePetRequest = new ChangePetRequest(petId, petName, petBirth, petGender, petSpecies);

        retrofitClient = RetrofitClient.getInstance();
        changePetApi changePetApi = RetrofitClient.getRetrofitChangePetInterface();

        changePetApi.getChangePetResponse("Bearer " + getPreferenceString("acessToken"),PID,changePetRequest).enqueue(new Callback<ChangePetResponse>() {
            @Override
            public void onResponse(Call<ChangePetResponse> call, Response<ChangePetResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ChangePetActivity.this, "수정되었습니다.", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else{
                    Toast.makeText(ChangePetActivity.this, "수정하려면 데이터가 더 필요합니다.", Toast.LENGTH_LONG).show();
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