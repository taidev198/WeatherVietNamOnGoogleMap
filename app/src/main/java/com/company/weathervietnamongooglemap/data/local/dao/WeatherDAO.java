package com.company.weathervietnamongooglemap.data.local.dao;

import com.company.weathervietnamongooglemap.data.api.response.WeatherForecastResponse;
import com.company.weathervietnamongooglemap.data.model.CurrentWeather;

public interface WeatherDAO {

    CurrentWeather getCurrentWeather();
    void SaveCurrentWeather(WeatherForecastResponse weatherForecastResponse);

}
