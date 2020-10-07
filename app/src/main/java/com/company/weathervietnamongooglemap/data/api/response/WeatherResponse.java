package com.company.weathervietnamongooglemap.data.api.response;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.clustering.ClusterItem;
import com.company.weathervietnamongooglemap.data.service.DownloadImage;
import com.company.weathervietnamongooglemap.data.source.CurrentWeatherDataSource;

public class WeatherResponse implements CurrentWeatherDataSource.OnFetchDataListener<Bitmap>, ClusterItem {

    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("icon")
    private String icon;

    private int bitmap ;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
         return  this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private void fetchingImage() {
        new DownloadImage(this).execute(this.icon);
    }

    @Override
    public void onFetchDataSuccess(Bitmap data) {
//        setIcon(data);
    }

    @Override
    public void onFetchDataFailure(Exception e) {

    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return null;
    }

    @Nullable
    @Override
    public String getTitle() {
        return null;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return null;
    }
}
