package com.company.weathervietnamongooglemap.data.source.current;

import com.company.weathervietnamongooglemap.data.source.CurrentWeatherDataSource;
import com.company.weathervietnamongooglemap.utls.StringUtil;

        public class CurrentRemoteDataSource implements CurrentWeatherDataSource.RemoteDataSource {
    private static CurrentRemoteDataSource sInstance;

    private CurrentRemoteDataSource() {
    }

    public static CurrentRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getWeatherForeCast(CurrentWeatherDataSource.OnFetchDataListener listener,
                                   String lat, String lon) {
        FetchCurrentWeatherFromUrl fetchCurrentWeatherFromUrl
                = new FetchCurrentWeatherFromUrl(listener);
        fetchCurrentWeatherFromUrl.execute(StringUtil.getWeatherForecast(lat, lon));
    }

    @Override
    public void getCurrentWeatherByCityName(CurrentWeatherDataSource.OnFetchDataListener listener, String cityName) {
        FetchCurrentWeatherFromUrl fetchCurrentWeatherFromUrl
                = new FetchCurrentWeatherFromUrl(listener);
        fetchCurrentWeatherFromUrl.execute(StringUtil.formatWeatherByCityName(cityName));
    }
}
