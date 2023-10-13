package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class CountDownActivity extends AppCompatActivity {
    ImageView countDownIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        countDownIv = findViewById(R.id.countDownIv);
        countDownIv.setBackgroundResource(R.drawable.countdown_animation);
        AnimationDrawable countDownAnim = (AnimationDrawable) countDownIv.getBackground();
        countDownAnim.start();
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
             @Override
             public void run() {
                 Intent intent = new Intent();
                 intent.setClass(CountDownActivity.this, ExerciseActivity.class);
                 startActivity(intent);
             }
         }, 5000);
    }
}