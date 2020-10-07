package com.company.weathervietnamongooglemap.data.api.response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;

import java.util.List;

public class WeatherForecastResponse implements ClusterItem {

    @Expose
    @SerializedName("lat")
    private double lat;
    @Expose
    @SerializedName("lon")
    private double lon;
    @Expose
    @SerializedName("current")
    private CurrentWeatherResponse currentWeather;
    @Expose
    @SerializedName("daily")
    private List<DailyWeatherResponse> dailyWeatherList;
    @Expose
    @SerializedName("hourly")
    private List<CurrentWeatherResponse> hourlyWeatherList;

    private String title;
    private String snippet;
    private String Address;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public CurrentWeatherResponse getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeatherResponse currentWeather) {
        this.currentWeather = currentWeather;
    }

    public List<DailyWeatherResponse> getDailyWeatherList() {
        return dailyWeatherList;
    }

    public void setDailyWeatherList(List<DailyWeatherResponse> dailyWeatherList) {
        this.dailyWeatherList = dailyWeatherList;
    }

    public List<CurrentWeatherResponse> getHourlyWeatherList() {
        return hourlyWeatherList;
    }

    public void setHourlyWeatherList(List<CurrentWeatherResponse> hourlyWeatherList) {
        this.hourlyWeatherList = hourlyWeatherList;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return new LatLng(this.lat, this.lon);
    }

    @Nullable
    @Override
    public String getTitle() {
        return String.valueOf(currentWeather.getTemp());
    }

    @Nullable
    @Override
    public String getSnippet() {
        return currentWeather.getWeathers().get(0).getDescription();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
