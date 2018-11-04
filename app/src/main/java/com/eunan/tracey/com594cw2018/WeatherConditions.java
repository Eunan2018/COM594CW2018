package com.eunan.tracey.com594cw2018;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WeatherConditions {

    @Getter
    @Setter
    public String temperature;
    @Getter
    @Setter
    public String humidity;
    @Getter
    @Setter
    public String pressure;
    @Getter
    @Setter
    public String speed;
    @Getter
    @Setter
    public String city;
    @Getter
    @Setter
    public String date;
}
