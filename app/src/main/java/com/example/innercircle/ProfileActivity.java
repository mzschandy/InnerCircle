package com.example.innercircle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        Intent intent5 = new Intent(ProfileActivity.this, FeedActivity.class);
                        startActivity(intent5);
                        item.setChecked(true);
                        break;
                    case R.id.nav_camera:
                        Intent intent6 = new Intent(ProfileActivity.this, CameraActivity.class);
                        startActivity(intent6);
                        item.setChecked(true);
                        break;
                    case R.id.nav_profile:
                        break;
                }

                return true;
            }
        });
    }
}