package com.example.finalprojectandroid1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LevelsActivity extends AppCompatActivity {
    private Button startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels_screen);


        startGame = (Button) findViewById(R.id.startBtn);
        startGame.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LevelsActivity.this, ArcadePong.class);
                startActivity(intent);
            }
        }));
    }
}
