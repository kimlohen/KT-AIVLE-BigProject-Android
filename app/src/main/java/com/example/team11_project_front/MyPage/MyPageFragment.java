package com.example.team11_project_front.MyPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.team11_project_front.Data.HospitalInfo;
import com.example.team11_project_front.Data.PetInfo;
import com.example.team11_project_front.R;

import java.util.ArrayList;


public class MyPageFragment extends Fragment {
    private View view;
    private ArrayList<PetInfo> petInfos;
    private ArrayList<HospitalInfo> hospitalInfos;
    Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_page, container, false);
        mContext = getActivity();

        //profile 부분 text 설정
        //이 변수들에 서버에서 받아온 데이터 저장하면 마이페이지 프로필에 보여줌
        String name = "이름";
        String type = "수의사";
        String email = "hhe@naver.com";

        TextView tv_name = (TextView) view.findViewById(R.id.profileName);
        TextView tv_type = (TextView) view.findViewById(R.id.type);
        TextView tv_email = (TextView) view.findViewById(R.id.email);

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
}