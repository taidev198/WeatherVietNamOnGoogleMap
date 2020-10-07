package com.company.weathervietnamongooglemap.data.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Map;

public class PlaceMarkList {

    private List<PlaceMark> mPlaceMarkList;
    private Map<String, Map<String, List<LatLng>>> mLocation;

    public PlaceMarkList() {

    }

    public List<PlaceMark> getPlaceMarkList() {
        return mPlaceMarkList;
    }

    public void setPlaceMarkList(List<PlaceMark> mPlaceMarkList) {
        this.mPlaceMarkList = mPlaceMarkList;
    }

    public Map<String, Map<String, List<LatLng>>> getLocation() {
        return mLocation;
    }

    public void setLocation(Map<String, Map<String, List<LatLng>>> mLocation) {
        this.mLocation = mLocation;
    }
}
