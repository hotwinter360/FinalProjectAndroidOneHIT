package com.example.finalprojectandroid1;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class LevelsActivity extends Activity {

    private ImageButton startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels_screen);


        startGame = (ImageButton) findViewById(R.id.Button1);
        startGame.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LevelsActivity.this, ArcadePong.class);
                startActivity(intent);
            }
        }));
    }
}
