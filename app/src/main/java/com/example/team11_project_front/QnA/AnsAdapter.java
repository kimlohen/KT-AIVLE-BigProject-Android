package com.example.team11_project_front.QnA;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.team11_project_front.Data.AnsInfo;
import com.example.team11_project_front.Data.QnAInfo;
import com.example.team11_project_front.R;

import java.util.ArrayList;

public class AnsAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<AnsInfo> list;

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

        writer.setText(list.get(i).getWriter());
        date.setText(list.get(i).getDate());
        ansText.setText(list.get(i).getContent());

        return view;
    }
}
