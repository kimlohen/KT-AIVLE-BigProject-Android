package com.example.team11_project_front.MyPage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.team11_project_front.Data.PostedList;
import com.example.team11_project_front.R;

import java.util.ArrayList;

public class PostedAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<PostedList> list;

    public PostedAdapter(Context context, ArrayList<PostedList> data) {
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
        View view = View.inflate(mContext, R.layout.itemlist_posted, null);
        TextView title = (TextView)view.findViewById(R.id.title);
        TextView pet = (TextView) view.findViewById(R.id.pet);
        TextView date =(TextView) view.findViewById(R.id.date);

        title.setText(list.get(i).getTitle());
        pet.setText(list.get(i).getPet());
        date.setText(list.get(i).getDate());

        return view;
    }
}
