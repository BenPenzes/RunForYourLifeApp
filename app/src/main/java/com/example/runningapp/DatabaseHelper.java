package com.example.runningapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "RunningRecords.db";
    public static String TABLE_NAME = "records";
    public static String COLUMN_NAME_DATETIME = "datetime";
    public static String COLUMN_NAME_OVERALL_TIME = "overallTime";
    public static String COLUMN_NAME_OVERALL_KMS = "overallKms";
    public static String COLUMN_NAME_AVG_PACE = "avgPace";
    public static String COLUMN_NAME_NUM_OF_STEPS = "numOfSteps";
    public static String SQL_CREATE_TABLES =
            "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_NAME_DATETIME + " TEXT PRIMARY KEY, " +
            COLUMN_NAME_OVERALL_TIME + " TEXT, " +
            COLUMN_NAME_OVERALL_KMS + " REAL, " +
            COLUMN_NAME_AVG_PACE + " TEXT, " +
            COLUMN_NAME_NUM_OF_STEPS + " INTEGER)";

    public static String SQL_DELETE_TABLES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_TABLES);
        onCreate(sqLiteDatabase);
    }
}
