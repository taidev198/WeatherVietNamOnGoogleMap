package com.company.weathervietnamongooglemap.data.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TempResponse {
    @Expose
    @SerializedName("day")
    private float day;
    @Expose
    @SerializedName("mor")
    private float mor;
    @Expose
    @SerializedName("eve")
    private float eve;
    @Expose
    @SerializedName("night")
    private float night;
    @Expose
    @SerializedName("min")
    private float min;
    @Expose
    @SerializedName("max")
    private float max;

    public float getDay() {
        return day;
    }

    public void setDay(float day) {
        this.day = day;
    }

    public float getMor() {
        return mor;
    }

    public void setMor(float mor) {
        this.mor = mor;
    }

    public float getEve() {
        return eve;
    }

    public void setEve(float eve) {
        this.eve = eve;
    }

    public float getNight() {
        return night;
    }

    public void setNight(float night) {
        this.night = night;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }
}
