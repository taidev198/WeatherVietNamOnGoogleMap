package com.company.weathervietnamongooglemap.data.model;

import java.util.Date;

public class CurrentWeather {
    private Date mDate;
    private Temperature mTemp;
    private String mWeather;
    private Location location;
    private String mAddress;
    private String mDes;
    // Downloaded separately
    private String mIcon;

    public CurrentWeather() {
        location = new Location();
        mTemp = new Temperature();
        mDate = new Date();
    }

    public CurrentWeather(Date date, Temperature temp, String weather, String icon) {
        mDate = date;
        mTemp = temp;
        mWeather = weather;
        mIcon = icon;
    }

    private CurrentWeather(WeatherBuilder weatherBuilder) {
        mTemp = new Temperature((float) weatherBuilder.mTemp);
        mIcon = weatherBuilder.mIcon;
        mAddress = weatherBuilder.mAddress;
        mDes = weatherBuilder.mDescription;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Temperature getTemp() {
        return mTemp;
    }

    public void setTemp(Temperature temp) {
        mTemp = temp;
    }

    public String getWeather() {
        return mWeather;
    }

    public void setWeather(String weather) {
        mWeather = weather;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getDes() {
        return mDes;
    }

    public void setDes(String mDes) {
        this.mDes = mDes;
    }

    public static class WeatherBuilder {
        private String mDescription;
        private double mTemp;
        private String mAddress;
        private String mIcon;


        public WeatherBuilder(String des) {
            mDescription = des;
        }

        public WeatherBuilder setTemp(double temp) {
            mTemp = temp;
            return this;
        }

        public WeatherBuilder setAddress(String address) {
            mAddress = address;
            return this;
        }

        public WeatherBuilder setIcon(String icon) {
            mIcon = icon;
            return this;
        }

        public CurrentWeather build(){
            return new CurrentWeather(this);
        }
    }
}