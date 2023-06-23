package com.example.team11_project_front.MyPage;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.team11_project_front.ChangeHospitalActivity;
import com.example.team11_project_front.Data.HospitalInfo;
import com.example.team11_project_front.R;

import java.util.ArrayList;


public class HospitalAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<HospitalInfo> list;

    public HospitalAdapter(Context context, ArrayList<HospitalInfo> data) {
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
        View view = View.inflate(mContext, R.layout.itemlist_hospital_info, null);
        ImageView image = (ImageView) view.findViewById(R.id.hospitalImage);
        TextView name = (TextView) view.findViewById(R.id.hospitalName);
        TextView prof = (TextView) view.findViewById(R.id.pNumber);
        TextView introduction = (TextView) view.findViewById(R.id.introduction);
        TextView address = (TextView) view.findViewById(R.id.location);

        AppCompatButton update = (AppCompatButton) view.findViewById(R.id.ho_update);


        // 여기가 이미지 넣는 곳.
        Glide.with(mContext)
                .load(list.get(i).getPhoto())
                .into(image);
        name.setText(list.get(i).getName());
        prof.setText(list.get(i).getTel());
        address.setText(list.get(i).getAddress());
        introduction.setText(list.get(i).getIntroduction());


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Hid = list.get(i).getId();
                String Hname = list.get(i).getName();
                String Htel = list.get(i).getTel();
                String Haddress = list.get(i).getAddress();
                String Hintro = list.get(i).getIntroduction();
                String Hphoto = list.get(i).getPhoto();
                Intent intent = new Intent(mContext, ChangeHospitalActivity.class);

                intent.putExtra("id", Hid);
                intent.putExtra("name", Hname);
                intent.putExtra("tel", Htel);
                intent.putExtra("address", Haddress);
                intent.putExtra("intro", Hintro);
                intent.putExtra("photo", Hphoto);
                mContext.startActivity(intent);
            }
        });

        return view;
    }
}
