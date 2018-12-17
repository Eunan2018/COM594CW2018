package com.eunan.tracey.com594cw2018;


import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class DisplayStack extends AppCompatActivity implements GetJsonStackData.OnDataAvailable {
    private static final String TAG = "DisplayStack";

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private final String KEY_RECYCLER_STATE = "Recycler_State";

    private static Bundle mBundleRecyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stackoverflow);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(200);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: starts");
        super.onStart();
        try {
            new GetJsonStackData(new PropertyManager(this).getProperties("stack.properties").getProperty("url"), this)
                    .executeGetDataTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Restore the state of the recycler view when activity restarts
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
        Log.d(TAG, "onStart: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();
        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
        Log.d(TAG, "onResume: ends");
    }

    @Override
    public void onDataAvailable(final ArrayList<StackModel> stackModel) {
        Log.d(TAG, "onDataAvailable: starts" + stackModel);
        // Running on same thread as GetJsonWeatherData
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new RecyclerViewAdapter(getApplicationContext(), stackModel);
                recyclerView.setAdapter(adapter);
            }
        });
        Log.d(TAG, "onDataAvailable: ends");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save the state of the recyclerview when orientation changes to landscape
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }
}


