package com.memory.thevoid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
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

        mHighScoreButton = (Button) findViewById(R.id.hs_button);
        mHighScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HighScoreDisplayActivity.class);
                startActivity(i);
            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent = null;
        switch(item.getItemId()) {
            case R.id.tile4:
                intent = new Intent(this, GameActivity4.class);
                startActivity(intent);
                return true;
            case R.id.tile6:
                intent = new Intent(this, GameActivity6.class);
                startActivity(intent);
                return true;
            case R.id.tile8:
                intent = new Intent(this, GameActivity8.class);
                startActivity(intent);
                return true;
            case R.id.tile10:
                intent = new Intent(this, GameActivity10.class);
                startActivity(intent);
                return true;
            case R.id.tile12:
                intent = new Intent(this, GameActivity12.class);
                startActivity(intent);
                return true;
            case R.id.tile14:
                intent = new Intent(this, GameActivity14.class);
                startActivity(intent);
                return true;
            case R.id.tile16:
                intent = new Intent(this, GameActivity16.class);
                startActivity(intent);
                return true;
            case R.id.tile18:
                intent = new Intent(this, GameActivity18.class);
                startActivity(intent);
                return true;
            case R.id.tile20:
                intent = new Intent(this, GameActivity20.class);
                startActivity(intent);
                return true;
            default:
                intent = new Intent(this, GameActivity8.class);
                startActivity(intent);
                return true;
        }
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