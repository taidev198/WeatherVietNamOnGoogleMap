package com.company.weathervietnamongooglemap.data.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class PlaceMark {

    private List<LatLng> mLatLngs;
    private String mProvince;
    private String mDistrict;
    private int mPopulation;

    public PlaceMark() {}

    public PlaceMark(List<LatLng> mLatLngs, String mProvince, String mDistrict, int mPopulation) {
        this.mLatLngs = mLatLngs;
        this.mProvince = mProvince;
        this.mDistrict = mDistrict;
        this.mPopulation = mPopulation;
    }

    public List<LatLng> getLatLngs() {
        return mLatLngs;
    }

    public void setLatLngs(List<LatLng> mLatLngs) {
        this.mLatLngs = mLatLngs;
    }

    public String getProvince() {
        return mProvince;
    }

    public void setProvince(String mProvince) {
        this.mProvince = mProvince;
    }

    public String getDistrict() {
        return mDistrict;
    }

    public void setDistrict(String mDistrict) {
        this.mDistrict = mDistrict;
    }

    public int getPopulation() {
        return mPopulation;
    }

    public void setPopulation(int mPopulation) {
        this.mPopulation = mPopulation;
    }
}
