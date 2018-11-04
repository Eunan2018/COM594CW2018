package com.eunan.tracey.com594cw2018;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class Weather extends AppCompatActivity implements GetJsonWeatherData.OnDataAvailable {
    private static final String TAG = "Weather";
    private static final String OPEN_WEATHER_MAP_API_KEY = "7886ba873ffb42fb880220fd1cf00f03";
    private static final String OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=%s&appid=%s";

    // TextViews
    private TextView txtTemperature;
    private TextView txtHumidity;
    private TextView txtPressure;
    private TextView txtCity;
    private TextView txtDate;
    private TextView txtSpeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Initialise TextViews
        txtCity = findViewById(R.id.textViewCity);
        txtDate = findViewById(R.id.textViewDate);
        txtTemperature = findViewById(R.id.textViewTemperature);
        txtHumidity = findViewById(R.id.textViewHumidity);
        txtPressure = findViewById(R.id.textViewPressure);
        txtSpeed = findViewById(R.id.textViewSpeed);
        Log.d(TAG, "onCreate: ends");
    }

    public void onDataAvailable(WeatherConditions weatherConditions){
        Log.d(TAG, "onDataAvailable: starts" + weatherConditions);
        // Display data to UI
        txtCity.setText("City: " + weatherConditions.getCity());
        txtTemperature.setText("Temperature: " + weatherConditions.getTemperature() + "C");
        txtDate.setText("Date: " + weatherConditions.getDate());
        txtHumidity.setText("Humidity: " + weatherConditions.getHumidity() + "%");
        txtPressure.setText("Pressure: " + weatherConditions.getPressure() + " mbar");
        txtSpeed.setText("Speed: " + weatherConditions.getSpeed()  + " M/h");
        Log.d(TAG, "onDataAvailable: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();
        double lat = 51.51, lon = -0.13;
        String units = "metric";
        String url = String.format(OPEN_WEATHER_MAP_URL
                , lat, lon, units, OPEN_WEATHER_MAP_API_KEY);
        new GetJsonWeatherData(url ,this).executeOnSameThread();
        Log.d(TAG, "onResume: ends");
    }
}
