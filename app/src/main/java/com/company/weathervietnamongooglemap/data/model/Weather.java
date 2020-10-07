package com.company.weathervietnamongooglemap.data.model;

import android.graphics.Bitmap;

import java.util.Date;

public class Weather {

    private Date mDate;
    private String mWeather;
    private Temperature mTemp;
    private Location mLocation;
    private Bitmap mIcon;

    public Weather() {
        mLocation = new Location();
        mTemp = new Temperature();
    }

    public Weather(Date date,
                   String weather,
                   Temperature temp,
                   Location location,
                   Bitmap bitmap) {

        mDate = date;
        mWeather = weather;
        mTemp = temp;
        mLocation = location;
        mIcon = bitmap;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getWeather() {
        return mWeather;
    }

    public void setWeather(String mWeather) {
        this.mWeather = mWeather;
    }

    public Temperature getTemp() {
        return mTemp;
    }

    public void setTemp(Temperature mTemp) {
        this.mTemp = mTemp;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location mLocation) {
        this.mLocation = mLocation;
    }

    public Bitmap getIcon() {
        return mIcon;
    }

    public void setIcon(Bitmap mIcon) {
        this.mIcon = mIcon;
    }
}
