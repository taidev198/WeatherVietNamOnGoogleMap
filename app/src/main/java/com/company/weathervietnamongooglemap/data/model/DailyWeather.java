package com.company.weathervietnamongooglemap.data.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyWeather extends Weather {

   private DailyTemp mDailyTemp;
   private List<Weather> weatherList;
    public DailyWeather() {
        weatherList = new ArrayList<>();
    }

    public DailyWeather(Date date,
                        String weather,
                        Location location,
                        Bitmap bitmap,
                        DailyTemp dailyTemp) {
        super(date, weather, new Temperature(dailyTemp.getTemperature()), location, bitmap);
        mDailyTemp = dailyTemp;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public DailyTemp getDailyTemp() {
        return mDailyTemp;
    }

    public void setDailyTemp(DailyTemp mDailyTemp) {
        this.mDailyTemp = mDailyTemp;
    }

    private class DailyTemp extends Temperature {

    private int mTempMor;
    private int mTempEve;
    private int mTempNight;

        public DailyTemp() {

        }

        public DailyTemp(float temp,
                         float minTemp,
                         float maxTemp,
                         int tempMor,
                         int tempEve,
                         int tempNight) {
            super(temp, minTemp, maxTemp);

        }

    public int getTempMor() {
        return mTempMor;
    }

    public void setTempMor(int mTempMor) {
        this.mTempMor = mTempMor;
    }

    public int getTempEve() {
        return mTempEve;
    }

    public void setTempEve(int mTempEve) {
        this.mTempEve = mTempEve;
    }

    public int getTempNight() {
        return mTempNight;
    }

    public void setTempNight(int mTempNight) {
        this.mTempNight = mTempNight;
    }
}
}
