package com.example.innercircle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPlay,btnBack,btnFor;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private Runnable runnable;
    private Handler handler;

    // MediaPlayer player;
    private static final String SAVED_TEXT = "Hi I'm a senior at the University of Florida studying Digital Arts and Sciences!";
    private SharedPreferences sharedPrefs;
    private EditText editText;

    FirebaseUser fbUser;
    DatabaseReference database;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ImageAdapter mAdapter;
    ArrayList<Image> images = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        btnPlay = findViewById(R.id.btnPlay);
        btnBack = findViewById(R.id.btnBack);
        btnFor = findViewById(R.id.btnFor);
        handler = new Handler();
        seekBar = findViewById(R.id.seekbar);

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.creativeminds);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopMediaPlayer();
                }
            });
        }

        btnFor.setOnClickListener((View.OnClickListener) this);
        btnBack.setOnClickListener(this);
        btnPlay.setOnClickListener(this);

        if (mediaPlayer != null) {
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    seekBar.setMax(mediaPlayer.getDuration());
                    mediaPlayer.start();
                    changeSeekbar();
                }
            });
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void changeSeekbar() {
        if (mediaPlayer != null) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());

            if (mediaPlayer.isPlaying()) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        changeSeekbar();
                    }
                };
                handler.postDelayed(runnable, 1000);
            }
        }

    }

    @Override
    public void onClick(View view) {
        if (mediaPlayer != null) {
            switch (view.getId()) {
                case R.id.btnPlay:
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        btnPlay.setText(">");
                    } else {
                        mediaPlayer.start();
                        btnPlay.setText("||");
                        changeSeekbar();
                    }
                    break;
                case R.id.btnFor:
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 5000);
                    break;
                case R.id.btnBack:
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 5000);
                    break;
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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

    protected void onStop() {
        super.onStop();

        stopMediaPlayer();
    }
}
