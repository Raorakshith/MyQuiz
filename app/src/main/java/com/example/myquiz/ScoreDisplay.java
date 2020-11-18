package com.example.myquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ScoreDisplay extends AppCompatActivity {
private TextView ctext;
String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_display);
        ctext=findViewById(R.id.congtext);
        text=getIntent().getStringExtra("totalscore");
        ctext.setText("Congratulations! You've scored "+text+"/10");
    }
}