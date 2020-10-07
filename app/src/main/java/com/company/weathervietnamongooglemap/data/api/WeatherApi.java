package com.company.weathervietnamongooglemap.data.api;

import com.company.weathervietnamongooglemap.data.api.response.GeocodingResponse;
import com.company.weathervietnamongooglemap.data.api.response.WeatherForecastResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface WeatherApi {

    /*
        Fungsi @Path disini adalah untuk mengisi value yang sudah kita set.
        Contoh : {username} disini nantinya akan diisi dengan kebutuhan yang disesuaikan.
        Observable disini ialah dari RxJava. Karena pada contoh disini kita akan menggabungkan
        Retrofit dengan RxJava.
         */
    @GET("data/2.5/onecall?")
    Observable<WeatherForecastResponse> requestRepos(@Query("lat") String lat,
                                                           @Query("lon") String lon,
                                                           @Query(" exclude") String exclude,
                                                           @Query("lang") String lang,
                                                           @Query("units") String units,
                                                           @Query("appid") String api);
    @GET
    Observable<GeocodingResponse> getCoors(@Url String url);
    @GET
    Observable<ResponseBody> downloadImage(@Url String url);
}
