package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ExerciseActivity extends AppCompatActivity implements SensorEventListener{
    private String startOfExercise = "";
    private int previousKm = 0;
    private int pace = 0;
    private int paceSum = 0;
    private int avgPaceDivisor = 1;
    private final int paceMeasurementInterval = 3; // every x seconds the pace is measured
    private double previousSeconds = 0.0;
    private double previousMetersRun = 0.0;
    private int steps = 0;
    private int initialSteps = 0;
    private double metersRun = 0.0;
    private final double stride = 0.75; // constant 75 cm steps
    private boolean firstTimeSensing = true;
    private RunningDatabase runningDatabase;
    private TextView timerTv;
    private TextView kilometersTv;
    private TextView paceTv;
    private TextView stepCounterTv;
    private Button stopBtn;
    private SensorManager mSManager;
    private Sensor stepCounterSensor;
    Timer timer;
    TimerTask timertask;
    Double time = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        runningDatabase = new RunningDatabase(this);
        timerTv = findViewById(R.id.timerTv);
        kilometersTv = findViewById(R.id.kilometersTv);
        paceTv = findViewById(R.id.paceTv);
        stepCounterTv = findViewById(R.id.stepCounterTv);
        stopBtn = findViewById(R.id.stopBtn);
        Date currDate = Calendar.getInstance().getTime();
        startOfExercise = DateFormat.getInstance().format(currDate);
        stopBtn.setOnClickListener(view -> {
            String overallTime = timerTv.getText().toString();
            runningDatabase.insertRunningRecord(startOfExercise, overallTime, metersRun/1000, paceSum/avgPaceDivisor, steps);
            onBackPressed();
        });
        mSManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if (mSManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            Log.d("StepCounterSensor", "Yes, a Step Counter! :D");
            stepCounterSensor = mSManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        } else {
            Log.d("StepCounterSensor", "No Step Counter! :(");
        }
        timer = new Timer();
        startTimer();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ExerciseActivity.this, ExerciseLaunchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    private void startTimer() {
        timertask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerTv.setText(getTimerText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timertask, 1000, 1000);
    }
    private String getTimerText() {
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);
        return formatTime(seconds, minutes, hours);
    }
    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSManager.registerListener(this,
                stepCounterSensor,
                SensorManager.SENSOR_DELAY_FASTEST);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSManager.unregisterListener(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            if (firstTimeSensing) {
                initialSteps = (int) sensorEvent.values[0];
                firstTimeSensing = false;
            } else {
                double kmsRun = metersRun/1000;
                double paceForInterval = 0.0;
                String kmString = "";
                steps = (int)sensorEvent.values[0] - initialSteps;
                metersRun = steps * stride;
                if(Math.floor(kmsRun) > previousKm){
                    previousKm = (int) Math.floor(kmsRun);
                    Toast.makeText(this, "You passed " + previousKm + " Km(s)", Toast.LENGTH_LONG).show();
                }
                kmString = String.format("%.2f Kms", kmsRun);
                if(time % paceMeasurementInterval == 0){
                    paceForInterval = metersRun - previousMetersRun;
                    pace = (int)Math.floor((time - previousSeconds) * (1000.0/paceForInterval));
                    previousSeconds = time;
                    previousMetersRun = metersRun;
                    if(0 < pace && pace < 240){
                        Toast.makeText(this, "Slow down!", Toast.LENGTH_LONG).show();
                    }
                    if(pace > 0){
                        paceTv.setText((pace / 60) + "'" + (pace % 60) + "\" Pace");
                        paceSum += pace;
                        avgPaceDivisor++;
                    }
                }
                kilometersTv.setText(kmString);
                stepCounterTv.setText(steps + " Steps");
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d("SENSOR-ACCURACY", sensor.getMinDelay() + "-" + sensor.getMaxDelay());
    }
}