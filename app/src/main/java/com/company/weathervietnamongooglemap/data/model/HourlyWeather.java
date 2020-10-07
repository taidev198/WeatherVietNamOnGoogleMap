package com.company.weathervietnamongooglemap.data.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HourlyWeather extends Weather {

    private List<Weather> mListWeather;
    public HourlyWeather() {
        mListWeather = new ArrayList<>();
    }

    public HourlyWeather(List<Weather> weatherList) {
        mListWeather = weatherList;
    }

    public HourlyWeather(Date date,
                         String weather,
                         Temperature temp,
                         Location location,
                         Bitmap bitmap) {
        super(date, weather, temp, location, bitmap);
    }

    public List<Weather> getListWeather() {
        return mListWeather;
    }

    public void setListWeather(List<Weather> mListWeather) {
        this.mListWeather = mListWeather;
    }
}
