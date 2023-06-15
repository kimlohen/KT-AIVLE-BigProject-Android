package com.example.team11_project_front;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.team11_project_front.API.addPetApi;
import com.example.team11_project_front.Data.AddPetRequest;
import com.example.team11_project_front.Data.AddPetResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetRegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityResultLauncher<Intent> resultLauncher;
    private EditText petNameEdit, petBirthEdit, petGenderEdit, petSpeciesEdit;
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
        petGenderEdit = (EditText) findViewById(R.id.petGenderEdit);
        petSpeciesEdit = (EditText) findViewById(R.id.petSpeciesEdit);
        addPetBtn = (Button) findViewById(R.id.addPetBtn);

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
        imm.hideSoftInputFromWindow(petGenderEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(petSpeciesEdit.getWindowToken(), 0);
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
        String petGender = petGenderEdit.getText().toString();
        String petSpecies = petSpeciesEdit.getText().toString();

        AddPetRequest addPetRequest = new AddPetRequest(email, petName, petBirth, petGender, petSpecies);

        retrofitClient = RetrofitClient.getInstance();
        addPetApi addPetApi = RetrofitClient.getRetrofitAddPetInterface();

        addPetApi.getAddPetResponse(addPetRequest).enqueue(new Callback<AddPetResponse>() {
            @Override
            public void onResponse(Call<AddPetResponse> call, Response<AddPetResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PetRegisterActivity.this, "추가되었습니다.", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }else{
                    Toast.makeText(PetRegisterActivity.this, "잘못된 동물 정보입니다.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddPetResponse> call, Throwable t) {
                Toast.makeText(PetRegisterActivity.this, "잘못된 동물 정보입니다.", Toast.LENGTH_LONG).show();
            }
        });
    }

    // 내부 저장소에 저장된 데이터 가져오기
    public String getPreferenceString(String key) {
        SharedPreferences pref = getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        return pref.getString(key, "");
    }
}