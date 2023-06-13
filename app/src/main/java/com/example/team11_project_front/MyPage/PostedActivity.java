package com.example.team11_project_front.MyPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.team11_project_front.Data.PostedList;
import com.example.team11_project_front.MainActivity;
import com.example.team11_project_front.R;

import java.util.ArrayList;

public class PostedActivity extends AppCompatActivity {

    ArrayList<PostedList> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted);
        ListView listView = (ListView) findViewById(R.id.postedList);
        list = new ArrayList<>();

        //이 변수들에 서버에서 받아온 데이터 저장 후 list에 추가하면 화면에 보여줌
        String title = "이거 여드름?";
        String pet = "초코";
        String date = "23-08-07";
        PostedList postedList = new PostedList(title,pet,date);
        list.add(postedList);


        PostedAdapter adapter = new PostedAdapter(this, list);
        listView.setAdapter(adapter);

        //뒤로가기 버튼 클릭 시 다시 마이페이지로 돌아감
        AppCompatButton btn = (AppCompatButton) findViewById(R.id.backbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }
}