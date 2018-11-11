package com.eunan.tracey.com594cw2018;

import android.media.Image;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetJsonWeatherData extends AppCompatActivity implements GetDataTask.OnDownloadComplete {
    private static final String TAG = "GetJsonWeatherData";

    private WeatherModel weatherModel = new WeatherModel();
    private final OnDataAvailable mCallBack;
    private String baseUrl;


    interface OnDataAvailable {
        void onDataAvailable(WeatherModel weatherModel);
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

        try {
            JSONObject topLevel = new JSONObject(data);
            //String msg = topLevel.getString("message");
            if (data.equalsIgnoreCase("Error: Not found city")) {
                Log.e("TAG", "City not found");
                Toast.makeText(this,"Error with city name",Toast.LENGTH_LONG).show();
            } else {
                // Parse data

                JSONObject main = topLevel.getJSONObject("main");
                String windInfo = topLevel.getString("wind");
                JSONObject windInfoObject = new JSONObject(windInfo);

                JSONArray itemsArray = topLevel.getJSONArray("weather");
                String img = itemsArray.getJSONObject(0).getString("icon");

                weatherModel.setImage(img);

                String temperature = String.valueOf(main.getDouble("temp"));
                weatherModel.setTemperature(temperature);
                String humidity = String.valueOf(main.getDouble("humidity"));
                weatherModel.setHumidity(humidity);
                String pressure = String.valueOf(main.getInt("pressure"));
                weatherModel.setPressure(pressure);
                String speed = String.valueOf(windInfoObject.getInt("speed"));
                weatherModel.setSpeed(speed);
                String city = String.valueOf(topLevel.getString("name"));
                weatherModel.setCity(city);
                Long l = Long.valueOf(topLevel.getLong("dt"));
                Date datetime = new Date(l * 1000);
                String dt = new SimpleDateFormat("dd.MM.yyyy").format(datetime);
                weatherModel.setDate(dt);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mCallBack != null) {
            // Inform caller that processing is finished
            mCallBack.onDataAvailable(weatherModel);
        }
        Log.d(TAG, "onDownLoadComplete: ends");
    }
}
