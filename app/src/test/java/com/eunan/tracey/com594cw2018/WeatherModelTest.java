package com.eunan.tracey.com594cw2018;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WeatherModelTest {
    WeatherModel weatherModel = new WeatherModel();
    String humidity = "testHumidity";
    String pressure = "testPressure";
    String speed = "testSpeed";
    String city =  "testCity";
    String date = "testDate";
    String image = "testImage";
    String temp = "testTemp";

    @Before
    public void setUp(){

        weatherModel.setImage(image);
        weatherModel.setDate(date);
        weatherModel.setCity(city);
        weatherModel.setSpeed(speed);
        weatherModel.setPressure(pressure);
        weatherModel.setHumidity(humidity);
        weatherModel.setTemperature(temp);
    }

    @Test
    public void testGettersAnnSetters() {
        Assert.assertTrue(weatherModel.getCity().equals(city));
        Assert.assertTrue(weatherModel.getImage().equals(image));
        Assert.assertTrue(weatherModel.getHumidity().equals(humidity));
        Assert.assertTrue(weatherModel.getPressure().equals(pressure));
        Assert.assertTrue(weatherModel.getSpeed().equals(speed));
        Assert.assertTrue(weatherModel.getTemperature().equals(temp));
        Assert.assertTrue(weatherModel.getDate().equals(date));
    }

}