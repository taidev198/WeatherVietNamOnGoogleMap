package com.company.weathervietnamongooglemap.data.repository;

import com.company.weathervietnamongooglemap.data.source.CurrentWeatherDataSource;
import com.company.weathervietnamongooglemap.data.source.current.CurrentRemoteDataSource;

public class CurrentWeatherRepository {
    private static CurrentWeatherRepository sInstance;
    private CurrentRemoteDataSource mCurrentRemoteDataSource;

    private CurrentWeatherRepository(CurrentRemoteDataSource currentRemoteDataSource) {
        mCurrentRemoteDataSource = currentRemoteDataSource;
    }

    public static CurrentWeatherRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentWeatherRepository(CurrentRemoteDataSource.getInstance());
        }
        return sInstance;
    }

    public void getCurrentWeather(CurrentWeatherDataSource.OnFetchDataListener listener,
                                  String lat, String lon) {
        mCurrentRemoteDataSource.getWeatherForeCast(listener, lat, lon);
    }

    public void getCurrentWeatherByCityName(CurrentWeatherDataSource.OnFetchDataListener listener,
                                  String cityName) {
        mCurrentRemoteDataSource.getCurrentWeatherByCityName(listener, cityName);
    }


}