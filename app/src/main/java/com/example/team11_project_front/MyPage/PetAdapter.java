package com.example.team11_project_front.MyPage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.team11_project_front.Data.PetInfo;
import com.example.team11_project_front.R;

import java.util.ArrayList;

public class PetAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<PetInfo> list;

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

        AppCompatButton update = (AppCompatButton) view.findViewById(R.id.update);
        AppCompatButton delete = (AppCompatButton) view.findViewById(R.id.delete);

        petImage.setImageResource(R.drawable.person_icon);
        name.setText(list.get(i).getName());
        species.setText(list.get(i).getSpecies());
        birth.setText(list.get(i).getBirth());
        gender.setText(list.get(i).getGender());

        return view;
    }
}
