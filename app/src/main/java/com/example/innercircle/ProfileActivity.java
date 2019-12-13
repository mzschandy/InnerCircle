package com.example.innercircle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    private static final String SAVED_TEXT = "text";
    private SharedPreferences sharedPrefs;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        editText = findViewById(R.id.aboutText);
        editText.setText(sharedPrefs.getString(SAVED_TEXT, null));

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        Intent intent5 = new Intent(ProfileActivity.this, ActivityFeed.class);
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

    public void onPauseClicked (View view){
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void onPlayClicked (View view) {
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.creativeminds);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopMediaPlayer();
                }
            });
        }

        mediaPlayer.start();
    }

    public void onStopClicked (View view) {
        stopMediaPlayer();
    }

    private void stopMediaPlayer(){
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void onPause(){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(SAVED_TEXT, editText.getText().toString());
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopMediaPlayer();
    }
}
