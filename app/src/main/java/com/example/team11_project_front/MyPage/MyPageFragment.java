package com.example.team11_project_front.MyPage;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.team11_project_front.API.deleteUserApi;
import com.example.team11_project_front.API.logoutApi;
import com.example.team11_project_front.API.refreshApi;

import com.example.team11_project_front.Data.DeleteUserResponse;
import com.example.team11_project_front.Data.HospitalInfo;
import com.example.team11_project_front.Data.HospitallistResponse;
import com.example.team11_project_front.Data.LogoutResponse;
import com.example.team11_project_front.Data.PetInfo;
import com.example.team11_project_front.Data.PetlistResponse;
import com.example.team11_project_front.Data.RefreshRequest;
import com.example.team11_project_front.Data.RefreshResponse;
import com.example.team11_project_front.LoginActivity;
import com.example.team11_project_front.PetRegisterActivity;
import com.example.team11_project_front.R;
import com.example.team11_project_front.RetrofitClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyPageFragment extends Fragment {
    private View view;
    private ArrayList<PetInfo> petInfos;
    private ArrayList<HospitalInfo> hospitalInfos;
    private RetrofitClient retrofitClient;
    private logoutApi logoutApi;
    private deleteUserApi deleteUserApi;
    private Button addPet;
    Context mContext;
    private com.example.team11_project_front.API.petlistApi petlistApi;
    private com.example.team11_project_front.API.hospitallistApi hospitallistApi;

    Bitmap bitmap;

    String is_vet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_page, container, false);
        mContext = getActivity();


        //profile 부분 text 설정
        //이 변수들에 서버에서 받아온 데이터 저장하면 마이페이지 프로필에 보여줌
        String name = getPreferenceString("first_name");
        String type = "일반회원";
        String email = getPreferenceString("email");
        String profile = getPreferenceString("profile_img");
        is_vet = getPreferenceString("is_vet");
        TextView tv_name = (TextView) view.findViewById(R.id.profileName);
        TextView tv_type = (TextView) view.findViewById(R.id.type);
        TextView tv_email = (TextView) view.findViewById(R.id.email);
        TextView tv_hospital = (TextView) view.findViewById(R.id.hospitalText);
        ImageView iv_profile = (ImageView) view.findViewById(R.id.profile);
        addPet = (Button) view.findViewById(R.id.addPetBtn);

        if (is_vet.equals("true")){
            type = "수의사";
        }else {
            tv_hospital.setVisibility(View.INVISIBLE);
        }

        tv_name.setText(name);
        tv_type.setText(type);
        tv_email.setText(email);

        Thread mthread = new Thread(){
            @Override
            public void run(){
                try{
                    URL url = new URL(profile);
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

        mthread.start();
        try{
            mthread.join();
            iv_profile.setImageBitmap(bitmap.createScaledBitmap(bitmap, 87, 87, false));
        }catch (InterruptedException e){
            e.printStackTrace();
        }


        //게시글 작성 내역 버튼 클릭 시 작성한 게시글들을 보여주는 페이지로 이동함
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.posted);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostedActivity.class);
                startActivity(intent);
            }
        });

        //로그아웃 영역 클릭 시 로그아웃을 하고 로그인 페이지로 이동함
        LinearLayout logout_layout = (LinearLayout) view.findViewById(R.id.logout);
        logout_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        LinearLayout deleteUser_layout = (LinearLayout) view.findViewById(R.id.deleteUser);
        deleteUser_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ deleteUser(); }
        });

        addPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PetRegisterActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //반려동물 정보 리스트

        retrofitClient = RetrofitClient.getInstance();
        petlistApi = retrofitClient.getRetrofitPetlistInterface();
        hospitallistApi = retrofitClient.getRetrofitHospitallistInterface();


        petlistApi.getPetlistResponse("Bearer " + getPreferenceString("acessToken")).enqueue(new Callback<ArrayList<PetlistResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PetlistResponse>> call, Response<ArrayList<PetlistResponse>> response) {
                ListView listView = (ListView) view.findViewById(R.id.petList);
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<PetlistResponse> petlistResponses = response.body();
                    // PetlistResponse 객체를 PetInfo 객체로 변환하여 리스트에 추가

                    petInfos = new ArrayList<>();
                    listView.setAdapter(null);

                    for (PetlistResponse petlistResponse : petlistResponses) {
                        String id = petlistResponse.getId();
                        String petName = petlistResponse.getName();
                        String species = petlistResponse.getSpecies();
                        String gender = petlistResponse.getGender();
                        String birth = petlistResponse.getBirth();
                        PetInfo petInfo = new PetInfo(id, petName, birth,  species,gender);
                        petInfos.add(petInfo);
                    }


                    PetAdapter adapter = new PetAdapter(getContext(), petInfos);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);


                }
            }

            public void onFailure(Call<ArrayList<PetlistResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), "동물 정보를 제대로 가져오지 못 했습니다.", Toast.LENGTH_LONG).show();
            }
        });


        //병원정보 리스트

        if (is_vet.equals("true")) {

            hospitallistApi.getHospitallistResponse("Bearer " + getPreferenceString("acessToken")).enqueue(new Callback<ArrayList<HospitallistResponse>>() {


                public void onResponse(Call<ArrayList<HospitallistResponse>> call, Response<ArrayList<HospitallistResponse>> response) {
                    ListView lv = (ListView) view.findViewById(R.id.hospital_list);

                    if (response.isSuccessful() && response.body() != null) {
                        ArrayList<HospitallistResponse> hospitallistResponses = response.body();
                        // PetlistResponse 객체를 PetInfo 객체로 변환하여 리스트에 추가
                        Toast.makeText(getActivity(), "병원 리스트가 갱신되었습니다.", Toast.LENGTH_LONG).show();
                        hospitalInfos = new ArrayList<>();
                        lv.setAdapter(null);

                        for (HospitallistResponse hospitallistResponse : hospitallistResponses) {
                            String id = hospitallistResponse.getId();
                            String name = hospitallistResponse.getName();
                            String address = hospitallistResponse.getAddress();
                            String tel = hospitallistResponse.getTel();
                            String intro = hospitallistResponse.getIntroduction();
                            String photo = hospitallistResponse.getPhoto();
                            HospitalInfo info = new HospitalInfo(id, name, address, tel, intro, photo);
                            hospitalInfos.add(info);
                        }


                        HospitalAdapter hospitalAdapter = new HospitalAdapter(getContext(), hospitalInfos);
                        hospitalAdapter.notifyDataSetChanged();
                        lv.setAdapter(hospitalAdapter);


                    }
                }

                public void onFailure(Call<ArrayList<HospitallistResponse>> call, Throwable t) {
                    Toast.makeText(getActivity(), "병원 정보를 제대로 가져오지 못 했습니다.", Toast.LENGTH_LONG).show();
                }
            });

        }
    }


    // 데이터를 내부 저장소에 저장하기
    public void setPreference(String key, String value){
        SharedPreferences pref = this.getActivity().getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }
    // 내부 저장소에 저장된 데이터 가져오기
    public String getPreferenceString(String key) {
        SharedPreferences pref = this.getActivity().getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        return pref.getString(key, "");
    }

    void logout(){
        retrofitClient = RetrofitClient.getInstance();
        logoutApi = RetrofitClient.getRetrofitLogoutInterface();

        logoutApi.getLogoutResponse("Bearer " + getPreferenceString("acessToken")).enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
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
                } else if (response.isSuccessful()){
                    setPreference("acessToken","");
                    setPreference("refreshToken","");
                    setPreference("email", "");
                    setPreference("first_name", "");
                    setPreference("is_vet", "");
                    setPreference("profile_img", "");
                    setPreference("autoLoginId", "");
                    setPreference("autoLoginPw", "");
                    Toast.makeText(getActivity(), "로그아웃이 완료되었습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("알림")
                            .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("알림")
                        .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });

    }

    void deleteUser(){
        retrofitClient = RetrofitClient.getInstance();
        deleteUserApi = RetrofitClient.getRetrofitDeleteUserInterface();
        deleteUserApi.getDeleteUserResponse("Bearer " + getPreferenceString("acessToken")).enqueue(new Callback<DeleteUserResponse>() {
            @Override
            public void onResponse(Call<DeleteUserResponse> call, Response<DeleteUserResponse> response) {
                Log.d("retrofit", "Data fetch success");

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
                } else if (response.isSuccessful() && response.body() != null) { //통신 성공
                    setPreference("acessToken","");
                    setPreference("refreshToken","");
                    setPreference("email", "");
                    setPreference("first_name", "");
                    setPreference("is_vet", "");
                    setPreference("profile_img", "");
                    setPreference("autoLoginId", "");
                    setPreference("autoLoginPw", "");

                    Toast.makeText(getActivity(), "회원탈퇴 되셨습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<DeleteUserResponse> call, Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("알림")
                            .setMessage("예기치 못한 오류가 발생하였습니다.\n 고객센터에 문의바랍니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
            }
        });
    }

}