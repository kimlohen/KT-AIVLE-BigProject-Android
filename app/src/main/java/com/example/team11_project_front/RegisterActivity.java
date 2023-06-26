package com.example.team11_project_front;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.team11_project_front.API.emailVerifyApi;
import com.example.team11_project_front.API.hospitalApi;
import com.example.team11_project_front.API.joinApi;
import com.example.team11_project_front.Data.EmailRequest;
import com.example.team11_project_front.Data.EmailResponse;
import com.example.team11_project_front.Data.HospitalRequest;
import com.example.team11_project_front.Data.HospitalResponse;
import com.example.team11_project_front.Data.JoinRequest;
import com.example.team11_project_front.Data.JoinResponse;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityResultLauncher<Intent> resultLauncher;
    private RetrofitClient retrofitClient;
    private joinApi joinApi;
    private emailVerifyApi emailVerifyApi;
    private Switch veterinarianBtn;
    private boolean isSwitchChecked = false;
    private EditText pwEdit, pwEdit2, nameEdit, mailEdit;
    private CheckBox serviceOkBtn;
    private ImageView backBtn;
    private Button registerBtn, verifyBtn;
    private boolean verifyMail = false;
    private String hospitalName, hospitalOfficeNumber, hospitalAddress, hospitalIntroduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        backBtn = (ImageView) findViewById(R.id.backBtn3);
        veterinarianBtn = (Switch) findViewById(R.id.veterinarianBtn);
        nameEdit = (EditText) findViewById(R.id.nameEdit);
        mailEdit = (EditText) findViewById(R.id.mailEdit);
        pwEdit = (EditText) findViewById(R.id.pwEdit);
        pwEdit2 = (EditText) findViewById(R.id.pwEdit2);
        serviceOkBtn = (CheckBox) findViewById(R.id.serviceOkBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        verifyBtn = (Button) findViewById(R.id.verifyBtn);
        TextView membershipLink = (TextView) findViewById(R.id.membershipLink);

        backBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        verifyBtn.setOnClickListener(this);
        membershipLink.setOnClickListener(this);

        veterinarianBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showAdditionalInfoDialog();
                } else {
                    isSwitchChecked = false;
                }
            }
        });

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {

                        }
                    }
                }
        );

        registerBtn.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_HOVER_ENTER:
                        // 마우스가 버튼 위로 들어왔을 때의 동작
                        registerBtn.setBackgroundColor(Color.BLACK);
                        registerBtn.setTextColor(Color.WHITE);
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT:
                        // 마우스가 버튼에서 벗어났을 때의 동작
                        registerBtn.setBackgroundColor(Color.LTGRAY); // 기본 배경색으로 변경하려면 이전 배경색을 지웁니다.
                        registerBtn.setTextColor(Color.BLACK); // 기본 글자색으로 변경하려면 이전 글자색을 지웁니다.
                        break;
                }
                return false;
            }
        });

        membershipLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMembershipDialog();
            }
        });


    }
    private void showMembershipDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("약관");
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_membership_condition, null);
        builder.setView(dialogView);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAdditionalInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("추가 정보 입력")
                .setMessage("수의사 관련 정보를 입력하세요.");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_additional_info, null);
        builder.setView(dialogView);

        final EditText hospitalNameEdit = dialogView.findViewById(R.id.hospitalNameEdit);
        final EditText hospitalOfficeNumberEdit = dialogView.findViewById(R.id.hospitalOfficeNumberEdit);
        final EditText hospitalAddressEdit = dialogView.findViewById(R.id.hospitalAddressEdit);
        final EditText hospitalIntroductionEdit = dialogView.findViewById(R.id.hospitalIntroductionEdit);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 추가 정보 처리를 위한 로직 작성
                hospitalName = hospitalNameEdit.getText().toString();
                hospitalOfficeNumber = hospitalOfficeNumberEdit.getText().toString();
                hospitalAddress = hospitalAddressEdit.getText().toString();
                hospitalIntroduction = hospitalIntroductionEdit.getText().toString();

                // 입력된 정보를 사용하여 필요한 작업 수행
                // ...

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                veterinarianBtn.setChecked(false);
                isSwitchChecked = false;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                isSwitchChecked = true;
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!isSwitchChecked) {
                    veterinarianBtn.setChecked(false);
                }
            }
        });
        dialog.show();
    }

    //키보드 숨기기
    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(nameEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mailEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(pwEdit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(pwEdit2.getWindowToken(), 0);
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
        if (id == R.id.backBtn3) {
            Intent intent = new Intent(v.getContext(), LoginActivity.class);
            resultLauncher.launch(intent);
        } else if (id == R.id.registerBtn) {
            send();
        } else if (id == R.id.verifyBtn){
            verify();
        }
    }

    void verify() {
        String email = mailEdit.getText().toString().trim();
        EmailRequest emailRequest = new EmailRequest(email);
        retrofitClient = RetrofitClient.getInstance();
        emailVerifyApi = RetrofitClient.getRetrofitEmailVerifytInterface();
        emailVerifyApi.getEmailResponse(emailRequest).enqueue(new Callback<EmailResponse>() {
            @Override
            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
                Log.d("retrofit", "Data fetch success");
                if (response.isSuccessful() && response.body() != null) {
                    EmailResponse result = response.body();
                    String success = result.getSuccess();
                    if(success.equals("true")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setTitle("알림")
                                .setMessage("이메일 인증에 성공하였습니다.")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();
                        verifyMail = true;
                        mailEdit.setFocusable(false);
                        mailEdit.setClickable(false);
                    }else if(success.equals("false")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setTitle("알림")
                                .setMessage("이미 존재하는 이메일입니다.")
                                .setPositiveButton("확인", null)
                                .create()
                                .show();
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("알림")
                            .setMessage("이미 존재하는 이메일입니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }
            }


            @Override
            public void onFailure(Call<EmailResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("서버와 통신에 실패하였습니다. 네트워크를 확인해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }

    void send(){
        try {
            String email = mailEdit.getText().toString();
            String name = nameEdit.getText().toString();
            String pw = pwEdit.getText().toString();
            String pw2 = pwEdit2.getText().toString();

            if (name.trim().length() == 0 || name == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("이름 정보를 입력바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else if (email.trim().length() == 0 || email == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("이메일 정보를 입력바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else if (!verifyMail) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("이메일 인증을 시도하기 바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else if (pw.trim().length() == 0 || pw == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("비밀번호 정보를 입력바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else if (pw2.trim().length() == 0 || pw == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("비밀번호 확인 정보를 입력바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else if (!pw.equals(pw2)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("비밀번호 입력과 확인이 다릅니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else if (pw.trim().length() < 8) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("비밀번호가 너무 짧습니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else if (isValidPassword(pw.trim()) != 7) {
                int errorCode = isValidPassword(pw.trim());
                if (errorCode == 2) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("알림")
                            .setMessage("비밀번호가 너무 깁니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }else if(errorCode == 3){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("알림")
                            .setMessage("비밀번호가 공백을 포함하고 있습니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }else if(errorCode == 4){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("알림")
                            .setMessage("비밀번호가 유효하지 않은 문자를 포함하고 있습니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }else if(errorCode == 5){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("알림")
                            .setMessage("비밀번호가 같은 문자를 연속으로 3번이상 포함하고 있습니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }else if(errorCode == 6){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("알림")
                            .setMessage("비밀번호가 연속된 문자, 숫자를 포함하고 있습니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }
            }else if (!serviceOkBtn.isChecked()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("서비스 약관의 동의를 바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else if (veterinarianBtn.isChecked() && !(hospitalAddress != null && hospitalIntroduction != null && hospitalOfficeNumber != null && hospitalName != null)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("병원정보를 입력바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }else{
                joinResponse();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "회원가입 전송 실패!", Toast.LENGTH_LONG).show();
        }
    }

    public void joinResponse() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String email = mailEdit.getText().toString().trim();
        String name = nameEdit.getText().toString().trim();
        String pw = pwEdit.getText().toString().trim();
        String pw2 = pwEdit2.getText().toString().trim();

        JoinRequest joinRequest = new JoinRequest(name, email, sha256(pw), sha256(pw2), "false");

        if (veterinarianBtn.isChecked()){
            joinRequest = new JoinRequest(name, email,  sha256(pw), sha256(pw2), "true");
        }

        retrofitClient = RetrofitClient.getInstance();
        joinApi = RetrofitClient.getRetrofitJoinInterface();

        joinApi.getJoinResponse(joinRequest).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {

                Log.d("retrofit", "Data fetch success");

                //통신 성공
                if (response.isSuccessful() && response.body() != null) {
                    JoinResponse result = response.body();

                    String acessToken = result.getAcessToken();
                    String refreshToken = result.getRefreshToken();

                    String email = result.getUser().getEmail();
                    String first_name = result.getUser().getFirst_name();
                    String profile_img = result.getUser().getIs_vet();
                    if(profile_img == null){
                        profile_img = result.getUser().getAvatar();
                    }
                    String is_vet = result.getUser().getProfile_img();

                    if (acessToken != null) {
                        String userID = mailEdit.getText().toString();
                        String userPassword = pwEdit.getText().toString();

                        //다른 통신을 하기 위해 token 저장
                        setPreference("acessToken",acessToken);
                        setPreference("refreshToken",refreshToken);
                        setPreference("email", email);
                        setPreference("first_name", first_name);
                        setPreference("is_vet", is_vet);
                        setPreference("profile_img", profile_img);
                    }

                    if(veterinarianBtn.isChecked()){
                        HospitalRequest hospitalRequest = new HospitalRequest(hospitalName, hospitalAddress, hospitalOfficeNumber, hospitalIntroduction);
                        hospitalApi hospitalApi = RetrofitClient.getRetrofitHospitalInterface();
                        hospitalApi.getJoinResponse("Bearer " + acessToken, hospitalRequest).enqueue(new Callback<HospitalResponse>() {
                            @Override
                            public void onResponse(Call<HospitalResponse> call, Response<HospitalResponse> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    Toast.makeText(RegisterActivity.this, "병원등록이 완료되었습니다.", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(RegisterActivity.this, "병원등록에 실패하였습니다. 관리자에게 문의해주세요.", Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<HospitalResponse> call, Throwable t) {
                                Toast.makeText(RegisterActivity.this, "병원등록에 실패하였습니다. 관리자에게 문의해주세요.", Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    //회원가입 전송
                    Toast.makeText(RegisterActivity.this, "회원가입되었습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    RegisterActivity.this.finish();
                }
            }

            //통신 실패
            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("알림")
                        .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }

    // 데이터를 내부 저장소에 저장하기
    public void setPreference(String key, String value){
        SharedPreferences pref = getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String sha256(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes("UTF-8"));
        byte[] bytes = md.digest();
        String hash = String.format("%64x", new BigInteger(1, bytes));
        return hash;
    }

    /**
     * 비밀번호 검증 메소드
     *
     * @param password 비밀번호 문자열
     * @return 오류번호
     */
    int isValidPassword(String password) {
        // 최소 8자, 최대 20자 상수 선언
        final int MIN = 8;
        final int MAX = 20;

        // 영어, 숫자, 특수문자 포함한 MIN to MAX 글자 정규식
        final String REGEX =
                "^((?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W]).{" + MIN + "," + MAX + "})$";
        // 3자리 연속 문자 정규식
        final String SAMEPT = "(\\w)\\1\\1";
        // 공백 문자 정규식
        final String BLANKPT = "(\\s)";

        // 정규식 검사객체
        Matcher matcher;

        // 공백 체크
        if (password == null || "".equals(password)) {
            return 1; // "Detected: No Password";
        }

        // ASCII 문자 비교를 위한 UpperCase
        String tmpPw = password.toUpperCase();
        // 문자열 길이
        int strLen = tmpPw.length();

        // 글자 길이 체크
        if (strLen > 20 || strLen < 8) {
            return 2; // "Detected: Incorrect Length(Length: " + strLen + ")";
        }

        // 공백 체크
        matcher = Pattern.compile(BLANKPT).matcher(tmpPw);
        if (matcher.find()) {
            return 3; //"Detected: Blank";
        }

        // 비밀번호 정규식 체크
        matcher = Pattern.compile(REGEX).matcher(tmpPw);
        if (!matcher.find()) {
            return 4; //"Detected: Wrong Regex";
        }

        // 동일한 문자 3개 이상 체크
        matcher = Pattern.compile(SAMEPT).matcher(tmpPw);
        if (matcher.find()) {
            return 5; //"Detected: Same Word";
        }

        // 연속된 문자 / 숫자 3개 이상 체크
        // ASCII Char를 담을 배열 선언
        int[] tmpArray = new int[strLen];

        // Make Array
        for (int i = 0; i < strLen; i++) {
            tmpArray[i] = tmpPw.charAt(i);
        }

        // Validation Array
        for (int i = 0; i < strLen - 2; i++) {
            // 첫 글자 A-Z / 0-9
            if ((tmpArray[i] > 47
                    && tmpArray[i + 2] < 58)
                    || (tmpArray[i] > 64
                    && tmpArray[i + 2] < 91)) {
                // 배열의 연속된 수 검사
                // 3번째 글자 - 2번째 글자 = 1, 3번째 글자 - 1번째 글자 = 2
                if (Math.abs(tmpArray[i + 2] - tmpArray[i + 1]) == 1
                        && Math.abs(tmpArray[i + 2] - tmpArray[i]) == 2) {
                    char c1 = (char) tmpArray[i];
                    char c2 = (char) tmpArray[i + 1];
                    char c3 = (char) tmpArray[i + 2];
                    return 6; //"Detected: Continuous Pattern: \"" + c1 + c2 + c3 + "\"";
                }
            }
        }
        // Validation Complete
        return 7;
    }
}