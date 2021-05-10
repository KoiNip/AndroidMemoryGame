package com.memory.thevoid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Custom_Dialog extends AppCompatDialogFragment {

    private EditText username;
    private int score = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Input Your Username")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (username.getText().toString() == null || username.getText().toString().length() < 1) {
                            Toast.makeText(getContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            saveScore(username.getText().toString() + " " + score, score);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        username = view.findViewById(R.id.etUsername);

        return builder.create();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void saveScore(String text, int score) throws IOException {

        Log.i("Custom_Dialog", text + " " + score);
        File file = new File("scores.txt");
        Scanner inputFile = new Scanner(file);

        int pos = 0;
        while (inputFile.hasNext()) {
            String line = inputFile.nextLine();
            pos++;
            int fileScore = Integer.parseInt(line.substring(line.lastIndexOf(" ")));
            if (fileScore > score)
                continue;
            else {
                File temp = new File("temp");
                writeScores(file, temp, text, pos);
                copyFileUsingStream(temp, file);
                temp.delete();
                return;
            }
        }
        writeScores(null, file, text, 0);
        inputFile.close();
    }

    private static void writeScores(File source, File dest, String text, int pos) throws IOException {
        Scanner scanner = new Scanner(source);
        FileWriter fw = new FileWriter(dest);
        PrintWriter pw = new PrintWriter(fw);

        if (dest == null)
            pw.append(text);
        else {
            int i = 1;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (i == pos)
                    pw.append(text);
                else {
                    pw.append(line);
                }
                i++;
            }
        }

        if (scanner != null)
            scanner.close();

        pw.close();
        fw.close();
    }

    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) >= 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
}