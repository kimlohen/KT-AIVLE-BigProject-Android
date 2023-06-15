package com.example.team11_project_front.QnA;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.team11_project_front.Data.QnAInfo;
import com.example.team11_project_front.MyPage.PetAdapter;
import com.example.team11_project_front.R;

import java.util.ArrayList;


public class QnaFragment extends Fragment {
    private View view;
    private ArrayList<QnAInfo> qnAInfos;

    private TextView beforeBtn, page1, page2, page3, page4, page5, afterBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_qna, container, false);

        beforeBtn = (TextView) view.findViewById(R.id.beforeBtn);
        page1 = (TextView) view.findViewById(R.id.page1);
        page2 = (TextView) view.findViewById(R.id.page2);
        page3 = (TextView) view.findViewById(R.id.page3);
        page4 = (TextView) view.findViewById(R.id.page4);
        page5 = (TextView) view.findViewById(R.id.page5);
        afterBtn = (TextView) view.findViewById(R.id.afterBtn);

        beforeBtn.setEnabled(false);
        beforeBtn.setVisibility(beforeBtn.INVISIBLE);

        page1.setTextColor(Color.BLUE);

        beforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.valueOf(page1.getText().toString()) == 6){
                    beforeBtn.setEnabled(false);
                    beforeBtn.setVisibility(beforeBtn.INVISIBLE);
                }
                page1.setText(String.valueOf(Integer.valueOf(page1.getText().toString()) - 5));
                page2.setText(String.valueOf(Integer.valueOf(page2.getText().toString()) - 5));
                page3.setText(String.valueOf(Integer.valueOf(page3.getText().toString()) - 5));
                page4.setText(String.valueOf(Integer.valueOf(page4.getText().toString()) - 5));
                page5.setText(String.valueOf(Integer.valueOf(page5.getText().toString()) - 5));

                page1.setTextColor(Color.BLUE);
                page2.setTextColor(Color.BLACK);
                page3.setTextColor(Color.BLACK);
                page4.setTextColor(Color.BLACK);
                page5.setTextColor(Color.BLACK);
            }
        });

        afterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeBtn.setEnabled(true);
                beforeBtn.setVisibility(beforeBtn.VISIBLE);
                page1.setText(String.valueOf(Integer.valueOf(page1.getText().toString()) + 5));
                page2.setText(String.valueOf(Integer.valueOf(page2.getText().toString()) + 5));
                page3.setText(String.valueOf(Integer.valueOf(page3.getText().toString()) + 5));
                page4.setText(String.valueOf(Integer.valueOf(page4.getText().toString()) + 5));
                page5.setText(String.valueOf(Integer.valueOf(page5.getText().toString()) + 5));

                page1.setTextColor(Color.BLUE);
                page2.setTextColor(Color.BLACK);
                page3.setTextColor(Color.BLACK);
                page4.setTextColor(Color.BLACK);
                page5.setTextColor(Color.BLACK);
            }
        });
        page1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page1.setTextColor(Color.BLUE);
                page2.setTextColor(Color.BLACK);
                page3.setTextColor(Color.BLACK);
                page4.setTextColor(Color.BLACK);
                page5.setTextColor(Color.BLACK);
            }
        });

        page2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page1.setTextColor(Color.BLACK);
                page2.setTextColor(Color.BLUE);
                page3.setTextColor(Color.BLACK);
                page4.setTextColor(Color.BLACK);
                page5.setTextColor(Color.BLACK);
            }
        });
        page3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page1.setTextColor(Color.BLACK);
                page2.setTextColor(Color.BLACK);
                page3.setTextColor(Color.BLUE);
                page4.setTextColor(Color.BLACK);
                page5.setTextColor(Color.BLACK);
            }
        });
        page4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page1.setTextColor(Color.BLACK);
                page2.setTextColor(Color.BLACK);
                page3.setTextColor(Color.BLACK);
                page4.setTextColor(Color.BLUE);
                page5.setTextColor(Color.BLACK);
            }
        });
        page5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page1.setTextColor(Color.BLACK);
                page2.setTextColor(Color.BLACK);
                page3.setTextColor(Color.BLACK);
                page4.setTextColor(Color.BLACK);
                page5.setTextColor(Color.BLUE);
            }
        });

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
                title = title + "OOO";
            }
            String writer = "홍길동";
            String date = "2023-06-14";
            String ansNum = "2";
            QnAInfo info = new QnAInfo(title, writer, date, ansNum);
            qnAInfos.add(info);
        }

        QnAAdapter adapter = new QnAAdapter(getContext(), qnAInfos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getContext(), ArticleActivity.class);
                /* putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값 */
                intent.putExtra("title", qnAInfos.get(position).getTitle());
                intent.putExtra("writer", qnAInfos.get(position).getWriter());
                intent.putExtra("date", qnAInfos.get(position).getDate());
                startActivity(intent);
            }
        });

    }

}