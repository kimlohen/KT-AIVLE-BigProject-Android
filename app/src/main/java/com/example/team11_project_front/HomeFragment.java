package com.example.team11_project_front;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team11_project_front.API.getHospitalAdApi;
import com.example.team11_project_front.API.refreshApi;
import com.example.team11_project_front.Data.HospitalAdResponse;
import com.example.team11_project_front.Data.RefreshRequest;
import com.example.team11_project_front.Data.RefreshResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private View view;
    private ImageView backBtn2;
    private androidx.appcompat.widget.AppCompatButton button_skin, btn_ad_hos_call;
    private TextView tv_ad_hos_name, tv_ad_hos_addr, tv_ad_hos_intro;
    private ImageView iv_ad_hos_profile;
    Bitmap bitmap;



    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        button_skin = (androidx.appcompat.widget.AppCompatButton) view.findViewById(R.id.btn_skin_diagnosis);
        button_skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼 클릭 이벤트 처리 및 SkinDiagnosisActivity로 이동
                Intent intent = new Intent(getActivity(), SkinDiagnosisActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        return view;

    }

    @Override
    public void onResume(){
        super.onResume();
        getHospitalAdApi getHospitalAdApi = RetrofitClient.getRetrofitGetHospitalAdInterface();
        getHospitalAdApi.getHospitalResponse("Bearer " + getPreferenceString("acessToken")).enqueue(new Callback<HospitalAdResponse>() {
            @Override
            public void onResponse(Call<HospitalAdResponse> call, Response<HospitalAdResponse> response) {
                if (response.code() == 401) {
                    RefreshRequest refreshRequest = new RefreshRequest(getPreferenceString("refreshToken"));
                    refreshApi refreshApi = RetrofitClient.getRefreshInterface();
                    refreshApi.getRefreshResponse(refreshRequest).enqueue(new Callback<RefreshResponse>() {
                        @Override
                        public void onResponse(Call<RefreshResponse> call, Response<RefreshResponse> response) {
                            if(response.isSuccessful() && response.body() != null){
                                setPreference("acessToken", response.body().getAccessToken());
                                Toast.makeText(getActivity(), "토큰이 만료되어 갱신하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getActivity(), "토큰 갱신에 실패하였습니다. 관리자에게 문의해주세요.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RefreshResponse> call, Throwable t) {
                            Toast.makeText(getActivity(), "토큰 갱신에 실패하였습니다. 관리자에게 문의해주세요.", Toast.LENGTH_LONG).show();
                        }
                    });
                } else if(response.isSuccessful() && response!=null){
                    HospitalAdResponse res = response.body();
                    String ad_hos_name = res.getHospital().getHos_name();
                    String ad_hos_addr = res.getHospital().getAddress();
                    String ad_hos_profile = res.getHospital().getHos_profile_img();
                    String ad_hos_intro = res.getHospital().getIntroduction();
                    String ad_hos_call = "tel:" + res.getHospital().getOfficenumber();

                    tv_ad_hos_name = (TextView) view.findViewById(R.id.today_hos_name);
                    tv_ad_hos_addr = (TextView) view.findViewById(R.id.today_hos_addr);
                    tv_ad_hos_intro = (TextView) view.findViewById(R.id.today_hos_intro);
                    iv_ad_hos_profile = (ImageView) view.findViewById(R.id.today_hos_img);
                    btn_ad_hos_call = (androidx.appcompat.widget.AppCompatButton) view.findViewById(R.id.today_hos_call);

                    tv_ad_hos_name.setText(ad_hos_name);
                    tv_ad_hos_addr.setText(ad_hos_addr);
                    tv_ad_hos_intro.setText(ad_hos_intro);
                    Thread mThread = new Thread(){
                        @Override
                        public void run(){
                            try {
                                URL url = new URL(ad_hos_profile);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setDoInput(true);
                                conn.connect();

                                InputStream is = conn.getInputStream();
                                bitmap = BitmapFactory.decodeStream(is);
                            }catch (MalformedURLException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    };

                    mThread.start();
                    try{
                        mThread.join();
                        iv_ad_hos_profile.setImageBitmap(bitmap.createScaledBitmap(bitmap, 120, 120, false));
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    btn_ad_hos_call.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public  void onClick(View view){
                            getActivity().startActivity(new Intent("android.intent.action.DIAL", Uri.parse(ad_hos_call)));
                        }
                    });

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("알림")
                            .setMessage("추천 병원 정보를 받아오는데 실패하였습니다.\n 관리자에게 문의바랍니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }
            }

            @Override
            public void onFailure(Call<HospitalAdResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("알림")
                        .setMessage("추천 병원 정보를 받아오는데 실패하였습니다.\n 관리자에게 문의바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }
    // 데이터를 내부 저장소에 저장하기
    public void setPreference(String key, String value){
        SharedPreferences pref = getActivity().getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    // 내부 저장소에 저장된 데이터 가져오기
    public String getPreferenceString(String key) {
        SharedPreferences pref = getActivity().getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        return pref.getString(key, "");
    }
}
