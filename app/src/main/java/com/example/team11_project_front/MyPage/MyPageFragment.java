package com.example.team11_project_front.MyPage;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team11_project_front.Data.HospitalInfo;
import com.example.team11_project_front.Data.LogoutResponse;
import com.example.team11_project_front.Data.PetInfo;
import com.example.team11_project_front.LoginActivity;
import com.example.team11_project_front.PetRegisterActivity;
import com.example.team11_project_front.R;
import com.example.team11_project_front.RetrofitClient;
import com.example.team11_project_front.API.logoutApi;

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

    private Button addPet;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_page, container, false);
        mContext = getActivity();

        //profile 부분 text 설정
        //이 변수들에 서버에서 받아온 데이터 저장하면 마이페이지 프로필에 보여줌
        String name = getPreferenceString("first_name");
        String type = "수의사";
        String email = getPreferenceString("email");

        TextView tv_name = (TextView) view.findViewById(R.id.profileName);
        TextView tv_type = (TextView) view.findViewById(R.id.type);
        TextView tv_email = (TextView) view.findViewById(R.id.email);
        addPet = (Button) view.findViewById(R.id.addPetBtn);

        tv_name.setText(name);
        tv_type.setText(type);
        tv_email.setText(email);

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
        ListView listView = (ListView) view.findViewById(R.id.petList);
        petInfos = new ArrayList<>();

        //이 변수들에 서버에서 받아온 데이터 저장 후 petInfos에 추가하면 화면에 보여줌
        String petName = "강아지";
        String species = "종";
        String gender = "성별";
        String birth = "2022.04";

        PetInfo petInfo = new PetInfo(petName,birth,species,gender);
        petInfos.add(petInfo);

        PetAdapter adapter = new PetAdapter(getContext(), petInfos);
        listView.setAdapter(adapter);

        //병원정보 리스트
        ListView lv = (ListView) view.findViewById(R.id.hospital_list);
        hospitalInfos = new ArrayList<>();

        //이 변수들에 서버에서 받아온 데이터 저장 후 hospitalInfos에 추가하면 화면에 보여줌
        String name = "병원이름";
        String location = "병원위치";
        String prof = "전문영역";
        String intro = "한줄 소개";

        HospitalInfo info = new HospitalInfo(name, location, prof, intro);
        hospitalInfos.add(info);

        HospitalAdapter hospitalAdapter = new HospitalAdapter(getContext(), hospitalInfos);
        lv.setAdapter(hospitalAdapter);
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

        logoutApi.getLogoutResponse().enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful()){
                    setPreference("acessToken","");
                    setPreference("refreshToken","");
                    setPreference("email", "");
                    setPreference("first_name", "");
                    setPreference("last_name", "");
                    setPreference("autoLoginId", "");
                    setPreference("autoLoginPw", "");
                    Toast.makeText(getActivity(), "로그아웃이 완료되었습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
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

}