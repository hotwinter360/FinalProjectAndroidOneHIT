package com.example.finalprojectandroid1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
    TextView tv_score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        tv_score = (TextView) findViewById(R.id.tv_score);
        EditText nameEt1 = findViewById(R.id.Name_input);
        String alert_msg_no_name = getResources().getString(R.string.alert_msg_no_name_entered);
        String alert_title_no_name = getResources().getString(R.string.alert_title_no_name_entered);
        String Best1 = getResources().getString(R.string.Best1);
        String Best2 = getResources().getString(R.string.Best2);
        String Best3 = getResources().getString(R.string.Best3);
        String lastscore = getResources().getString(R.string.lastscore);
        int best1,best2,best3;

        Button addBtn = findViewById(R.id.Add);
        Button playagainbtn = findViewById(R.id.playAgainbtn);
        SharedPreferences sp = getSharedPreferences("Score",MODE_PRIVATE);

        int LastScore = sp.getInt("Score",0);
        best1 = sp.getInt("Best1",0);
        best2 = sp.getInt("Best2",0);
        best3 = sp.getInt("Best3",0);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = tv_score.getText().toString();
                if ((name.equals("") || name.matches("[0-9]+"))) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Score.this);
                    alertDialog.setTitle("alert_msg_no_name");
                    alertDialog.setMessage("alert_title_no_name_entered");
                    alertDialog.setPositiveButton("OK", null);
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                } else {
                Toast.makeText(Score.this, (String) name, Toast.LENGTH_SHORT).show();
                }
                Animation animation = AnimationUtils.loadAnimation(Score.this, R.anim.bounce);
                MyBounce interpolator = new MyBounce(0.2,20);
                animation.setInterpolator(interpolator);
                addBtn.startAnimation(animation);

            }
        });
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
        tv_score.setText((String) lastscore + ": " + LastScore + " " + "\n" + (String) Best1 + ": " + best1 + " " + "\n" + (String) Best2 + ": " + best2  + " " + "\n" + (String) Best3 + ": " + best3  + " " + "\n");
        playagainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tv_score.getText().toString();
                if ((name.equals("") || name.matches("[0-9]+"))) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Score.this);
                    alertDialog.setTitle("alert_msg_no_number");
                    alertDialog.setMessage("alert_title_no_number_entered");
                    alertDialog.setPositiveButton("OK", null);
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                Animation animation = AnimationUtils.loadAnimation(Score.this, R.anim.bounce);
                MyBounce interpolator = new MyBounce(0.2,20);
                animation.setInterpolator(interpolator);
                playagainbtn.startAnimation(animation);
            }
        });
    }

}