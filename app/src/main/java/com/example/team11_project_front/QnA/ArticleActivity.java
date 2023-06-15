package com.example.team11_project_front.QnA;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.team11_project_front.R;

import org.w3c.dom.Text;


public class ArticleActivity extends AppCompatActivity {
    private TextView summaryTitle, title, date, writer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Intent intent = getIntent();

        summaryTitle = (TextView) findViewById(R.id.summaryTitle);
        title = (TextView) findViewById(R.id.articleTitle);
        date = (TextView) findViewById(R.id.articleDate);
        writer = (TextView) findViewById(R.id.articleWriter);
        summaryTitle.setText(intent.getStringExtra("title"));
        title.setText(intent.getStringExtra("title"));
        date.setText(intent.getStringExtra("date"));
        writer.setText(intent.getStringExtra("writer"));
    }
}