package com.company.weathervietnamongooglemap.data.source;

import com.company.weathervietnamongooglemap.data.api.response.WeatherForecastResponse;
import com.company.weathervietnamongooglemap.data.model.CurrentWeather;

public interface CurrentWeatherDataSource {

    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(T data);

        void onFetchDataFailure(Exception e);
    }

    interface LocalDataSource {
        CurrentWeather getCurrentWeather();
        void SaveCurrentWeather(WeatherForecastResponse weatherForecastResponse);
    }

    interface RemoteDataSource {
        void getWeatherForeCast(OnFetchDataListener listener, String lat, String lon);

        void getCurrentWeatherByCityName(OnFetchDataListener listener, String cityName);
    }

}
