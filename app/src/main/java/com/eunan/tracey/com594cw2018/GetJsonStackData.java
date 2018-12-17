package com.eunan.tracey.com594cw2018;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class GetJsonStackData extends AsyncTask<String ,Void, ArrayList> implements GetDataTask.OnDownloadComplete {
    private static final String TAG = "GetJsonStackData";
    private String baseUrl;
    private ArrayList<StackModel> stackList = new ArrayList<>();
    private final OnDataAvailable mCallBack;

    interface OnDataAvailable {
        void onDataAvailable(ArrayList<StackModel> stackModel);
    }

    public GetJsonStackData(String url, OnDataAvailable callBack) {
        Log.d(TAG, "GetJsonStackData: called");
        mCallBack = callBack;
        baseUrl = url;
    }

    void executeGetDataTask() {
        Log.d(TAG, "executeOnSameThread: starts");
        GetDataTask getDataTask = new GetDataTask(this);
        getDataTask.execute(baseUrl);
        Log.d(TAG, "executeOnSameThread: ends");
    }

    @Override
    public void onDownloadComplete(String data) {
        Log.d(TAG, "onDownloadComplete: starts");
        this.execute(data);
        Log.d(TAG, "onDownLoadComplete: ends");
    }
    @Override
    protected ArrayList<StackModel> doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: " + strings);
        try {
            // Start at the root of the json object
            String json = strings[0];
            JSONObject root = new JSONObject(json);
            JSONArray itemsArray = root.getJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++) {

                // Create StackModel class to add to ArrayList
                StackModel stackModel = new StackModel();

                // Get values from JSON Array items
                String title = itemsArray.getJSONObject(i).getString("title");
                stackModel.setTitle(String.valueOf(title));

                String link = itemsArray.getJSONObject(i).getString("link");
                stackModel.setLink(String.valueOf(link));

                // Get first element in JSON Array owner
                JSONObject jsonImage = itemsArray.getJSONObject(i);
                JSONObject img = jsonImage.getJSONObject("owner");

                String image = String.valueOf(img.getString("profile_image"));
                stackModel.setImage(String.valueOf(image));

                // Add each StackModel to the list
                stackList.add(stackModel);
            }
        } catch (Exception e) {
            Log.d(TAG, "onDownLoadComplete: Exception " + e.getMessage());
        }
        if (mCallBack != null) {
            // Inform caller that processing is finished and return List of StackModels
            mCallBack.onDataAvailable(stackList);
        }
        return null;
    }
}
