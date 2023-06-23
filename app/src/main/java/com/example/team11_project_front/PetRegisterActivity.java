package com.example.team11_project_front;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.team11_project_front.API.addPetApi;
import com.example.team11_project_front.Data.AddPetRequest;
import com.example.team11_project_front.Data.AddPetResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetRegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityResultLauncher<Intent> resultLauncher;
    private EditText petNameEdit, petBirthEdit;
    private Button aButton,bButton,cButton,dButton;
    private int genderButton = 0, speciesButton=0;
    private ImageView backBtn;
    private Button addPetBtn;
    private RetrofitClient retrofitClient;
    private addPetApi addPetApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_register);

        backBtn = (ImageView) findViewById(R.id.petBackBtn);
        petNameEdit = (EditText) findViewById(R.id.petNameEdit);
        petBirthEdit = (EditText) findViewById(R.id.petBirthEdit);
        aButton = findViewById(R.id.aButton);
        bButton = findViewById(R.id.bButton);
        cButton = findViewById(R.id.cButton);
        dButton = findViewById(R.id.dButton);
        addPetBtn = (Button) findViewById(R.id.addPetBtn);

        // 0: 버튼 없음, 1: aButton, 2: bButton

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

        backBtn.setOnClickListener(this);
        addPetBtn.setOnClickListener(this);

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
        imm.hideSoftInputFromWindow(petNameEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(petBirthEdit.getWindowToken(), 0);

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
        if (id == R.id.petBackBtn) {
            onBackPressed();
        } else if (id == R.id.addPetBtn) {

            addPet();
        }
    }

    void addPet(){
        String email = getPreferenceString("email");
        String petName = petNameEdit.getText().toString();
        String petBirth = petBirthEdit.getText().toString();
        String petGender = "";
        String petSpecies= "";

// clickedButton 1번이면 남자, 2번이면 여자. 0번이면 넘어가면 안됨.
        if(genderButton == 1) {
            petGender = "M";
        }
        else if(genderButton == 2) {
            petGender = "F";
        }
        else {
            Toast.makeText(PetRegisterActivity.this, "성별이 제대로 이루어지지 않습니다.", Toast.LENGTH_LONG).show();
        }


        if(speciesButton == 1) {
            petSpecies = "Dog";
        }
        else if(speciesButton == 2) {
            petSpecies = "Cat";
        }
        else {
            Toast.makeText(PetRegisterActivity.this, "종이 제대로 이루어지지 않았습니다.", Toast.LENGTH_LONG).show();
        }


        AddPetRequest addPetRequest = new AddPetRequest(email, petName, petBirth, petGender, petSpecies);

        retrofitClient = RetrofitClient.getInstance();
        addPetApi addPetApi = RetrofitClient.getRetrofitAddPetInterface();

        addPetApi.getAddPetResponse("Bearer " + getPreferenceString("acessToken"), addPetRequest).enqueue(new Callback<AddPetResponse>() {
            @Override
            public void onResponse(Call<AddPetResponse> call, Response<AddPetResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PetRegisterActivity.this, "추가되었습니다.", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else{
                    Toast.makeText(PetRegisterActivity.this, "잘못된 동물 정보입니다. <error2>", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddPetResponse> call, Throwable t) {
                Toast.makeText(PetRegisterActivity.this, "잘못된 동물 정보입니다.<error3>", Toast.LENGTH_LONG).show();
            }
        });
    }

    // 내부 저장소에 저장된 데이터 가져오기
    public String getPreferenceString(String key) {
        SharedPreferences pref = getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        return pref.getString(key, "");
    }
}