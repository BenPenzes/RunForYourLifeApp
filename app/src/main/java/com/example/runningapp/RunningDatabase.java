package com.example.runningapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RunningDatabase {
    private static SQLiteDatabase dbHelper;
    public RunningDatabase(Context context){
        dbHelper = new DatabaseHelper(context).getWritableDatabase();
    }
    public void insertRunningRecord(String datetime, String overallTime, double overallKms, int avgPace, int numOfSteps) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME_DATETIME, datetime);
        values.put(DatabaseHelper.COLUMN_NAME_OVERALL_TIME, overallTime);
        values.put(DatabaseHelper.COLUMN_NAME_OVERALL_KMS, overallKms);
        values.put(DatabaseHelper.COLUMN_NAME_AVG_PACE, avgPace);
        values.put(DatabaseHelper.COLUMN_NAME_NUM_OF_STEPS, numOfSteps);
        dbHelper.insert(DatabaseHelper.TABLE_NAME, null, values);
    }
    public Cursor listRecords(){
        Cursor c = dbHelper.query(DatabaseHelper.TABLE_NAME,
                new String[]
                        {
                                DatabaseHelper.COLUMN_NAME_DATETIME,
                                DatabaseHelper.COLUMN_NAME_OVERALL_TIME,
                                DatabaseHelper.COLUMN_NAME_OVERALL_KMS,
                                DatabaseHelper.COLUMN_NAME_AVG_PACE,
                                DatabaseHelper.COLUMN_NAME_NUM_OF_STEPS
                        },
                null, null, null, null, null);
        if (c.moveToFirst() || c.getCount() != 0){
            c.moveToFirst();
        } else {
            c.close();
            return null;
        }
        return c;
    }
}
