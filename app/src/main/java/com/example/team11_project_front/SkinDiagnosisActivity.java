package com.example.team11_project_front;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.telecom.InCallService;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.net.Uri;
import android.provider.MediaStore;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SkinDiagnosisActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView placeholder_image;
    private Uri selectedImageUri;

    private androidx.appcompat.widget.AppCompatButton btn_select_pic;
    private androidx.appcompat.widget.AppCompatButton btn_take_pic;
    private androidx.appcompat.widget.AppCompatButton btn_register_pic;
    private Spinner spinner;
    private Camera camera;
    private InCallService.VideoCall preview;

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

        spinner = findViewById(R.id.spinner);

        // Spinner에 표시할 항목 배열
        String[] petOptions = {"라옹", "레오", "라임"};

        // ArrayAdapter를 사용하여 항목 배열을 Spinner에 연결
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        // 사진 선택
        btn_select_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                Animation anim = AnimationUtils.loadAnimation(SkinDiagnosisActivity.this, R.animator.button_scale);
                btn_select_pic.startAnimation(anim);
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
                if (selectedImageUri != null) {
                    // Toast.makeText(SkinDiagnosisActivity.this, "Image URI: " + selectedImageUri.toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SkinDiagnosisActivity.this, AnswerByGptActivity.class);
                    intent.putExtra("selectedImageUri", selectedImageUri.toString());
                    startActivity(intent);
                    // 이미지 선택되지 않았을 때 nextBtn 클릭하여 다음 activity로 넘어가지 못함
                } else {
                    Snackbar.make(v, "사진을 등록해주세요.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        // 이 부분으로 onClick 메소드 실행시 버튼 클릭하면 튕겨서 주석처리 했습니다
        // 사진 등록 버튼 클릭 (다음 activity로 intent 전달)
//        androidx.appcompat.widget.AppCompatButton btn_register_pic = findViewById(R.id.btn_register_pic);
//        btn_register_pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (selectedImageUri != null) {
//                    Bitmap bitmap = null;
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                    byte[] byteArray = stream.toByteArray();
//
//                    Intent intent = new Intent(SkinDiagnosisActivity.this, AnswerByGptActivity.class);
//                    intent.putExtra("image", byteArray);
//
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(SkinDiagnosisActivity.this, "이미지를 선택하세요.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {
            // Toast.makeText(this, "Camera app not found", Toast.LENGTH_SHORT).show();
            Snackbar.make(placeholder_image, "카메라를 사용할 수 없습니다.", Snackbar.LENGTH_SHORT).show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            placeholder_image.setImageURI(selectedImageUri);
        }
    }

    private Uri saveImageBitmap(Bitmap imageBitmap) {
        // 이미지를 파일로 저장, 해당 파일의 Uri를 반환
        // e.g. 이미지 파일을 앱의 내부 저장소 또는 외부 저장소에 저장
        return null;
    }
}
