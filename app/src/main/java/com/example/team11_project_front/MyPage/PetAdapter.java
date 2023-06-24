package com.example.team11_project_front.MyPage;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.team11_project_front.ChangePetActivity;
import com.example.team11_project_front.Data.PetInfo;
import com.example.team11_project_front.R;
import com.example.team11_project_front.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<PetInfo> list;
    private int number;

    private RetrofitClient retrofitClient;
    private com.example.team11_project_front.API.deletePetApi deletePetApi;

    public PetAdapter(Context context, ArrayList<PetInfo> data) {
        mContext = context;
        list = data;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = View.inflate(mContext, R.layout.itemlist_pet_info, null);
        ImageView petImage = (ImageView) view.findViewById(R.id.petImage);
        TextView name = (TextView) view.findViewById(R.id.petName);
        TextView species = (TextView) view.findViewById(R.id.species);
        TextView birth = (TextView) view.findViewById(R.id.birth);
        TextView gender = (TextView) view.findViewById(R.id.gender);
        number = i;

        AppCompatButton update = (AppCompatButton) view.findViewById(R.id.update);
        AppCompatButton delete = (AppCompatButton) view.findViewById(R.id.delete);

        petImage.setImageResource(R.drawable.person_icon);
        name.setText(list.get(i).getName());
        birth.setText(list.get(i).getBirth());
        String CheckGender = list.get(i).getGender();

        if(CheckGender.equals("M")) {
            gender.setText("수컷");
            
        }
        else if(CheckGender.equals("F")) {
            gender.setText("암컷");
        }
        else {
            gender.setText(CheckGender);
        }
        String CheckSpecies = list.get(i).getSpecies();
        if(CheckSpecies.equals("Cat")||CheckSpecies.equals("cat")) {
            species.setText("고양이");
        }
        else if(CheckSpecies.equals("Dog")||CheckSpecies.equals("dog")) {
            species.setText("강아지");
        }
        else {
            species.setText(CheckSpecies);
        }


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Pid = list.get(i).getId();
                String Pname = list.get(i).getName();
                String Pgender = list.get(i).getGender();
                String Pspecies = list.get(i).getSpecies();
                String Pbirth = list.get(i).getBirth();
                Intent intent = new Intent(mContext, ChangePetActivity.class);

                intent.putExtra("id", Pid);
                intent.putExtra("name", Pname);
                intent.putExtra("gender", Pgender);
                intent.putExtra("species", Pspecies);
                intent.putExtra("birth", Pbirth);
                mContext.startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("정말 삭제하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String Pid = list.get(i).getId();
                                deletePet(Pid,i);
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 삭제 취소 시 동작할 내용을 여기에 작성
                                // 예를 들면 아무 동작도 하지 않거나 취소 메시지를 표시할 수 있습니다.
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        return view;
    }

    void deletePet(String PID,int number) {


        retrofitClient = RetrofitClient.getInstance();
        com.example.team11_project_front.API.deletePetApi deletePetApi = RetrofitClient.getRetrofitDeletePetInterface();
        deletePetApi.getDeletePetResponse("Bearer " + getPreferenceString("acessToken"),PID).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 204) {
                    Toast.makeText(mContext, "삭제하였습니다.", Toast.LENGTH_LONG).show();
                    list.remove(number);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "삭제하는 과정에서 실패하였습니다.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(mContext, "요청을 받아오지 못 했습니다.", Toast.LENGTH_LONG).show();
            }
        });

    }

    public String getPreferenceString(String key) {
        SharedPreferences pref = mContext.getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        return pref.getString(key, "");
    }
}
