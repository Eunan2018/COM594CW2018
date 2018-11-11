package com.eunan.tracey.com594cw2018;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class DisplayStack extends AppCompatActivity implements GetJsonStackData.OnDataAvailable {
    private static final String TAG = "DisplayStack";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stackoverflow);
    }
    
    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();
        try {
            new GetJsonStackData(new PropertyManager(this).getProperties("stack.properties").getProperty("url") ,this)
                    .executeGetDataTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onResume: ends");
    }

    @Override
    public void onDataAvailable(ArrayList<StackModel> stackModel) {
        Log.d(TAG, "onDataAvailable: starts" + stackModel);
        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(200);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this,stackModel);

        recyclerView.setAdapter(adapter);
        Log.d(TAG, "onDataAvailable: ends");
    }

}


