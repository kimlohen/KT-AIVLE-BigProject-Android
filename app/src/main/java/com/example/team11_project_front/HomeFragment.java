package com.example.team11_project_front;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

public class HomeFragment extends Fragment {
    private View view;
    private ImageButton button_skin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        Spinner spinner = view.findViewById(R.id.spinner);

        // Spinner에 표시할 항목 배열
        String[] petOptions = {"라옹", "레오", "라임"};

        // ArrayAdapter를 사용하여 항목 배열을 Spinner에 연결
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, petOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Fragment -> Activity 화면 전환!
        //// androidx.appcompat.widget.AppCompatButton button_skin = view.findViewById(R.id.btn_skin_diagnosis);
//        button_skin = (ImageButton) view.findViewById(R.id.btn_skin_diagnosis);
//        button_skin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 버튼 클릭 이벤트 처리 및 Activity로 이동
//                Intent intent = new Intent(getActivity(), SkinDiagnosisActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);
//            }
//        });

        return view;
    }
}
