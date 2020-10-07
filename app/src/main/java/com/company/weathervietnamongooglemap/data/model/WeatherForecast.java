package com.company.weathervietnamongooglemap.data.model;

public class WeatherForecast {

    private Weather mCurrentWeather;
    private HourlyWeather mHourlyWeather;
    private DailyWeather mDailyWeather;

    public WeatherForecast(Weather currentWeather,
                           HourlyWeather hourlyWeather,
                           DailyWeather dailyWeather) {
        mCurrentWeather = currentWeather;
        mHourlyWeather = hourlyWeather;
        mDailyWeather = dailyWeather;
    }

    public Weather getCurrentWeather() {
        return mCurrentWeather;
    }

    public void setCurrentWeather(Weather mCurrentWeather) {
        this.mCurrentWeather = mCurrentWeather;
    }

    public HourlyWeather getHourlyWeather() {
        return mHourlyWeather;
    }

    public void setHourlyWeather(HourlyWeather mHourlyWeather) {
        this.mHourlyWeather = mHourlyWeather;
    }

    public DailyWeather getDailyWeather() {
        return mDailyWeather;
    }

    public void setDailyWeather(DailyWeather mDailyWeather) {
        this.mDailyWeather = mDailyWeather;
    }
}
