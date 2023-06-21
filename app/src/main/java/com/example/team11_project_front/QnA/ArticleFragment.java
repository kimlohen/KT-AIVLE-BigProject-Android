package com.example.team11_project_front.QnA;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team11_project_front.API.pictureApi;
import com.example.team11_project_front.API.qnaApi;
import com.example.team11_project_front.API.refreshApi;
import com.example.team11_project_front.Data.AnsInfo;
import com.example.team11_project_front.Data.PictureResponse;
import com.example.team11_project_front.Data.RefreshRequest;
import com.example.team11_project_front.Data.RefreshResponse;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String contents;
    private View view;
    private ArrayList<AnsInfo> ansInfos;
    private Button ansBtn;
    private Bitmap bitmap;

    private RetrofitClient retrofitClient;
    private com.example.team11_project_front.API.qnaApi picture;
    public ArticleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ArticleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleFragment newInstance(String param1) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contents = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_article, container, false);

        TextView question = view.findViewById(R.id.question);
        ImageView iv_disease = view.findViewById(R.id.diseaseImg);
        try {
            String questionText = this.getArguments().getString("contents");
            String pictureID = this.getArguments().getString("photo");
            question.setText(questionText);
            retrofitClient = RetrofitClient.getInstance();
            pictureApi pictureApi = RetrofitClient.getRetrofitPictureInterface();
            pictureApi.getPictureResponse("Bearer " + getPreferenceString("acessToken"), pictureID).enqueue(new Callback<PictureResponse>() {

                @Override
                public void onResponse(Call<PictureResponse> call, Response<PictureResponse> response) {
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
                    } else if(response.isSuccessful() && response.body() != null){
                        PictureResponse result = response.body();
                        String pictureUrl = result.getPhoto();
                        Thread mThread = new Thread(){
                            @Override
                            public void run(){
                                try {
                                    URL url = new URL(pictureUrl);
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

                        mThread.start();
                        try{
                            mThread.join();
                            iv_disease.setImageBitmap(bitmap.createScaledBitmap(bitmap, 400, 400, false));
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PictureResponse> call, Throwable t) {
                    Log.e("Fail", t.toString());
                    Toast.makeText(getActivity(), "서버에서 사진 정보를 받아오지 못하였습니다.", Toast.LENGTH_LONG).show();
                }
            });

        }catch (NullPointerException e){
            question.setText("질문 내용을 불러오는데 실패하였습니다.");
        }

        ansBtn = (Button) view.findViewById(R.id.ansBtn);
        String is_vet = getPreferenceString("is_vet");
        if(is_vet.equals("false")){
            ansBtn.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        ListView listView = (ListView) view.findViewById(R.id.ansList);
        ansInfos = new ArrayList<>();
        String writer = "홍길동";
        String date = "2023-06-16";
        String content = "안녕하세요. 수의사 홍길동입니다. 질문에 대해 답변드리겠습니다.";
        AnsInfo ansInfo = new AnsInfo(writer, date, content, "");
        ansInfos.add(ansInfo);
        AnsAdapter adapter = new AnsAdapter(getContext(), ansInfos);
        listView.setAdapter(adapter);
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