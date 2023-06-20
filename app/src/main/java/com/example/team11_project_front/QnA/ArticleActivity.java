package com.example.team11_project_front.QnA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.team11_project_front.R;

import org.w3c.dom.Text;


public class ArticleActivity extends AppCompatActivity {
    private TextView summaryTitle, title, date, writer, question;
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
        String dateText = intent.getStringExtra("date");
        date.setText(dateText.split("T")[0] + " " + dateText.split("T")[1].substring(0, 8));
        writer.setText(intent.getStringExtra("writer"));

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        Bundle bundle = new Bundle();

        String contents = intent.getStringExtra("contents");
        bundle.putString("contents", contents);
        ArticleFragment articleFragment = new ArticleFragment();
        articleFragment.setArguments(bundle);
        transaction.replace(R.id.articleFragmentContainerView, articleFragment).commit();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}