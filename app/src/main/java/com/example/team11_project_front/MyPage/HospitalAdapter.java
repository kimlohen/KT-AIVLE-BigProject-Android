package com.example.team11_project_front.MyPage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.team11_project_front.Data.HospitalInfo;
import com.example.team11_project_front.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HospitalAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<HospitalInfo> list;
    Bitmap bitmap;

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
        TextView location = (TextView) view.findViewById(R.id.location);

        AppCompatButton update = (AppCompatButton) view.findViewById(R.id.ho_update);

        Thread mthread = new Thread(){
            @Override
            public void run(){
                try{
                    URL url = new URL(list.get(i).getHos_profile_img());
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

        mthread.start();
        try{
            mthread.join();
            image.setImageBitmap(bitmap.createScaledBitmap(bitmap, 120, 120, false));
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        name.setText(list.get(i).getName());
        prof.setText(list.get(i).getPart());
        location.setText(list.get(i).getLocation());
        introduction.setText(list.get(i).getIntroduction());

        return view;
    }
}
