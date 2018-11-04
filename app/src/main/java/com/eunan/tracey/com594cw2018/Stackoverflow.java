package com.eunan.tracey.com594cw2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Stackoverflow extends AppCompatActivity implements GetDataTask.OnDownloadComplete {
    private static final String TAG = "Stackoverflow";
    private static final String OPEN_WEATHER_MAP_URL = "https://api.stackexchange.com/2.2/questions?pagesize=1&order=desc&sort=activity&tagged=android&site=stackoverflow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stackoverflow);
        new GetDataTask(this).execute(OPEN_WEATHER_MAP_URL);
    }

    @Override
    public void onDownloadComplete(String data) {
        Log.d(TAG, "onDownloadComplete: " + data);
    }
}


