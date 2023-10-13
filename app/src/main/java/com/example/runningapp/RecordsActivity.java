package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {
    private ListView recordsLv;
    private MyRecordsAdapter adapter;
    private RunningDatabase runningDatabase;
    private ArrayList<ExerciseRecord> records = new ArrayList<ExerciseRecord>();

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        recordsLv = findViewById(R.id.recordsLv);
        runningDatabase = new RunningDatabase(this);
        Cursor c = runningDatabase.listRecords();
        if (c != null) {
            while (!c.isAfterLast()) {
                records.add(new ExerciseRecord(
                        c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME_DATETIME)),
                        c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME_OVERALL_TIME)),
                        Double.parseDouble(c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME_OVERALL_KMS))),
                        Integer.parseInt(c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME_AVG_PACE))),
                        Integer.parseInt(c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAME_NUM_OF_STEPS)))
                ));
                c.moveToNext();
            }
            adapter = new MyRecordsAdapter(records);
            recordsLv.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No record of any runs!", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    class MyRecordsAdapter extends BaseAdapter {
        ArrayList<ExerciseRecord> myListOfRecords = new ArrayList<ExerciseRecord>();
        public MyRecordsAdapter(ArrayList<ExerciseRecord> myListOfRecords) {
            this.myListOfRecords = myListOfRecords;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View cardView = getLayoutInflater().inflate(R.layout.exercise_record_card, null);
            ExerciseRecord thisRecord = myListOfRecords.get(i);
            TextView datetimeTv = cardView.findViewById(R.id.dateimeTv);
            TextView timeOfRunTv = cardView.findViewById(R.id.timeOfRunTv);
            TextView kmOfRunTv = cardView.findViewById(R.id.kmOfRunTv);
            TextView paceTv = cardView.findViewById(R.id.paceTv);
            TextView numberOfStepsTv = cardView.findViewById(R.id.numberOfStepsTv);
            datetimeTv.setText(thisRecord.datetime);
            timeOfRunTv.setText(thisRecord.overallTime);
            kmOfRunTv.setText(String.format("%.2f Kms", thisRecord.overallKms));
            numberOfStepsTv.setText(thisRecord.numOfSteps + " Steps");
            paceTv.setText((thisRecord.avgPace / 60) + "'" + (thisRecord.avgPace % 60) + "\" Pace");
            return cardView;
        }
        @Override
        public int getCount() {
            return myListOfRecords.size();
        }
        @Override
        public Object getItem(int i) {
            return myListOfRecords.get(i);
        }
        @Override
        public long getItemId(int i) {
            return Integer.toUnsignedLong(i);
        }
    }
}