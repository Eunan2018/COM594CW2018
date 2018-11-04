package com.eunan.tracey.com594cw2018;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetJsonWeatherData extends AsyncTask<String, Void, WeatherConditions> implements GetDataTask.OnDownloadComplete {
    private static final String TAG = "GetJsonWeatherData";

    private WeatherConditions weatherConditions = new WeatherConditions();
    private final OnDataAvailable mCallBack;
    private String baseUrl;

    interface OnDataAvailable {
        void onDataAvailable(WeatherConditions weatherConditions);
    }

    public GetJsonWeatherData(String url, OnDataAvailable callBack) {
        Log.d(TAG, "GetJsonWeatherData: called");
        baseUrl = url;
        mCallBack = callBack;
    }

    @Override
    protected WeatherConditions doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: starts" + strings);
        GetDataTask getDataTask = new GetDataTask(this);
        getDataTask.execute(strings);
        Log.d(TAG, "doInBackground: ends");
        return weatherConditions;
    }



    void executeOnSameThread() {
        Log.d(TAG, "executeOnSameThread: starts");
        GetDataTask getDataTask = new GetDataTask(this);
        getDataTask.execute(baseUrl);
        Log.d(TAG, "executeOnSameThread: ends");
    }

    @Override
    public void onDownloadComplete(String data) {
        Log.d(TAG, "onDownLoadComplete: starts");

        try {

            // Parse data
            JSONObject topLevel = new JSONObject(data);
            JSONObject main = topLevel.getJSONObject("main");
            String windInfo = topLevel.getString("wind");
            JSONObject windInfoObject = new JSONObject(windInfo);

            // Use weather model and set each value
            String temperature = String.valueOf(main.getDouble("temp"));
            weatherConditions.setTemperature(temperature);
            String humidity = String.valueOf(main.getDouble("humidity"));
            weatherConditions.setHumidity(humidity);
            String pressure = String.valueOf(main.getInt("pressure"));
            weatherConditions.setPressure(pressure);
            String speed = String.valueOf(windInfoObject.getInt("speed"));
            weatherConditions.setSpeed(speed);
            String city = String.valueOf(topLevel.getString("name"));
            weatherConditions.setCity(city);
            Long l = Long.valueOf(topLevel.getLong("dt"));
            Date datetime = new Date(l * 1000);
            String dt = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(datetime);
            weatherConditions.setDate(dt);
        } catch (Exception e) {
            Log.d(TAG, "onDownLoadComplete: Exception " + e.getMessage());
        }
        if (mCallBack != null) {
            // Inform caller that processing is finished
            mCallBack.onDataAvailable(weatherConditions);
        }
        Log.d(TAG, "onDownLoadComplete: ends");
    }
}
