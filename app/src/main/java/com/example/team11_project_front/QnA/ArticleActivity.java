package com.example.team11_project_front.QnA;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.team11_project_front.R;

import org.w3c.dom.Text;


public class ArticleActivity extends AppCompatActivity {
    private TextView summaryTitle, title, date, writer;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Intent intent = getIntent();

        summaryTitle = (TextView) findViewById(R.id.summaryTitle);
        title = (TextView) findViewById(R.id.articleTitle);
        date = (TextView) findViewById(R.id.articleDate);
        writer = (TextView) findViewById(R.id.articleWriter);
        backBtn = (ImageView) findViewById(R.id.backBtn2);

        summaryTitle.setText(intent.getStringExtra("title"));
        title.setText(intent.getStringExtra("title"));
        date.setText(intent.getStringExtra("date"));
        writer.setText(intent.getStringExtra("writer"));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}