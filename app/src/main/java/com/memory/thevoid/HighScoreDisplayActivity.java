package com.memory.thevoid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class HighScoreDisplayActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    private static final String FILE_NAME4 = "scores4.txt";
    private static final String FILE_NAME6 = "scores6.txt";
    private static final String FILE_NAME8 = "scores8.txt";
    private static final String FILE_NAME10 = "scores10.txt";
    private static final String FILE_NAME12 = "scores12.txt";
    private static final String FILE_NAME14 = "scores14.txt";
    private static final String FILE_NAME16 = "scores16.txt";
    private static final String FILE_NAME18 = "scores18.txt";
    private static final String FILE_NAME20 = "scores20.txt";

    TextView score1;
    TextView score2;
    TextView score3;
    Button selectBoard;
    String file_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_display);
        score1 = (TextView) findViewById(R.id.score_1);
        score2 = (TextView) findViewById(R.id.score_2);
        score3 = (TextView) findViewById(R.id.score_3);
        selectBoard = (Button) findViewById(R.id.board_select);

        selectBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    public void setTextViews() {
        FileInputStream fis = null;
        try {
            String text;
            int index = 0;
            fis = openFileInput(file_name);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            while((text = br.readLine()) != null) {
                if(index == 0) {
                    score1.setText(text);
                }
                if(index == 2) {
                    score2.setText(text);
                }
                if(index == 4) {
                    score3.setText(text);
                    break;
                }
                index++;
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent = null;
        switch(item.getItemId()) {
            case R.id.tile4:
                file_name = FILE_NAME4;
                setTextViews();
                return true;
            case R.id.tile6:
                file_name = FILE_NAME6;
                setTextViews();
                return true;
            case R.id.tile8:
                file_name = FILE_NAME8;
                setTextViews();
                return true;
            case R.id.tile10:
                file_name = FILE_NAME10;
                setTextViews();
                return true;
            case R.id.tile12:
                file_name = FILE_NAME12;
                setTextViews();
                return true;
            case R.id.tile14:
                file_name = FILE_NAME14;
                setTextViews();
                return true;
            case R.id.tile16:
                file_name = FILE_NAME16;
                setTextViews();
                return true;
            case R.id.tile18:
                file_name = FILE_NAME18;
                setTextViews();
                return true;
            case R.id.tile20:
                file_name = FILE_NAME20;
                setTextViews();
                return true;
            default:
                file_name = FILE_NAME4;
                setTextViews();
                return true;
        }
    }
}