//package com.example.team11_project_front;
//
//import android.content.Intent;
//import android.graphics.Rect;
//import android.os.Bundle;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.ImageView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class AnswerByGptActivity extends AppCompatActivity {
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_answer_by_gpt);
//
//
//        ImageView backBtn = findViewById(R.id.backBtn);
//
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AnswerByGptActivity.this, SkinDiagnosisActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        View focusView = getCurrentFocus();
//        if (focusView != null) {
//            Rect rect = new Rect();
//            focusView.getGlobalVisibleRect(rect);
//            int x = (int) ev.getX(), y = (int) ev.getY();
//            if (!rect.contains(x, y)) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                if (imm != null)
//                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
//                focusView.clearFocus();
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//}


package com.example.team11_project_front;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.team11_project_front.API.picturePostApi;
import com.example.team11_project_front.Data.PicturePostRequest;
import com.example.team11_project_front.Data.PictureResponse;
import com.example.team11_project_front.QnA.QnaFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnswerByGptActivity extends AppCompatActivity {

    private Dialog questionDialog, gptDialog;
    private EditText questionEditText,TitleEditText;

    private TextView diseaseNameText, diagonistDateText,proabilityText;
    private Button btn_ai_diagnosis;

    private int flag = -1;

    @SuppressLint("WrongThread")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_by_gpt);

        // 이미지 URI
        String path = getIntent().getStringExtra("image");
        Bitmap bitmap;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.parse(path));
            try {
                bitmap = ImageDecoder.decodeBitmap(source);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else{
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        ImageView imageView = findViewById(R.id.imageView);
        Bitmap scaled_bitmap = bitmap.createScaledBitmap(bitmap, 200, 200, false);
        imageView.setImageBitmap(scaled_bitmap);

        Cursor cursor = getContentResolver().query(Uri.parse(path), null, null, null, null);
        cursor.moveToNext();
        int idx = cursor.getColumnIndex("_data");
        File file = new File(cursor.getString(idx));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);

        String pet_id = getIntent().getStringExtra("pet_id");
        MultipartBody.Part textPart = MultipartBody.Part.createFormData("pet_id", pet_id);

        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        picturePostApi picturePostApi = retrofitClient.getRetrofitPostPictureInterface();
        picturePostApi.getPictureResponse("Bearer " + getPreferenceString("acessToken"), filePart, textPart).enqueue(new Callback<PictureResponse>() {
            @Override
            public void onResponse(Call<PictureResponse> call, Response<PictureResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    PictureResponse res = response.body();
                    String res_d = res.getModel_result();
                    String res_t = res.getCreated_at().split("T")[0];

                    TextView tv_diseaseName = (TextView) findViewById(R.id.diseaseNameText);
                    tv_diseaseName.setText(res_d);
                    TextView tv_date = (TextView) findViewById(R.id.diagonistDateText);
                    tv_date.setText(res_t);
                }
            }
            @Override
            public void onFailure(Call<PictureResponse> call, Throwable t) {
                Log.e("dia", t.toString());
            }
        });



        ImageView backBtn = findViewById(R.id.backBtn);

        diseaseNameText = findViewById(R.id.diseaseNameText);
        diagonistDateText = findViewById(R.id.diagonistDateText);
        proabilityText = findViewById(R.id.ProabilityText);
        btn_ai_diagnosis = findViewById(R.id.btn_ai_diagnosis);


        // 사진은 전 단계에 있던 사진 가져오면 된다고 본다면, 병명, 검사일자,GPT 가져오는 장소, 그리고 여러종류의 id(user_id,진단_id) 가져와야한다.

        diseaseNameText.setText("황소병");
        diagonistDateText.setText("2023-06-26");
        proabilityText.setText("95.53%");



        //
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnswerByGptActivity.this, SkinDiagnosisActivity.class);
                startActivity(intent);
            }
        });

        btn_ai_diagnosis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showGPTDialog();

            }
        });



        // Fragment 전환을 위한 코드 추가
        findViewById(R.id.btn_post_qna).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuestionDialog();
            }
        });

        findViewById(R.id.btn_return_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // HomeFragment 인스턴스 생성
                Intent intent = new Intent(AnswerByGptActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    void showGPTDialog() {
        Dialog gptDialog = new Dialog(this);
        gptDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gptDialog.setContentView(R.layout.gpt_dialog);
        gptDialog.setCanceledOnTouchOutside(false);

        TextView dialogTitle = gptDialog.findViewById(R.id.dialogTitle);
        TextView dialogContent = gptDialog.findViewById(R.id.dialogContent);
        Button closeButton = gptDialog.findViewById(R.id.closeButton);
        dialogTitle.setText("AI 진단");



        // gpt 부분
        dialogContent.setText("GPT를 통한 이 질환은 다음과 같은 진단을 내릴 수 있습니다.");






        //
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gptDialog.dismiss();
            }
        });

        gptDialog.show();
    }


    //
    void showQuestionDialog() {

        questionDialog = new Dialog(this);
        questionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        questionDialog.setContentView(R.layout.question_dialog);
        questionDialog.setCanceledOnTouchOutside(false);

        TitleEditText = questionDialog.findViewById(R.id.question_title_edit_text);
        questionEditText = questionDialog.findViewById(R.id.question_content_edit_text);
        Button yesButton = questionDialog.findViewById(R.id.yes_button);
        Button noButton = questionDialog.findViewById(R.id.no_button);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = TitleEditText.getText().toString();
                String question = questionEditText.getText().toString();
                registerQuestion(title,question);
                flag = 1;
                dismissQuestionDialog();
                if (flag == 1) {
                    // QnaFragment 인스턴스 생성
                    QnaFragment qnaFragment = new QnaFragment();

                    // FragmentManager: AnswerByGptActivity -> QnaFragment
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_qna, qnaFragment);
                    fragmentTransaction.commit();
                }

            }
        });

        // 아니오 버튼 클릭 시
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissQuestionDialog();
                flag = -1;
            }
        });

        questionDialog.show();




        // QnaFragment 인스턴스 생성

    }

    private void registerQuestion(String title, String question) {
        // 질문 등록 로직을 수행하는 부분
        // 여기서는 간단히 토스트 메시지로 질문을 보여줍니다.

        Toast.makeText(this, "제목이" + title + "질문이 등록되었습니다: " + question, Toast.LENGTH_SHORT).show();






    }

    private void dismissQuestionDialog() {

        if (questionDialog != null && questionDialog.isShowing()) {
            questionEditText.setText(""); // EditText 내용 초기화
            questionDialog.dismiss();
        }
    }


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
}
