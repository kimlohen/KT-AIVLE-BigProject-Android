package com.example.team11_project_front.QnA;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.team11_project_front.Data.QnAInfo;
import com.example.team11_project_front.R;

import java.util.ArrayList;

public class QnAAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<QnAInfo> list;

    public QnAAdapter(Context context, ArrayList<QnAInfo> data) {
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
        View view = View.inflate(mContext, R.layout.itemlist_qna, null);
        TextView title = (TextView)view.findViewById(R.id.title);
        TextView writer = (TextView) view.findViewById(R.id.writer);
        TextView date =(TextView) view.findViewById(R.id.date);
        TextView ansNum = (TextView) view.findViewById(R.id.ansNum);

        title.setText(list.get(i).getTitle());
        writer.setText(list.get(i).getWriter());
        date.setText(list.get(i).getDate());
        ansNum.setText(list.get(i).getAnsNum());

        return view;
    }
}
