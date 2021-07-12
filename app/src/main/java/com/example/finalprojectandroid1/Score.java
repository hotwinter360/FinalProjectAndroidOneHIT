package com.example.finalprojectandroid1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

public class Score extends Activity {
    Bitmap iBackground;
    EditText nameEt1 = findViewById(R.id.Name_input);
    Button addBtn = findViewById(R.id.Add);
    TextView tv_score;
    int Points,best1,best2,best3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        tv_score = (TextView) findViewById(R.id.tv_score);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Score", Context.MODE_PRIVATE);
        int LastScore = sp.getInt("Score",0);
        best1 = sp.getInt("best1",0);
        best2 = sp.getInt("best2",0);
        best3 = sp.getInt("best3",0);

        if (LastScore > best3){
            best3 = LastScore;
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("Best3",best3);
            editor.apply();
        }
        if (LastScore > best2){
            int temp = best2;
            best2 = LastScore;
            best3 = temp;
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("Best3",best3);
            editor.putInt("Best2",best2);
            editor.apply();
        }
        if (LastScore > best1){
            int temp = best1;
            best1 = LastScore;
            best2 = temp;
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("Best2",best2);
            editor.putInt("Best1",best1);
            editor.apply();
        }

        tv_score.setText("Last Score: " + LastScore + "\n" + "Best1: " + best1 + "\n" + "Best2: " + best2 + "\n" + "Best3: " + best3 + "\n");

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEt1.getText().toString();
                if (nameEt1.equals("")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Score.this);
                    alertDialog.setTitle("alert_msg_no_number");
                    alertDialog.setMessage("alert_title_no_number_entered");
                    alertDialog.setPositiveButton("OK", null);
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                } else {
                Toast.makeText(Score.this, (String) name, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}