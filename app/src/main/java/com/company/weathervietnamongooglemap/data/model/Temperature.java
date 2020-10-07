package com.company.weathervietnamongooglemap.data.model;

public class Temperature {
    private float temperature;
    private float minTemperature;
    private float maxTemperature;

    public Temperature() {

    }

    public Temperature(float temp) {
        temperature = temp;
    }

    public Temperature(float temp,
                       float minTemp,
                       float maxTemp) {

        temperature = temp;
        minTemperature = minTemp;
        maxTemperature = maxTemp;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(float minTemperature) {
        this.minTemperature = minTemperature;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }
}
