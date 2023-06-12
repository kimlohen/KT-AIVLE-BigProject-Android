package com.example.team11_project_front.MyPage;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.team11_project_front.Data.PetInfo;
import com.example.team11_project_front.R;

import java.util.ArrayList;


public class MyPageFragment extends Fragment {
    private View view;
    private ArrayList<PetInfo> petInfos;
    Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_page, container, false);
        mContext = getActivity();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ListView listView = (ListView) view.findViewById(R.id.petList);
        petInfos = new ArrayList<>();
        String petName = "강아지";
        String species = "종";
        String gender = "성별";
        String birth = "2022.04";
        PetInfo petInfo = new PetInfo(petName,birth,species,gender);
        petInfos.add(petInfo);

        PetAdapter adapter = new PetAdapter(getContext(), petInfos);
        listView.setAdapter(adapter);
    }
}