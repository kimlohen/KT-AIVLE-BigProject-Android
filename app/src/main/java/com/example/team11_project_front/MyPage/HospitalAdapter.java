package com.example.team11_project_front.MyPage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

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
        TextView prof = (TextView) view.findViewById(R.id.prof);
        TextView introduction = (TextView) view.findViewById(R.id.introduction);
        TextView location = (TextView) view.findViewById(R.id.location);

        AppCompatButton update = (AppCompatButton) view.findViewById(R.id.ho_update);


        image.setImageResource(R.drawable.person_icon);
        name.setText(list.get(i).getName());
        prof.setText(list.get(i).getPart());
        location.setText(list.get(i).getLocation());
        introduction.setText(list.get(i).getIntroduction());

        return view;
    }
}
