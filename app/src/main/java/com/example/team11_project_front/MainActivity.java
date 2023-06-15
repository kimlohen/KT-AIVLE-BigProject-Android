package com.example.team11_project_front;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.team11_project_front.MyPage.MyPageFragment;
import com.example.team11_project_front.QnA.QnaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    private MyPageFragment myPageFragment = new MyPageFragment();
    private QnaFragment qnaFragment = new QnaFragment();
    private HomeFragment homeFragment = new HomeFragment();
    private PostFragment postFragment = new PostFragment();
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ft = fm.beginTransaction();

        bottomNavigationView = findViewById(R.id.menu_bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
        bottomNavigationView.setOnItemSelectedListener(new ItemSelectedListener());

        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_layout, homeFragment).commit();
    }

    class ItemSelectedListener implements NavigationBarView.OnItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ft = fm.beginTransaction();
            int itemId = item.getItemId();
            if (itemId == R.id.menu_home) {
                ft.replace(R.id.menu_frame_layout, homeFragment);
                ft.commit();
                return true;
            }
            else if (itemId == R.id.menu_mypage) {
                ft.replace(R.id.menu_frame_layout, myPageFragment);
                ft.commit();
                return true;
            }
            else if (itemId == R.id.menu_post) {
                ft.replace(R.id.menu_frame_layout, postFragment);
                ft.commit();
                return true;
            }
            else if (itemId == R.id.menu_qna) {
                ft.replace(R.id.menu_frame_layout, qnaFragment);
                ft.commit();
                return true;
            }

            return false;
        }
    }
}