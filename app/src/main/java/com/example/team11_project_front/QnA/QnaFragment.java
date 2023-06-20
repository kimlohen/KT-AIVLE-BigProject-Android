package com.example.team11_project_front.QnA;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.team11_project_front.API.addPetApi;
import com.example.team11_project_front.API.qnaApi;
import com.example.team11_project_front.Data.QnAInfo;
import com.example.team11_project_front.Data.QnaResponse;
import com.example.team11_project_front.MyPage.PetAdapter;
import com.example.team11_project_front.PetRegisterActivity;
import com.example.team11_project_front.R;
import com.example.team11_project_front.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QnaFragment extends Fragment {
    private View view;
    private ArrayList<QnAInfo> qnAInfos;

    private TextView beforeBtn, page1, page2, page3, page4, page5, afterBtn;

    private RetrofitClient retrofitClient;
    private qnaApi qnaApi;

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

        retrofitClient = RetrofitClient.getInstance();
        qnaApi qnaApi = RetrofitClient.getRetrofitQnaInterface();
        qnaApi.getQnaResponse("Bearer " + getPreferenceString("acessToken")).enqueue(new Callback<ArrayList<QnaResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<QnaResponse>> call, Response<ArrayList<QnaResponse>> response) {
                if (response.isSuccessful()){
                    ArrayList<QnaResponse> responses = response.body();
                    responses.forEach((element) -> {
                        String title = element.getTitle();
                        String writer = element.getUserid();
                        String contents = element.getContents();
                        String ansNum = element.getAnswer_count();
                        String photo = element.getPhoto();
                        String date = element.getCreated_at();

                        QnAInfo info = new QnAInfo(title, writer, date, ansNum, photo, contents);
                        qnAInfos.add(info);
                    });
                    QnAAdapter adapter = new QnAAdapter(getContext(), qnAInfos);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<QnaResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), "서버에서 게시판 정보를 받아오지 못하였습니다.", Toast.LENGTH_LONG).show();

                QnAInfo test = new QnAInfo("동해물과 백두산이 마르고 닳도록", "홍길동", "2023-06-20", "0", "photo", "하느님이 보우하사 우리나라 만세");
                qnAInfos.add(test);
                QnAAdapter adapter = new QnAAdapter(getContext(), qnAInfos);
                listView.setAdapter(adapter);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getContext(), ArticleActivity.class);
                /* putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값 */
                intent.putExtra("title", qnAInfos.get(position).getTitle());
                intent.putExtra("writer", qnAInfos.get(position).getWriter());
                intent.putExtra("date", qnAInfos.get(position).getDate());
                intent.putExtra("contents", qnAInfos.get(position).getContent());
                intent.putExtra("photo", qnAInfos.get(position).getPhoto());
                startActivity(intent);
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