package com.example.team11_project_front;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.telecom.InCallService;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team11_project_front.API.petlistApi;
import com.example.team11_project_front.Data.PetlistResponse;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkinDiagnosisActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView placeholder_image;
    private Uri selectedImageUri;

    private androidx.appcompat.widget.AppCompatButton btn_select_pic;
    private androidx.appcompat.widget.AppCompatButton btn_take_pic;
    private androidx.appcompat.widget.AppCompatButton btn_register_pic;
    private Spinner spinner;
    private InCallService.VideoCall preview;
    Bitmap bitmap;
    private boolean havePicture = false;
    private String petId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_diagnosis);
        Intent intent = getIntent();

        placeholder_image = findViewById(R.id.placeholder_image);
        btn_select_pic = findViewById(R.id.btn_select_pic);
        btn_take_pic = findViewById(R.id.btn_take_pic);
        btn_register_pic = findViewById(R.id.btn_register_pic);
        ImageView backBtn = findViewById(R.id.backBtn);
        spinner = (Spinner) findViewById(R.id.diagonsisSpin);

        // Spinner에 표시할 항목 배열

        ArrayList petOptions = new ArrayList<>();
        ArrayList petIdx = new ArrayList<>();
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        petlistApi petlistApi = retrofitClient.getRetrofitPetlistInterface();

        petlistApi.getPetlistResponse("Bearer " + getPreferenceString("acessToken")).enqueue(new Callback<ArrayList<PetlistResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PetlistResponse>> call, Response<ArrayList<PetlistResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<PetlistResponse> petlistResponses = response.body();
                    for (PetlistResponse petlistResponse : petlistResponses) {
                        String id = petlistResponse.getId();
                        String petName = petlistResponse.getName();
                        petOptions.add(petName);
                        petIdx.add(id);
                    }
                    // ArrayAdapter를 사용하여 항목 배열을 Spinner에 연결
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SkinDiagnosisActivity.this, android.R.layout.simple_spinner_dropdown_item, petOptions);
                    spinner.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            public void onFailure(Call<ArrayList<PetlistResponse>> call, Throwable t) {
                Toast.makeText(SkinDiagnosisActivity.this, "동물 정보를 제대로 가져오지 못 했습니다.", Toast.LENGTH_LONG).show();
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                petId = petIdx.get(position)+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                petId = petIdx.get(0)+"";
            }
        });

        // 사진 선택
        btn_select_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // 사진 촬영
        btn_take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onBackPressed(); }
        });


        // 사진 등록
        btn_register_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (havePicture) {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "IMG_" + System.currentTimeMillis(), null);
                    Intent intent = new Intent(SkinDiagnosisActivity.this, AnswerByGptActivity.class);
                    intent.putExtra("image", path);
                    intent.putExtra("pet_id", petId);
                    startActivity(intent);
                    // 이미지 선택되지 않았을 때 nextBtn 클릭하여 다음 activity로 넘어가지 못함
                } else {
                    Snackbar.make(v, "사진을 등록해주세요.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }


    ActivityResultLauncher<Intent> activityResultPicture = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        Bundle extras = result.getData().getExtras();
                        bitmap = (Bitmap) extras.get("data");
                        bitmap.createScaledBitmap(bitmap, 400, 400, false);
                        placeholder_image.setImageBitmap(bitmap);
                        havePicture = true;
                    }
                }
            });
    ActivityResultLauncher<Intent> activityResultStorage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        Uri uri = result.getData().getData();
                        try{
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            bitmap.createScaledBitmap(bitmap, 400, 400, false);
                            placeholder_image.setImageBitmap(bitmap);
                            havePicture = true;
                        }catch(FileNotFoundException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultStorage.launch(intent);
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultPicture.launch(intent);
    }

    // 데이터를 내부 저장소에 저장하기
    public void setPreference(String key, String value){
        SharedPreferences pref = getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }
    // 내부 저장소에 저장된 데이터 가져오기
    public String getPreferenceString(String key) {
        SharedPreferences pref = getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        return pref.getString(key, "");
    }

    private static String[] Add(String[] originArray, String Val) {
        // 순서 1. (원본 배열의 크기 + 1)를 크기를 가지는 배열을 생성
        String[] newArray = new String[originArray.length + 1];

        // 순서 2. 새로운 배열에 값을 순차적으로 할당
        for(int index = 0; index < originArray.length; index++) {
            newArray[index] = originArray[index];
        }

        // 순서 3. 새로운 배열의 마지막 위치에 추가하려는 값을 할당
        newArray[originArray.length] = Val;

        // 순서 4. 새로운 배열을 반환
        return newArray;
    }

}
