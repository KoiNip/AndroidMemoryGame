package com.memory.thevoid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HighScoreActivity extends AppCompatActivity {
    private static final String FILE_NAME4 = "scores4.txt";
    private static final String FILE_NAME6 = "scores6.txt";
    private static final String FILE_NAME8 = "scores8.txt";
    private static final String FILE_NAME10 = "scores10.txt";
    private static final String FILE_NAME12 = "scores12.txt";
    private static final String FILE_NAME14 = "scores14.txt";
    private static final String FILE_NAME16 = "scores16.txt";
    private static final String FILE_NAME18 = "scores18.txt";
    private static final String FILE_NAME20 = "scores20.txt";

    EditText initials;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        Intent intent = getIntent();
        int gameSize = intent.getIntExtra("com.memory.thevoid.gamesize", 4);
        int score = intent.getIntExtra("com.memory.thevoid.score", 4);

        initials = (EditText) findViewById(R.id.initals_hs);
        back = (Button) findViewById(R.id.back_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v, gameSize, score);
                finish();
            }
        });
    }

    public void save(View v, int gameSize, int score) {
        String hs_initials = initials.getText().toString();
        FileOutputStream fos = null;
        String file_name;
        String final_write;

        switch(gameSize) {
            case 4:
                file_name = FILE_NAME4;
                break;
            case 6:
                file_name = FILE_NAME6;
                break;
            case 8:
                file_name = FILE_NAME8;
                break;
            case 10:
                file_name = FILE_NAME10;
                break;
            case 12:
                file_name = FILE_NAME12;
                break;
            case 14:
                file_name = FILE_NAME14;
                break;
            case 16:
                file_name = FILE_NAME16;
                break;
            case 18:
                file_name = FILE_NAME18;
                break;
            case 20:
                file_name = FILE_NAME20;
                break;
            default:
                file_name = FILE_NAME4;
                break;
        }

        final_write = hs_initials + " - " + score + "\n";

        try {
            fos = openFileOutput(file_name, MODE_APPEND);
            fos.write(final_write.getBytes());

            initials.getText().clear();
            Toast.makeText(this, "Score saved!", Toast.LENGTH_SHORT).show();
            Log.i("HighScoreActivity", "Saved to " + getFilesDir() + "/" + file_name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}