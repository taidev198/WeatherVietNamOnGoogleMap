package com.company.weathervietnamongooglemap.data.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeatherResponse {

    @Expose
    @SerializedName("temp")
    private float temp;
    @Expose
    @SerializedName("visibility")
    private int visibility;
    @Expose
    @SerializedName("clouds")
    private int clouds;
    @Expose
    @SerializedName("wind_speed")
    private float wind_speed;
    @Expose
    @SerializedName("weather")
    private List<WeatherResponse> weathers;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
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
