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
import android.graphics.ImageDecoder;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.team11_project_front.API.picturePostApi;
import com.example.team11_project_front.Data.PicturePostRequest;
import com.example.team11_project_front.Data.PictureResponse;
import com.example.team11_project_front.QnA.QnaFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();


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
        TextView tv_diseaseName = (TextView) findViewById(R.id.diseaseNameText);
        TextView tv_date = (TextView) findViewById(R.id.diagonistDateText);
        TextView tv_score = (TextView) findViewById(R.id.diagonistScoreText);
        TextView tv_gpt = (TextView) findViewById(R.id.explanationGPT);

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
                    String res_d = res.getModel_result(); // disease name
                    String res_p = res.getModel_conf(); // probability
                    String res_t = res.getCreated_at().split("T")[0];

                    tv_diseaseName.setText(res_d);
                    tv_score.setText(res_p);

                    tv_date.setText(res_t);
                    String question = "반려견 피부질환 AI model이 " + res_p + "%의 Confidence로 " + res_d + "을/를 예상하고있어.\n이 병명에 대해서 간단한 설명을 해줘.";

                    JSONArray arr = new JSONArray();
                    JSONObject object = new JSONObject();
                    try{
                        object.put("model", "text-davinci-003");
                        object.put("prompt", question);
                        object.put("temperature", 0.4);
                        object.put("max_tokens", 1000);
                        object.put("top_p", 1);
                        object.put("frequency_penalty", 0);
                        object.put("presence_penalty", 0);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                    RequestBody body = RequestBody.create(MediaType.get("application/json"), object.toString());
                    Request request = new Request.Builder()
                            .url("https://api.openai.com/v1/completions")
                            .header("Authorization", "Bearer " + "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")
                            .post(body)
                            .build();
                    client.newCall(request).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                            if(response.isSuccessful()){
                                JSONObject jsonObject = null;
                                try{
                                    jsonObject = new JSONObject(response.body().string());
                                    JSONArray jsonArray = jsonObject.getJSONArray("choices");
                                    String result = jsonArray.getJSONObject(0).getString("text");
                                    tv_gpt.setText(result);
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }else{

                            }
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<PictureResponse> call, Throwable t) {
                Log.e("dia", t.toString());
            }
        });



        ImageView backBtn = findViewById(R.id.backBtn);
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
