package com.example.myquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ScoreDisplay extends AppCompatActivity {
private TextView ctext;
String text,text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_display);
        ctext=findViewById(R.id.congtext);
        text=getIntent().getStringExtra("totalscore");
        text1=getIntent().getStringExtra("maximumscore");
        ctext.setText("Congratulations! You've scored "+text+"/"+text1);
    }
}