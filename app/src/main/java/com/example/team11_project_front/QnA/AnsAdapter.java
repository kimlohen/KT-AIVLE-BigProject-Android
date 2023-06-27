package com.example.team11_project_front.QnA;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.team11_project_front.Data.AnsInfo;
import com.example.team11_project_front.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AnsAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<AnsInfo> list;
    Bitmap bitmap;

    public AnsAdapter(Context context, ArrayList<AnsInfo> data) {
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
        View view = View.inflate(mContext, R.layout.itemlist_ans, null);
        TextView writer = (TextView) view.findViewById(R.id.ansName);
        TextView date =(TextView) view.findViewById(R.id.ansDate);
        TextView ansText = (TextView) view.findViewById(R.id.ansText);
        TextView callBtn = (TextView) view.findViewById(R.id.callBtn);
        ImageView hos_profile = (ImageView) view.findViewById(R.id.imageView3);
        TextView hos_name = (TextView) view.findViewById(R.id.ansHosName);
        TextView hos_intro = (TextView) view.findViewById(R.id.ansIntro);

        hos_name.setText(list.get(i).getHos_name());
        hos_intro.setText(list.get(i).getHos_intro());
        date.setText(list.get(i).getDate());
        ansText.setText(list.get(i).getContent());
        if(hos_name.getText().equals("ChatGPT")){
            callBtn.setVisibility(View.GONE);
            writer.setText("");
        }else{
            writer.setText(list.get(i).getWriter());
        }

        Thread mThread = new Thread(){
            @Override
            public void run(){
                try {
                    URL url = new URL(list.get(i).getHos_profile());
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
            hos_profile.setImageBitmap(bitmap.createScaledBitmap(bitmap, 80, 80, false));
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent("android.intent.action.DIAL", Uri.parse(list.get(i).getConnect())));
            }
        });

        return view;
    }
}
