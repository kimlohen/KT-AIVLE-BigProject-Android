package com.example.team11_project_front;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Toast;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

public class SkinDiagnosisActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int PICK_IMAGE_REQUEST = 1;

    private androidx.appcompat.widget.AppCompatButton btn_select_pic;
    private androidx.appcompat.widget.AppCompatButton btn_take_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_diagnosis);

        btn_select_pic = findViewById(R.id.btn_select_pic);
        btn_take_pic = findViewById(R.id.btn_take_pic);

        btn_select_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btn_take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
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
            Toast.makeText(this, "Camera app not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // 이미지 선택 완료
            Uri imageUri = data.getData();
            // 선택한 이미지 데이터 처리
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            // 사진 촬영
            Bundle extras = data.getExtras();
            if (extras != null && extras.containsKey("data")) {
                Uri imageUri = getImageUriFromCameraOutput(extras);
                // 촬영한 이미지 데이터 처리
                Toast.makeText(this, "Image captured", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Uri getImageUriFromCameraOutput(Bundle extras) {
        Uri imageUri = null;
        Object imageObj = extras.get("data");
        if (imageObj instanceof Uri) {
            imageUri = (Uri) imageObj;
        } else if (imageObj instanceof Bitmap) {
            Bitmap imageBitmap = (Bitmap) imageObj;
            imageUri = saveImageBitmap(imageBitmap);
        }
        return imageUri;
    }

    private Uri saveImageBitmap(Bitmap imageBitmap) {
        // 이미지를 파일로 저장, 해당 파일의 Uri를 반환
        // e.g. 이미지 파일을 앱의 내부 저장소 또는 외부 저장소에 저장
        return null;
    }
}
