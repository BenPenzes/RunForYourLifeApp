package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ExerciseLaunchActivity extends AppCompatActivity {
    Button runBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_launch);
        runBtn = findViewById(R.id.runBtn);
        runBtn.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(this, CountDownActivity.class);
            startActivity(intent);
        });

    }
}