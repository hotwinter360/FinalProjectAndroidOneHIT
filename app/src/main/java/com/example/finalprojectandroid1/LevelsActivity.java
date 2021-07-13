package com.example.finalprojectandroid1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class LevelsActivity extends Activity implements View.OnClickListener {

    static int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels_screen);

        ImageButton button1 = findViewById(R.id.Button1);
        ImageButton button2 = findViewById(R.id.Button2);
        ImageButton button3 = findViewById(R.id.Button3);
        ImageButton button4 = findViewById(R.id.Button4);
        ImageButton button5 = findViewById(R.id.Button5);
        ImageButton button6 = findViewById(R.id.Button6);
        button1.setOnClickListener((View.OnClickListener) this);
        button2.setOnClickListener((View.OnClickListener) this);
        button3.setOnClickListener((View.OnClickListener) this);
        button4.setOnClickListener((View.OnClickListener) this);
        button5.setOnClickListener((View.OnClickListener) this);
        button6.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Button1:
                level=3;
                break;
            case R.id.Button2:
                level = 4;
                break;
            case R.id.Button3:
                level = 5;
                break;
            case R.id.Button4:
                level = 6;
                break;
            case R.id.Button5:
                level = 7;
                break;
            case R.id.Button6:
                level = 8;
                break;
        }
        Intent intent = new Intent(LevelsActivity.this, ArcadePong.class);
        startActivity(intent);
    }
}




