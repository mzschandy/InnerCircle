package com.example.innercircle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        break;
                    case R.id.nav_camera:
                        Intent intent1 = new Intent(FeedActivity.this, CameraActivity.class);
                        startActivity(intent1);
                        item.setChecked(true);
                        break;
                    case R.id.nav_profile:
                        Intent intent2 = new Intent(FeedActivity.this, ProfileActivity.class);
                        startActivity(intent2);
                        item.setChecked(true);
                        break;
                }

                return true;

            }
        });

    }

}
