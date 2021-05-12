package com.memory.thevoid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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
        String file_name;

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

        reorder(file_name, hs_initials, score);
    }

    public void reorder(String file_name, String hs_initials, int score) {
        FileInputStream fis = null;
        try {
            String text;
            String scoreOne;
            String scoreTwo;
            String scoreThree;
            String scoreNew;
            int score1;
            int score2;
            int score3;
            int index = 0;
            boolean stop = false;
            fis = openFileInput(file_name);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            scoreNew = score + " - " + hs_initials;
            scoreOne = br.readLine();
            if(scoreOne == null) {
                write(file_name, scoreNew, scoreNew, scoreNew);
                scoreOne = scoreNew;
                scoreTwo = scoreNew;
                scoreThree = scoreNew;
                stop = true;
            }
            scoreTwo = br.readLine();
            if(scoreTwo == null && !stop) {
                write(file_name, scoreOne, scoreNew, scoreNew);
                scoreTwo = scoreNew;
                scoreThree = scoreNew;
                stop = true;
            }
            scoreThree = br.readLine();
            if(scoreThree == null && !stop) {
                write(file_name, scoreOne, scoreTwo, scoreNew);
                scoreThree = scoreNew;
                stop = true;
            }

            score1 = Integer.parseInt(scoreOne.substring(0, 1));
            score2 = Integer.parseInt(scoreTwo.substring(0, 1));
            score3 = Integer.parseInt(scoreThree.substring(0, 1));

            Log.i("HighScoreActivity", scoreOne + " " + String.valueOf(score1));
            Log.i("HighScoreActivity", scoreTwo + " " + String.valueOf(score2));
            Log.i("HighScoreActivity", scoreThree + " " + String.valueOf(score3));
            Log.i("HighScoreActivity", scoreNew + " " + String.valueOf(score));

            if(score >= score1 && !stop) {
                write(file_name, scoreNew, scoreOne, scoreTwo);
                stop = true;
            }
            else if (score >= score2 && !stop) {
                write(file_name, scoreOne, scoreNew, scoreTwo);
                stop = true;
            }
            else if(score >= score3 && !stop) {
                write(file_name, scoreOne, scoreTwo, scoreNew);
                stop = true;
            }
            else if(!stop){
                write(file_name, scoreOne, scoreTwo, scoreThree);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void write(String file_name, String entryOne, String entryTwo, String entryThree) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(file_name, MODE_PRIVATE);
            fos.write(entryOne.getBytes());
            fos.write("\n".getBytes());
            fos.write(entryTwo.getBytes());
            fos.write("\n".getBytes());
            fos.write(entryThree.getBytes());

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