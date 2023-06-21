package com.example.team11_project_front;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.net.Uri;
import android.provider.MediaStore;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class SkinDiagnosisActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView placeholder_image;
    private Uri selectedImageUri;

    private androidx.appcompat.widget.AppCompatButton btn_select_pic;
    private androidx.appcompat.widget.AppCompatButton btn_take_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_diagnosis);
        Intent intent = getIntent();

        placeholder_image = findViewById(R.id.placeholder_image);
        btn_select_pic = findViewById(R.id.btn_select_pic);
        btn_take_pic = findViewById(R.id.btn_take_pic);
        ImageView nextBtn = findViewById(R.id.nextBtn);
        ImageView backBtn = findViewById(R.id.backBtn);

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
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImageUri != null) {
                    // Toast.makeText(SkinDiagnosisActivity.this, "Image URI: " + selectedImageUri.toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SkinDiagnosisActivity.this, AnswerByGptActivity.class);
                    intent.putExtra("selectedImageUri", selectedImageUri.toString());
                    startActivity(intent);
                // 이미지 선택되지 않았을 때 nextBtn 클릭하여 다음 activity로 넘어가지 못함
                } else {
                    Toast.makeText(SkinDiagnosisActivity.this, "사진을 등록해주세요.", Toast.LENGTH_SHORT).show();
                }
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            // 이미지 선택 완료
//            Uri imageUri = data.getData();
//            // 선택한 이미지 데이터 처리
//            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
//        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
//            // 사진 촬영
//            Bundle extras = data.getExtras();
//            if (extras != null && extras.containsKey("data")) {
//                Uri imageUri = getImageUriFromCameraOutput(extras);
//                // 촬영한 이미지 데이터 처리
//                Toast.makeText(this, "Image captured", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private Uri getImageUriFromCameraOutput(Bundle extras) {
//        Uri imageUri = null;
//        Object imageObj = extras.get("data");
//        if (imageObj instanceof Uri) {
//            imageUri = (Uri) imageObj;
//        } else if (imageObj instanceof Bitmap) {
//            Bitmap imageBitmap = (Bitmap) imageObj;
//            imageUri = saveImageBitmap(imageBitmap);
//        }
//        return imageUri;
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            // 이미지 선택 완료
//            selectedImageUri = data.getData();
//            placeholder_image.setImageURI(selectedImageUri);
//            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
//        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
//            // 사진 촬영
//            Bundle extras = data.getExtras();
//            if (extras != null && extras.containsKey("data")) {
//                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                selectedImageUri = saveImageBitmap(imageBitmap);
//                placeholder_image.setImageBitmap(imageBitmap);
//                Toast.makeText(this, "Image captured", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
        // 이미지 선택 완료
        selectedImageUri = data.getData();
        // Log.d("SelectedImageUri", selectedImageUri.toString()); // 로그 출력
        placeholder_image.setImageURI(selectedImageUri);
        placeholder_image.setVisibility(View.VISIBLE); // Make sure the placeholder image is visible
        Toast.makeText(this, "이미지가 등록되었습니다.", Toast.LENGTH_SHORT).show();

        // Update the positioning of the selected image to center it
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) findViewById(R.id.activity_skin_diagnosis)); // Replace with the ID of your ConstraintLayout
        constraintSet.connect(placeholder_image.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        constraintSet.connect(placeholder_image.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        constraintSet.connect(placeholder_image.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(placeholder_image.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        constraintSet.applyTo((ConstraintLayout) findViewById(R.id.activity_skin_diagnosis)); // Replace with the ID of your ConstraintLayout
    } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
        // 사진 촬영
        Bundle extras = data.getExtras();
        if (extras != null && extras.containsKey("data")) {
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            selectedImageUri = saveImageBitmap(imageBitmap);
            placeholder_image.setImageBitmap(imageBitmap);
            placeholder_image.setVisibility(View.VISIBLE); // Make sure the placeholder image is visible
            Toast.makeText(this, "Image captured", Toast.LENGTH_SHORT).show();

            // Update the positioning of the selected image to center it
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone((ConstraintLayout) findViewById(R.id.activity_skin_diagnosis)); // Replace with the ID of your ConstraintLayout
            constraintSet.connect(placeholder_image.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
            constraintSet.connect(placeholder_image.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
            constraintSet.connect(placeholder_image.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
            constraintSet.connect(placeholder_image.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
            constraintSet.applyTo((ConstraintLayout) findViewById(R.id.activity_skin_diagnosis)); // Replace with the ID of your ConstraintLayout
        }
    }
}


    private Uri saveImageBitmap(Bitmap imageBitmap) {
        // 이미지를 파일로 저장, 해당 파일의 Uri를 반환
        // e.g. 이미지 파일을 앱의 내부 저장소 또는 외부 저장소에 저장
        return null;
    }
}
