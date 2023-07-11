package com.example.team11_project_front;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
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

import com.example.team11_project_front.API.gptApi;
import com.example.team11_project_front.API.picturePostApi;
import com.example.team11_project_front.API.refreshApi;
import com.example.team11_project_front.Data.AddQRequest;
import com.example.team11_project_front.Data.AddQResponse;
import com.example.team11_project_front.Data.GPTRequest;
import com.example.team11_project_front.Data.PictureResponse;
import com.example.team11_project_front.Data.RefreshRequest;
import com.example.team11_project_front.Data.RefreshResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
    private RetrofitClient retrofitClient;
    private com.example.team11_project_front.API.addQApi addQApi;
    private String PictureId;

    private int flag = -1;

    Bitmap bitmap;
    ImageView imageView;
    TextView tv_diseaseInfo, tv_date;
    Button btn_ai_diagnosis;
    boolean gpt_flag = false;
    String gptResult;
    boolean question_flag = false;
    String picId;
    private Timer timerCall, gptTimerCall;

    @SuppressLint("WrongThread")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_by_gpt);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        TimerTask timerTask = new TimerTask(){
           @Override
           public void run(){
               loading();
           }
        };
        TimerTask gptTimerTask = new TimerTask(){
            @Override
            public void run(){
                gptLoading();
            }
        };

        // 이미지 URI
        String path = getIntent().getStringExtra("image");
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

        imageView = findViewById(R.id.imageView);
        tv_diseaseInfo = (TextView) findViewById(R.id.diseaseNameText);
        tv_date = (TextView) findViewById(R.id.diagonistDateText);
        btn_ai_diagnosis = (Button) findViewById(R.id.btn_ai_diagnosis);

        timerCall = new Timer();
        timerCall.schedule(timerTask, 0, 1000);

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
                if (response.code() == 401) {
                    RefreshRequest refreshRequest = new RefreshRequest(getPreferenceString("refreshToken"));
                    refreshApi refreshApi = RetrofitClient.getRefreshInterface();
                    refreshApi.getRefreshResponse(refreshRequest).enqueue(new Callback<RefreshResponse>() {
                        @Override
                        public void onResponse(Call<RefreshResponse> call, Response<RefreshResponse> response) {
                            if(response.isSuccessful() && response.body() != null){
                                setPreference("acessToken", response.body().getAccessToken());
                                Toast.makeText(AnswerByGptActivity.this, "토큰이 만료되어 갱신하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(AnswerByGptActivity.this, "토큰 갱신에 실패하였습니다. 관리자에게 문의해주세요.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RefreshResponse> call, Throwable t) {
                            Toast.makeText(AnswerByGptActivity.this, "토큰 갱신에 실패하였습니다. 관리자에게 문의해주세요.", Toast.LENGTH_LONG).show();
                        }
                    });
                } else if(response.isSuccessful() && response.body() != null){
                    PictureResponse res = response.body();
                    timerCall.cancel();
                    String res_d = res.getModel_result(); // disease name
                    String res_p = res.getModel_conf(); // probability
                    String res_t = res.getCreated_at().split("T")[0];
                    picId = res.getId();
                    question_flag = true;

                    if(res_d.equals("무증상")){
                        tv_diseaseInfo.setTextColor(Color.parseColor("#008000"));
                    }else if(res_d.equals("미란, 궤양") || res_d.equals("결절, 종괴 ")){
                        tv_diseaseInfo.setTextColor(Color.parseColor("#DB4455"));
                    }else {
                        tv_diseaseInfo.setTextColor(Color.parseColor("#FC6500"));
                    }

                    if(Double.parseDouble(res_p) >= 80.0){
                        String diseaseInfo = "매우 높은 확률(" + res_p +"%)로\n" + "[" + res_d + "]이(가) 예상됨";
                        tv_diseaseInfo.setText(diseaseInfo);
                    }else if(Double.parseDouble(res_p) >= 60.0){
                        String diseaseInfo = "높은 확률(" + res_p +"%)로\n" +  "[" + res_d + "]이(가) 예상됨";
                        tv_diseaseInfo.setText(diseaseInfo);
                    }else if(Double.parseDouble(res_p) >= 40.0){
                        String diseaseInfo = "높지 않은 확률(" + res_p +"%)로\n" + "[" +  res_d + "]이(가) 예상됨";
                        tv_diseaseInfo.setText(diseaseInfo);
                    }else if(Double.parseDouble(res_p) >= 20.0){
                        String diseaseInfo = "낮은 확률(" + res_p +"%)로\n" + "[" + res_d + "]이(가) 예상됨";
                        tv_diseaseInfo.setText(diseaseInfo);
                    }else{
                        String diseaseInfo = "매우 낮은 확률(" + res_p +"%)로\n" + "[" +  res_d + "]이(가) 예상됨";
                        tv_diseaseInfo.setText(diseaseInfo);
                    }


                    tv_date.setText(res_t);

                    gptTimerCall = new Timer();
                    gptTimerCall.schedule(gptTimerTask, 0, 1000);

                    String question = "반려견 피부질환 AI model이 " + res_p + "%의 Confidence로 " + res_d + "을/를 예상하고있어.\n이 병명에 대해서 간단한 설명을 해줘.";

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


                    String gpt_key = "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";
//                    String gpt_key = "";
//                    try {
//                        ApplicationInfo ai = getApplicationContext().getPackageManager().getApplicationInfo(getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
//                        gpt_key = ai.metaData.get("gpt_key").toString();
//                    } catch (PackageManager.NameNotFoundException e) {
//                        throw new RuntimeException(e);
//                    }
                    Request request = new Request.Builder()
                            .url("https://api.openai.com/v1/completions")
                            .header("Authorization", "Bearer " + gpt_key)
                            .post(body)
                            .build();
                    client.newCall(request).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                            if(response.isSuccessful()){
                                JSONObject jsonObject = null;
                                try{
                                    jsonObject = new JSONObject(response.body().string());
                                    JSONArray jsonArray = jsonObject.getJSONArray("choices");
                                    gptResult = jsonArray.getJSONObject(0).getString("text");
                                    gpt_flag = true;
                                    GPTRequest gptRequest = new GPTRequest(gptResult);
                                    gptApi gptApi = RetrofitClient.getRetrofitGPTInterface();
                                    gptApi.gptResponse("Bearer " + getPreferenceString("acessToken"), picId, gptRequest).enqueue(new Callback<PictureResponse>() {
                                        @Override
                                        public void onResponse(Call<PictureResponse> call, Response<PictureResponse> response) {
                                            if (response.code() == 401) {
                                                RefreshRequest refreshRequest = new RefreshRequest(getPreferenceString("refreshToken"));
                                                refreshApi refreshApi = RetrofitClient.getRefreshInterface();
                                                refreshApi.getRefreshResponse(refreshRequest).enqueue(new Callback<RefreshResponse>() {
                                                    @Override
                                                    public void onResponse(Call<RefreshResponse> call, Response<RefreshResponse> response) {
                                                        if(response.isSuccessful() && response.body() != null){
                                                            setPreference("acessToken", response.body().getAccessToken());
                                                            Toast.makeText(AnswerByGptActivity.this, "만료되어 갱신하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                                                        }else{
                                                            Toast.makeText(AnswerByGptActivity.this, "토큰 갱신에 실패하였습니다. 관리자에게 문의해주세요.", Toast.LENGTH_LONG).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<RefreshResponse> call, Throwable t) {
                                                        Toast.makeText(AnswerByGptActivity.this, "토큰 갱신에 실패하였습니다. 관리자에게 문의해주세요.", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                            gptTimerCall.cancel();
                                            btn_ai_diagnosis.setText("GPT 설명");
                                            btn_ai_diagnosis.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F0F0F0")));
                                            Toast.makeText(AnswerByGptActivity.this, "결과에 대한 GPT 설명이 도착하였습니다.", Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onFailure(Call<PictureResponse> call, Throwable t) {
                                            t.printStackTrace();
                                        }
                                    });
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

        findViewById(R.id.btn_ai_diagnosis).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(gpt_flag) {
                    showGPTDialog();
                }else{
                    Toast.makeText(AnswerByGptActivity.this, "GPT 결과를 기다리고 있습니다. 잠시 뒤에 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Fragment 전환을 위한 코드 추가
        findViewById(R.id.btn_post_qna).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(question_flag){
                    showQuestionDialog();
                }else{
                    Toast.makeText(AnswerByGptActivity.this, "AI판단 결과를 기다리고 있습니다. 잠시 뒤에 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
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

    private void loading(){
        String nameText = tv_diseaseInfo.getText().toString();
        if (nameText.equals("예측중입니다.....")){
            tv_diseaseInfo.setText("예측중입니다");
        }else{
            tv_diseaseInfo.setText(nameText+".");
        }
    }

    private void gptLoading(){
        String target = btn_ai_diagnosis.getText().toString();
        if (target.equals("GPT 설명.....")){
            btn_ai_diagnosis.setText("GPT 설명");
        }else{
            btn_ai_diagnosis.setText(target+".");
        }
    }

    void showGPTDialog() {
        Dialog gptDialog = new Dialog(this);
        gptDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gptDialog.setContentView(R.layout.dialog_gpt);
        gptDialog.setCanceledOnTouchOutside(false);

        TextView dialogTitle = gptDialog.findViewById(R.id.dialogTitle);
        TextView dialogContent = gptDialog.findViewById(R.id.dialogContent);
        Button closeButton = gptDialog.findViewById(R.id.closeButton);
        dialogTitle.setText("GPT 설명");

        // gpt 부분
        dialogContent.setText(gptResult);


        //
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gptDialog.dismiss();
            }
        });

        gptDialog.show();
    }

    void showQuestionDialog() {

        questionDialog = new Dialog(this);
        questionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        questionDialog.setContentView(R.layout.dialog_question);
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

                // register을 통해서 통신을 한다.
                registerQuestion(title, question, picId);
                flag = 1;


                // MainActivity 로 이동하는 부분.
                Intent intent = new Intent(AnswerByGptActivity.this, MainActivity.class);
                startActivity(intent);



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
    }

    private void registerQuestion(String title, String question, String pictureId) {
        // 질문 등록 로직을 수행하는 부분
        // 여기서는 간단히 토스트 메시지로 질문을 보여줍니다.

        retrofitClient = RetrofitClient.getInstance();
        com.example.team11_project_front.API.addQApi addQApi = RetrofitClient.getRetrofitAddQInterface();

        Toast.makeText(this, title + "이라는 제목으로 질문이 등록되었습니다: ", Toast.LENGTH_SHORT).show();

        AddQRequest addQRequest = new AddQRequest(title,question, pictureId);

        addQApi.getAddQResponse("Bearer " + getPreferenceString("acessToken"),addQRequest).enqueue(new Callback<AddQResponse>() {
            @Override
            public void onResponse(Call<AddQResponse> call, Response<AddQResponse> response) {
                if (response.code() == 401) {
                    RefreshRequest refreshRequest = new RefreshRequest(getPreferenceString("refreshToken"));
                    refreshApi refreshApi = RetrofitClient.getRefreshInterface();
                    refreshApi.getRefreshResponse(refreshRequest).enqueue(new Callback<RefreshResponse>() {
                        @Override
                        public void onResponse(Call<RefreshResponse> call, Response<RefreshResponse> response) {
                            if(response.isSuccessful() && response.body() != null){
                                setPreference("acessToken", response.body().getAccessToken());
                                Toast.makeText(AnswerByGptActivity.this, "토큰이 만료되어 갱신하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(AnswerByGptActivity.this, "토큰 갱신에 실패하였습니다. 관리자에게 문의해주세요.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RefreshResponse> call, Throwable t) {
                            Toast.makeText(AnswerByGptActivity.this, "토큰 갱신에 실패하였습니다. 관리자에게 문의해주세요.", Toast.LENGTH_LONG).show();
                        }
                    });
                } else if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(AnswerByGptActivity.this, "질문 등록이 되었습니다.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(AnswerByGptActivity.this, "등록이 실패하였습니다. 다시 시도해주십시오.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddQResponse> call, Throwable t) {
                Toast.makeText(AnswerByGptActivity.this, "서버에 요청이 실패하였습니다.", Toast.LENGTH_LONG).show();
            }
        });

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
