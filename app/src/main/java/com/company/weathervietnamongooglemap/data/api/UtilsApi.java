package com.company.weathervietnamongooglemap.data.api;

public class UtilsApi {

    private static String BASE_URL = "https://api.openweathermap.org/";

    public static WeatherApi getAPIService(){
        return RetrofitClient.getClient(BASE_URL).create(WeatherApi.class);
    }

}
