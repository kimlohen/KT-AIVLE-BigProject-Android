package com.example.team11_project_front.QnA;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team11_project_front.API.qnaApi;
import com.example.team11_project_front.API.refreshApi;
import com.example.team11_project_front.Data.QnAInfo;
import com.example.team11_project_front.Data.QnaListResponse;
import com.example.team11_project_front.Data.QnaResponse;
import com.example.team11_project_front.Data.RefreshRequest;
import com.example.team11_project_front.Data.RefreshResponse;
import com.example.team11_project_front.R;
import com.example.team11_project_front.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QnaFragment extends Fragment {
    private View view;
    private ArrayList<QnAInfo> qnAInfos;
    private TextView beforeBtn, page1, page2, page3, page4, page5, afterBtn, searchText;
    private Button searchBtn;
    private ListView listView;
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
        searchText = (TextView) view.findViewById(R.id.searchText);
        searchBtn = (Button) view.findViewById(R.id.searchBtn);

        beforeBtn.setEnabled(false);
        beforeBtn.setVisibility(beforeBtn.INVISIBLE);

        page1.setTextColor(Color.BLUE);

        listView = (ListView) view.findViewById(R.id.qnaList);
        qnaList("1");

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
                qnaList(page1.getText().toString());
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
                qnaList(page1.getText().toString());
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
                qnaList(page1.getText().toString());
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
                qnaList(page2.getText().toString());
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
                qnaList(page3.getText().toString());
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
                qnaList(page4.getText().toString());
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
                qnaList(page5.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getContext(), ArticleActivity.class);
                /* putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값 */
                intent.putExtra("qId", qnAInfos.get(position).getId());
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

    void qnaList(String index){
        qnAInfos = new ArrayList<>();

        retrofitClient = RetrofitClient.getInstance();
        qnaApi qnaApi = RetrofitClient.getRetrofitQnaInterface();
        qnaApi.getQnaResponse("Bearer " + getPreferenceString("acessToken"), index).enqueue(new Callback<QnaListResponse>() {
            @Override
            public void onResponse(Call<QnaListResponse> call, Response<QnaListResponse> response) {
                Log.d("retrofit", "Data fetch success");
                Log.e("qna", String.valueOf(response.isSuccessful()));
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
                    List<QnaResponse> responses = response.body().getQnaResponses();
                    responses.forEach((element) -> {
                        String qID = element.getId();
                        String title = element.getTitle();
                        String writer = element.getUser_name();
                        String contents = element.getContents();
                        String ansNum = element.getAnswer_count();
                        String photo = element.getPhoto();
                        String date = element.getCreated_at();

                        QnAInfo info = new QnAInfo(qID, title, writer, date, ansNum, photo, contents);
                        qnAInfos.add(info);
                    });
                    QnAAdapter adapter = new QnAAdapter(getContext(), qnAInfos);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
                } else{
                    Toast.makeText(getActivity(), "해당 페이지에 게시물이 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QnaListResponse> call, Throwable t) {
                Log.e("qna", t.getMessage());
                Toast.makeText(getActivity(), "서버에서 게시판 정보를 받아오지 못하였습니다.", Toast.LENGTH_LONG).show();
            }
        });}

}