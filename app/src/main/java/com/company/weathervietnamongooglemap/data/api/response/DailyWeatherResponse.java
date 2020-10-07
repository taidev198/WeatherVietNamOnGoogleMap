package com.company.weathervietnamongooglemap.data.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyWeatherResponse {

    @Expose
    @SerializedName("temp")
    private TempResponse temp;
    @Expose
    @SerializedName("clouds")
    private int clouds;
    @Expose
    @SerializedName("wind_speed")
    private float wind_speed;
    @Expose
    @SerializedName("weather")
    private List<WeatherResponse> weathers;

    public TempResponse getTemp() {
        return temp;
    }

    public void setTemp(TempResponse temp) {
        this.temp = temp;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(float wind_speed) {
        this.wind_speed = wind_speed;
    }

    public List<WeatherResponse> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<WeatherResponse> weathers) {
        this.weathers = weathers;
    }
}
