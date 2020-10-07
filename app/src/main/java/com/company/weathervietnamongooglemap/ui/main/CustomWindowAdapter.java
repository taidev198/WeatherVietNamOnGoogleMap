package com.company.weathervietnamongooglemap.ui.main;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.company.weathervietnamongooglemap.R;
import com.company.weathervietnamongooglemap.data.api.response.WeatherForecastResponse;

public class CustomWindowAdapter implements GoogleMap.InfoWindowAdapter{
    LayoutInflater mInflater;
   private WeatherForecastResponse mWeatherForecastResponse;

    public CustomWindowAdapter(LayoutInflater i, WeatherForecastResponse weatherForecastResponse){
        mInflater = i;
        mWeatherForecastResponse = weatherForecastResponse;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getInfoWindow(Marker marker) {
        // Getting view from the layout file
        View v = mInflater.inflate(R.layout.infowindow, null);
        TextView description =  v.findViewById(R.id.text_des_iw);
        description.setText(mWeatherForecastResponse.getCurrentWeather().getWeathers().get(0).getDescription());
        TextView address = v.findViewById(R.id.text_add_iw);
        address.setText(mWeatherForecastResponse.getAddress());
        TextView temp = v.findViewById(R.id.text_temp_iw);
        temp.setText(Float.toString(mWeatherForecastResponse.getCurrentWeather().getTemp()));
        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
//        View v = mInflater.inflate(R.layout.infowindow, null);
//        TextView description =  v.findViewById(R.id.text_des_iw);
//        description.setText(mWeatherForecastResponse.getCurrentWeather().getWeathers().get(0).getDescription());
//        System.out.println(mWeatherForecastResponse.getCurrentWeather().getWeathers().get(0).getDescription());
//        TextView temp = v.findViewById(R.id.text_temp_iw);
//        temp.setText(Float.toString(mWeatherForecastResponse.getCurrentWeather().getTemp()));
//        System.out.println(mWeatherForecastResponse.getCurrentWeather().getTemp());
//        TextView address = v.findViewById(R.id.text_add_iw);
//        address.setText(mWeatherForecastResponse.getAddress());
//        System.out.println(mWeatherForecastResponse.getAddress());
        return null;
    }
}
