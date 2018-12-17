package com.eunan.tracey.com594cw2018;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DisplayWeather extends AppCompatActivity implements GetJsonWeatherData.OnDataAvailable, SensorEventListener {
    private static final String TAG = "DisplayWeather";

    private TextView txtHumidity;
    private TextView txtPressure;
    private TextView txtCity;
    private TextView txtSpeed;
    private TextView txtDate1;
    private TextView txtDate2;
    private TextView txtDate3;
    private TextView txtDate4;
    private TextView txtDate5;
    private TextView txtTemperature1;
    private TextView txtTemperature2;
    private TextView txtTemperature3;
    private TextView txtTemperature4;
    private TextView txtTemperature5;

    private TextView txtViewExampleCity;
    private EditText editTxtCity;


    // Create SensorManger
    private SensorManager sensorManager;
    private long lastUpdateTime;
    private static float SHAKE_THRESHOLD_GRAVITY = 2;

    // Create ImageViews
    private ImageView imagePressure;
    private ImageView imageHumidity;
    private ImageView imageWind;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;

    // Create button
    private Button btn;

    // Firebase Auth
    FirebaseAuth firebaseAuth;
    // Firebase User
    FirebaseUser firebaseUser;
    NetworkInfo info = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        // Initialise SensorManger
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        lastUpdateTime = System.currentTimeMillis();

        // Initialise all views
        initialiseViews();

        // Create instance of firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        // Get current firebase user
        firebaseUser = firebaseAuth.getCurrentUser();

        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, "Shake device for the next screen!!", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();

        Log.d(TAG, "onCreate: ends");
    }


    private void initialiseViews() {
        Log.d(TAG, "initialiseViews: starts");
        // Initialise TextViews
        txtCity = findViewById(R.id.textViewCity);
        txtDate1 = findViewById(R.id.textViewDate1);
        txtTemperature1 = findViewById(R.id.textViewTemperature1);
        txtHumidity = findViewById(R.id.textViewHumidity);
        txtPressure = findViewById(R.id.textViewPressure);
        txtSpeed = findViewById(R.id.textViewSpeed);

        // Forecast
        txtTemperature2 = findViewById(R.id.textViewTemperature2);
        txtTemperature3 = findViewById(R.id.textViewTemperature3);
        txtTemperature4 = findViewById(R.id.textViewTemperature4);
        txtTemperature5 = findViewById(R.id.textViewTemperature5);

        txtDate1 = findViewById(R.id.textViewDate1);
        txtDate2 = findViewById(R.id.textViewDate2);
        txtDate3 = findViewById(R.id.textViewDate3);
        txtDate4 = findViewById(R.id.textViewDate4);
        txtDate5 = findViewById(R.id.textViewDate5);
        txtViewExampleCity = findViewById(R.id.textViewExample);
        editTxtCity = findViewById(R.id.editTextCity);
        // Initialise Button
        btn = findViewById(R.id.buttonLogout);


        // Initialise ImageViews
        imageHumidity = findViewById(R.id.imageHumidity);
        imagePressure = findViewById(R.id.imagePressure);
        imageWind = findViewById(R.id.imageWind);

        imageHumidity.setVisibility(View.INVISIBLE);
        imageWind.setVisibility(View.INVISIBLE);
        imagePressure.setVisibility(View.INVISIBLE);
        image1 = findViewById(R.id.imageViewDay1);
        image2 = findViewById(R.id.imageViewDay2);
        image3 = findViewById(R.id.imageViewDay3);
        image4 = findViewById(R.id.imageViewDay4);
        image5 = findViewById(R.id.imageViewDay5);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                Intent logoutIntent = new Intent(DisplayWeather.this, MainActivity.class);
                startActivity(logoutIntent);
            }
        });


        editTxtCity.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    final String city = editTxtCity.getText().toString();
                    if (!TextUtils.isEmpty(city.trim())) {
                        info = ((ConnectivityManager)
                                getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                        if (info != null) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        final String URL = new PropertyManager(getBaseContext()).getProperties("weather.properties").getProperty("url");
                                        String units = "metric";
                                        String url = String.format(URL,
                                                city, units);
                                        new GetJsonWeatherData(url, DisplayWeather.this).executeGetDataTask();
                                        editTxtCity.setText("");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
                            return false;
                        }
                        return true;
                    } else {
                        Toast.makeText(getApplicationContext(), "City cannot be empty", Toast.LENGTH_LONG).show();
                        editTxtCity.setText("");
                    }
                }
                return false;
            }
        });
        Log.d(TAG, "initialiseViews: ends");
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }

    }

    private void getAccelerometer(SensorEvent event) {

        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float gX = x / SensorManager.GRAVITY_EARTH;
        float gY = y / SensorManager.GRAVITY_EARTH;
        float gZ = z / SensorManager.GRAVITY_EARTH;

        // gForce will be close to 1 when there is no movement
        float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

        long currentTime = System.currentTimeMillis();
        if (gForce >= SHAKE_THRESHOLD_GRAVITY) {
            if (currentTime - lastUpdateTime < 200) {
                return;
            }
            lastUpdateTime = currentTime;
            info = (NetworkInfo) ((ConnectivityManager)
                    getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (info != null) {
                Toast.makeText(this, "Device was shaken", Toast.LENGTH_SHORT).show();
                Intent stackIntent = new Intent(DisplayWeather.this, DisplayStack.class);
                startActivity(stackIntent);
            } else {
                Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
                return;
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // unregister listener
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    // Display data to UI
    public void onDataAvailable(final ArrayList<WeatherModel> weatherModel) {
        txtViewExampleCity.setVisibility(View.INVISIBLE);
        imageHumidity.setVisibility(View.VISIBLE);
        imageWind.setVisibility(View.VISIBLE);
        imagePressure.setVisibility(View.VISIBLE);
        ArrayList<ImageView> imageViews = new ArrayList<>();
        float kelvinTemp = 273.15f;
        Log.d(TAG, "onDataAvailable: starts" + weatherModel);
        DecimalFormat df2 = new DecimalFormat(".##");
        imageViews.add(image1);
        imageViews.add(image2);
        imageViews.add(image3);
        imageViews.add(image4);
        imageViews.add(image5);
        for (int i = 0; i < weatherModel.size(); i++) {

            Picasso.with(DisplayWeather.this).load(new PropertyManager(DisplayWeather.this).getProperties("img.properties").getProperty("img") + weatherModel.get(i).getImage() + ".png")
                    .into(imageViews.get(i));

            String city = weatherModel.get(0).getCity();
            txtCity.setText(getString(R.string.city, city));
            float celsius = Float.parseFloat(weatherModel.get(0).getTemperature()) - kelvinTemp;
            String Temperature = df2.format(celsius) + "\u2103";
            txtTemperature1.setText(getString(R.string.temp, Temperature));

            String date = weatherModel.get(0).getDate().substring(0, 10);
            txtDate1.setText(getString(R.string.date, date));

            String humidity = weatherModel.get(0).getHumidity() + " %\nHumidity";
            txtHumidity.setText(getString(R.string.humidity, humidity));

            String pressure = weatherModel.get(0).getPressure() + " hpa";
            txtPressure.setText(getString(R.string.pressure, pressure));

            String speed = weatherModel.get(0).getSpeed() + " M/H\n Wind Speed";
            txtSpeed.setText(getString(R.string.speed, speed));

            //Forecasts Temperature
            float celsius2 = Float.parseFloat(weatherModel.get(1).getTemperature()) - kelvinTemp;
            String Temperature2 = df2.format(celsius2) + "\u2103";
            txtTemperature2.setText(getString(R.string.temp, Temperature2));
            float celsius3 = Float.parseFloat(weatherModel.get(2).getTemperature()) - kelvinTemp;
            String Temperature3 = df2.format(celsius3) + "\u2103";
            txtTemperature3.setText(getString(R.string.temp, Temperature3));
            float celsius4 = Float.parseFloat(weatherModel.get(3).getTemperature()) - kelvinTemp;
            String Temperature4 = df2.format(celsius4) + "\u2103";
            txtTemperature4.setText(getString(R.string.temp, Temperature4));
            float celsius5 = Float.parseFloat(weatherModel.get(4).getTemperature()) - kelvinTemp;
            String Temperature5 = df2.format(celsius5) + "\u2103";
            txtTemperature5.setText(getString(R.string.temp, Temperature5));

            //Forecast Date
            String date2 = weatherModel.get(1).getDate();
            txtDate2.setText(getString(R.string.date, date2).substring(5, 10).replace("-", "/"));
            String date3 = weatherModel.get(2).getDate();
            txtDate3.setText(getString(R.string.date, date3).substring(5, 10).replace("-", "/"));
            String date4 = weatherModel.get(3).getDate();
            txtDate4.setText(getString(R.string.date, date4).substring(5, 10).replace("-", "/"));
            String date5 = weatherModel.get(4).getDate();
            txtDate5.setText(getString(R.string.date, date5).substring(5, 10).replace("-", "/"));
        }
    }


}
