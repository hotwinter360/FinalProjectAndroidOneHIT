package com.example.finalprojectandroid1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;




public class MainActivity extends AppCompatActivity {

    private Button play;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button) findViewById(R.id.playBtn);
        play.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openArcadePong();
            }
        }));
    }
    public void openArcadePong(){
        Intent  intent = new Intent(this,ArcadePong.class);
        startActivity(intent);
    }

}