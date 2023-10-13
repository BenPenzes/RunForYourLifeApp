package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button exerciseBtn;
    Button recordsBtn;
    Button exitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        exerciseBtn = findViewById(R.id.exerciseBtn);
        recordsBtn = findViewById(R.id.recordsBtn);
        exitBtn = findViewById(R.id.exitBtn);
        exerciseBtn.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(this, ExerciseLaunchActivity.class);
            startActivity(intent);
        });
        recordsBtn.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(this, RecordsActivity.class);
            startActivity(intent);
        });
        exitBtn.setOnClickListener(view -> {
            finish();
        });
    }
}