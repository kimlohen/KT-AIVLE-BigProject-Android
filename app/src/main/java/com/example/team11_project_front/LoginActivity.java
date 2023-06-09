package com.example.team11_project_front;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class LoginActivity extends AppCompatActivity {

    private FragmentManager fm = getSupportFragmentManager();
    private FragmentTransaction ft;

    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ft = fm.beginTransaction();

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {

                        }
                    }
                }
        );

    }

    public void login(View view) {
        Cursor cursor;
        /*
            추후에 test에서 ""로 변경
         */
        String string1 = "test";
        String string2 = "test";

        EditText idEdit = (EditText) findViewById(R.id.editID);
        EditText pwEdit = (EditText) findViewById(R.id.editPW);

        String idText = idEdit.getText().toString();
        String pwText = pwEdit.getText().toString();

        // temp code
        if (string1.equals(idText) & string2.equals(pwText)) {
            Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            resultLauncher.launch(intent);
        }
    }
}
/*
        while (cursor.moveToNext()) {
            string1 = cursor.getString(0);
            string2 = cursor.getString(1);
            if (string1.equals(idText) & string2.equals(pwText)) {
                Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                resultLauncher.launch(intent);
            }
        }
    }
*/
