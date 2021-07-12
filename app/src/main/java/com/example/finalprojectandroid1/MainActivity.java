package com.example.finalprojectandroid1;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_main_screen);


        play = (Button) findViewById(R.id.playBtn);
            play.setOnClickListener((new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent  intent = new Intent(MainActivity.this, LevelsActivity.class);
                  startActivity(intent);
            }
             }));
   }

}