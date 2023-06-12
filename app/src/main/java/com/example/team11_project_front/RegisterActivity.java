package com.example.team11_project_front;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private Switch switchToggle;
    private EditText editTextText;
    private EditText editTextText2;
    private TextView textView;

    private EditText editTextText3;
    private EditText editTextText4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        switchToggle = findViewById(R.id.switchToggle);
        editTextText = findViewById(R.id.editTextText);
        editTextText2 = findViewById(R.id.editTextText2);
        textView = findViewById(R.id.textView);
        editTextText3 = findViewById(R.id.editTextText3);
        editTextText4 = findViewById(R.id.editTextText4);

        switchToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editTextText3.setVisibility(View.VISIBLE);
                    editTextText4.setVisibility(View.VISIBLE);
                } else {
                    editTextText3.setVisibility(View.GONE);
                    editTextText4.setVisibility(View.GONE);
                }
            }
        });

    }

    public void onclick(View v) {

        String pwd = editTextText.getText().toString();
        String pwd2 = editTextText2.getText().toString();

        if (!(pwd.equals(pwd2))) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.INVISIBLE);
        }
    }
}