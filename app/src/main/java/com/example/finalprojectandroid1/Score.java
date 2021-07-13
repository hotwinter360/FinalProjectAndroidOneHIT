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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class Score extends Activity {
    TextView tv_score;
    private EditText editText;
    int best1,best2,best3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        tv_score = (TextView) findViewById(R.id.tv_score);

        String Best1 = getResources().getString(R.string.Best1);
        String Best2 = getResources().getString(R.string.Best2);
        String Best3 = getResources().getString(R.string.Best3);
        String lastscore = getResources().getString(R.string.lastscore);

        Button exit_btn = findViewById(R.id.Exit);
        Button playagainbtn = findViewById(R.id.playAgainbtn);
        int scoreResult = getIntent().getIntExtra("SCORE",0);
        SharedPreferences sp = getSharedPreferences("Score",MODE_PRIVATE);

        int LastScore = sp.getInt("Score",0);
        best1 = sp.getInt("Best1",0);
        best2 = sp.getInt("Best2",0);
        best3 = sp.getInt("Best3",0);

        if (LastScore > best1){
            int temp = best1;
            best1 = LastScore;
            best2 = temp;
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("Best2",best2);
            editor.putInt("Best1",best1);
            editor.apply();
        }
        else if (LastScore > best2){
            int temp = best2;
            best2 = LastScore;
            best3 = temp;
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("Best3",best3);
            editor.putInt("Best2",best2);
            editor.apply();
        }
       else if (scoreResult > LastScore){
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("Best3",best3);
            editor.apply();
        }
        tv_score.setText((String) lastscore + ": " + LastScore + " " + "\n" + (String) Best1 + ": " + best1 + " " + "\n" + (String) Best2 + ": " + best2  + " " + "\n" + (String) Best3 + ": " + best3  + " " + "\n");

        playagainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LevelsActivity.class);
                    startActivity(intent);
                    finish();
                Animation animation = AnimationUtils.loadAnimation(Score.this, R.anim.bounce);
                MyBounce interpolator = new MyBounce(0.2,20);
                animation.setInterpolator(interpolator);
                playagainbtn.startAnimation(animation);
            }
        });
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }

}