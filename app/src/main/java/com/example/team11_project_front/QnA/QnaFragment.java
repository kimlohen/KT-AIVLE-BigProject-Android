package com.example.team11_project_front.QnA;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.team11_project_front.Data.QnAInfo;
import com.example.team11_project_front.MyPage.PetAdapter;
import com.example.team11_project_front.R;

import java.util.ArrayList;


public class QnaFragment extends Fragment {
    private View view;
    private ArrayList<QnAInfo> qnAInfos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_qna, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ListView listView = (ListView) view.findViewById(R.id.qnaList);
        qnAInfos = new ArrayList<>();

        for(int i = 0 ; i < 10 ; i++) {
            String title = "제목";
            for(int j = 0 ; j < i ; j ++){
                title = title + "O";
            }
            String writer = "홍길동";
            String date = "2023-06-14";
            String ansNum = "2";
            QnAInfo info = new QnAInfo(title, writer, date, ansNum);
            qnAInfos.add(info);
        }

        QnAAdapter adapter = new QnAAdapter(getContext(), qnAInfos);
        listView.setAdapter(adapter);
    }

}