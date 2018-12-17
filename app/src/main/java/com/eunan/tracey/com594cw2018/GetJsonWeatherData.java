package com.eunan.tracey.com594cw2018;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GetJsonWeatherData extends AppCompatActivity implements GetDataTask.OnDownloadComplete {
    private static final String TAG = "GetJsonWeatherData";

    private final OnDataAvailable mCallBack;
    private String baseUrl;
    private ArrayList<WeatherModel> weatherModels = new ArrayList<>();

    interface OnDataAvailable {
        void onDataAvailable(ArrayList<WeatherModel> weatherModels);
    }

    public GetJsonWeatherData(String url, OnDataAvailable callBack) {
        Log.d(TAG, "GetJsonWeatherData: called");
        baseUrl = url;
        mCallBack = callBack;
    }

    void executeGetDataTask() {
        Log.d(TAG, "executeOnSameThread: starts");
        GetDataTask getDataTask = new GetDataTask(this);
        getDataTask.execute(baseUrl);
        Log.d(TAG, "executeOnSameThread: ends");
    }

    @Override
    public void onDownloadComplete(String data) {
        Log.d(TAG, "onDownLoadComplete: starts" + data);
        if(!data.isEmpty()) {
            try {
                JSONObject root = new JSONObject(data);

                JSONArray jsonArray = root.getJSONArray("list");
                JSONObject cityObject = root.getJSONObject("city");
                String city = String.valueOf(cityObject.getString("name"));
                for (int i = 0; i < jsonArray.length(); i++) {

                    String date = String.valueOf(jsonArray.getJSONObject(i).getString("dt_txt"));
                    JSONArray weatherArray = jsonArray.getJSONObject(i).getJSONArray("weather");
                    JSONObject mainObject = jsonArray.getJSONObject(i).getJSONObject("main");
                    JSONObject windObject = jsonArray.getJSONObject(i).getJSONObject("wind");

                    // Used in substring to get exact time of each day
                    String dateInput = date;
                    String dateOutput = dateInput.substring(11, 19);

                    // Get values for each day at 3pm
                    if (dateOutput.equals("15:00:00")) {
                        WeatherModel weatherModel = new WeatherModel();
                        String img  = weatherArray.getJSONObject(0).getString("icon");
                        String temperature = String.valueOf(mainObject.getDouble("temp"));
                        String humidity = String.valueOf(mainObject.getDouble("humidity"));
                        String pressure = String.valueOf(mainObject.getInt("pressure"));
                        String windSpeed = String.valueOf(windObject.getDouble("speed"));

                        // Use model class to set weather conditions
                        weatherModel.setImage(img);
                        weatherModel.setTemperature(temperature);
                        weatherModel.setHumidity(humidity);
                        weatherModel.setPressure(pressure);
                        weatherModel.setSpeed(windSpeed);
                        weatherModel.setCity(city);
                        weatherModel.setDate(date);

                        // Add each days weather to ArrayList
                        weatherModels.add(weatherModel);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mCallBack != null) {
                // Inform caller that processing is finished
                mCallBack.onDataAvailable(weatherModels);
            }
        }else{
            Toast.makeText(this,"Try again",Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "onDownLoadComplete: ends");
    }
}
