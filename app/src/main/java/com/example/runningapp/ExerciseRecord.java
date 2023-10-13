package com.example.runningapp;

public class ExerciseRecord {
    public String datetime;
    public String overallTime;
    public double overallKms;
    public int avgPace;
    public int numOfSteps;
    public ExerciseRecord(String datetime, String overallTime, double overallKms, int avgPace, int numOfSteps){
        this.datetime = datetime;
        this.overallTime = overallTime;
        this.overallKms = overallKms;
        this.avgPace = avgPace;
        this.numOfSteps = numOfSteps;
    }
}
