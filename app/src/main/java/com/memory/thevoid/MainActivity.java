package com.memory.thevoid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mStartButton;
    private Button mHighScoreButton;
    private Button mToggleMusicButton;
    private static MediaPlayer player;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToggleMusicButton = findViewById(R.id.music_button);
        mToggleMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleMusic(view);
            }
        });

        mStartButton = (Button)findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                startActivity(i);
            }
        });
    }

    public void toggleMusic(View v) {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.song1);
            player.setVolume(100, 100);
            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            Toast.makeText(this, "Song is now playing!", Toast.LENGTH_SHORT).show();
        }
        else {
            player.stop();
            player.release();
            player = null;
            Toast.makeText(this, "MediaPlayer released!", Toast.LENGTH_SHORT).show();
        }
    }
}