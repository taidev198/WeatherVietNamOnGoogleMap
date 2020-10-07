package com.company.weathervietnamongooglemap.ui.map;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.company.weathervietnamongooglemap.ui.base.BasePresenter;
import com.company.weathervietnamongooglemap.ui.base.BaseView;

public interface MapContract {
    interface View extends BaseView<Presenter> {
        void loadMap();
        void showLocationPermissionNeeded();
        void addMarkerToMap(MarkerOptions options, LatLng latLng);
    }
    interface Presenter extends BasePresenter {
        void locationPermissionGranted();
        void locationPermissionRefused();
        void requestGps();
        void addMarker(LatLng latLng);
    }
}