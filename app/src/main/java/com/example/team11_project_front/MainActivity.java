package com.example.team11_project_front;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.team11_project_front.API.getHospitalAdApi;
import com.example.team11_project_front.API.refreshApi;
import com.example.team11_project_front.Data.HospitalAdResponse;
import com.example.team11_project_front.Data.HospitalResponse;
import com.example.team11_project_front.Data.RefreshRequest;
import com.example.team11_project_front.Data.RefreshResponse;
import com.example.team11_project_front.MyPage.MyPageFragment;
import com.example.team11_project_front.QnA.QnaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fm = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    private MyPageFragment myPageFragment = new MyPageFragment();
    private QnaFragment qnaFragment = new QnaFragment();
    private HomeFragment homeFragment = new HomeFragment();
    private FragmentTransaction ft;

    private RetrofitClient retrofitClient;
    private com.example.team11_project_front.API.getHospitalApi getHospitalApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ft = fm.beginTransaction();

        bottomNavigationView = findViewById(R.id.menu_bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
        bottomNavigationView.setOnItemSelectedListener(new ItemSelectedListener());

        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_layout, homeFragment).commit();
        retrofitClient = RetrofitClient.getInstance();
        if(getPreferenceString("is_vet").equals("true")){
            getHospitalApi = RetrofitClient.getRetrofitGetHospitalInterface();
            getHospitalApi.getHospitalResponse("Bearer " + getPreferenceString("acessToken")).enqueue(new Callback<List<HospitalResponse>>() {
                @Override
                public void onResponse(Call<List<HospitalResponse>> call, Response<List<HospitalResponse>> response) {
                    if (response.code() == 401) {
                        RefreshRequest refreshRequest = new RefreshRequest(getPreferenceString("refreshToken"));
                        refreshApi refreshApi = RetrofitClient.getRefreshInterface();
                        refreshApi.getRefreshResponse(refreshRequest).enqueue(new Callback<RefreshResponse>() {
                            @Override
                            public void onResponse(Call<RefreshResponse> call, Response<RefreshResponse> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    setPreference("acessToken", response.body().getAccessToken());
                                    Toast.makeText(MainActivity.this, "토큰이 만료되어 갱신하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(MainActivity.this, "토큰 갱신에 실패하였습니다. 관리자에게 문의해주세요.", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RefreshResponse> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "토큰 갱신에 실패하였습니다. 관리자에게 문의해주세요.", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else if(response.isSuccessful() && response.body() != null){
                        List<HospitalResponse> responses = response.body();
                        HospitalResponse res = responses.get(0);
                        setPreference("hos_id", res.getHos_id());
                        setPreference("hos_name", res.getHos_name());
                        setPreference("hos_address", res.getAddress());
                        setPreference("hos_officenumber", res.getOfficenumber());
                        setPreference("hos_introduction", res.getIntroduction());
                        setPreference("hos_profile_img", res.getHos_profile_img());
                    }
                }

                @Override
                public void onFailure(Call<List<HospitalResponse>> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("알림")
                            .setMessage("내 병원 정보를 받아오는데 실패하였습니다.\n 관리자에게 문의바랍니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }
            });

        }else{
            setPreference("hos_id", "");
            setPreference("hos_name", "");
            setPreference("hos_address", "");
            setPreference("hos_officenumber", "");
            setPreference("hos_introduction", "");
            setPreference("hos_profile_img", "");
        }

    }

    class ItemSelectedListener implements NavigationBarView.OnItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ft = fm.beginTransaction();
            int itemId = item.getItemId();
            if (itemId == R.id.menu_home) {
                ft.replace(R.id.menu_frame_layout, homeFragment);
                ft.commit();
                return true;
            }
            else if (itemId == R.id.menu_mypage) {
                ft.replace(R.id.menu_frame_layout, myPageFragment);
                ft.commit();
                return true;
            }
            else if (itemId == R.id.menu_qna) {
                ft.replace(R.id.menu_frame_layout, qnaFragment);
                ft.commit();
                return true;
            }

            return false;
        }
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