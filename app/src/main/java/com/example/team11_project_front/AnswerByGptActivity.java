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

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.team11_project_front.QnA.QnaFragment;

public class AnswerByGptActivity extends AppCompatActivity {

    private Dialog questionDialog, gptDialog;
    private EditText questionEditText,TitleEditText;

    private TextView diseaseNameText, diagonistDateText,proabilityText;
    private Button btn_ai_diagnosis;

    private int flag = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_by_gpt);

        // 이미지 URI
        String selectedImageUriString = getIntent().getStringExtra("selectedImageUri");
        // Log.d("SelectedImageUri", selectedImageUriString); // 로그 출력
        if (selectedImageUriString != null) {
            Uri selectedImageUri = Uri.parse(selectedImageUriString);
            // 이제 selectedImageUri를 사용하여 이미지를 처리할 수 있습니다.
        }



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
        dialogContent.setText("GPT를 통한 이 질환은 다음과 같은 진단을 내릴 수 있습니다.");

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
}
