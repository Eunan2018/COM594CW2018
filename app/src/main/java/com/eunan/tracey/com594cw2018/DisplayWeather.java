package com.eunan.tracey.com594cw2018;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DisplayWeather extends AppCompatActivity implements GetJsonWeatherData.OnDataAvailable {
    private static final String TAG = "DisplayWeather";

    // TextViews
    private TextView txtTemperature;
    private TextView txtHumidity;
    private TextView txtPressure;
    private TextView txtCity;
    private TextView txtDate;
    private TextView txtSpeed;

    private AutoCompleteTextView textView;

    private ImageView image;
    // Initialise button
    private Button btn;

    ArrayList<String> cityList = new ArrayList<>();
    String city = "";

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
        image = findViewById(R.id.imageview_weather);
        textView = findViewById(R.id.autoCompleteTextView);
        textView.setOnItemClickListener(listClick);

        // Get all cities in openweathermap
        getCities();

        Log.d(TAG, "onCreate: ends");
    }


    private void getCities() {
        Log.d(TAG, "getCities: starts");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONArray cityJsonArray = new JSONArray(new PropertyManager(DisplayWeather.this).readJSONFromAsset("cities.json"));
                    for (int i = 0; i < cityJsonArray.length(); i++) {
                        JSONObject cityJsonObject = cityJsonArray.getJSONObject(i);
                        String city = cityJsonObject.getString("name");
                        cityList.add(city);
                    }
                } catch (Exception e) {
                    Log.d(TAG, "In exception" + e.getMessage());
                    e.printStackTrace();
                }
                // Populate autocomplete with cities
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DisplayWeather.this,
                        android.R.layout.simple_expandable_list_item_1, cityList);

                textView = findViewById(R.id.autoCompleteTextView);
                textView.setThreshold(3);
                textView.setAdapter(adapter);
            }
        });
        Log.d(TAG, "getCities: ends");
    }

    private AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                String name = textView.getText().toString();
                if (!cityList.contains(name)) {
                    Toast.makeText(getBaseContext(), "Invalid entry. " + name + " is not a city. Try again!!", Toast.LENGTH_LONG).show();
                } else {
                    final String API = new PropertyManager(getBaseContext()).getProperties("weather.properties").getProperty("api");
                    final String URL = new PropertyManager(getBaseContext()).getProperties("weather.properties").getProperty("url");
                    String units = "metric";
                    String url = String.format(URL,
                            name, units, API);
                    new GetJsonWeatherData(url, DisplayWeather.this).executeGetDataTask();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void onDataAvailable(WeatherModel weatherModel) {
        Log.d(TAG, "onDataAvailable: starts" + weatherModel);
        DecimalFormat df2 = new DecimalFormat(".##");
        // Display data to UI
        final String icon = weatherModel.getImage();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String iconUrl = new PropertyManager(DisplayWeather.this).getProperties("img.properties").getProperty("img") + icon + ".png";
                            Picasso.with(DisplayWeather.this).load(iconUrl).into(image);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        String city = weatherModel.getCity();
        txtCity.setText(getString(R.string.city, city));
        float kelvin = Float.parseFloat(weatherModel.getTemperature());
        float celsius = kelvin - 273.15f;
        String Temperature = df2.format(celsius) + "\u2103";
        txtTemperature.setText(getString(R.string.temp, Temperature));
        String date = weatherModel.getDate();
        txtDate.setText(getString(R.string.date, date));
        String humidity = weatherModel.getHumidity() + " hpa";
        txtHumidity.setText(getString(R.string.humidity, humidity));
        String pressure = weatherModel.getPressure();
        txtPressure.setText(getString(R.string.pressure, pressure));
        String speed = weatherModel.getSpeed() + " M/H";
        txtSpeed.setText(getString(R.string.speed, speed));
    }
}
