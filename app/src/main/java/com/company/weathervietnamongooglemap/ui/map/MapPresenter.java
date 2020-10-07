package com.company.weathervietnamongooglemap.ui.map;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

public class MapPresenter implements MapContract.Presenter,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    MapContract.View mView;
   public MapPresenter(MapContract.View view) {
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void locationPermissionGranted() {

    }

    @Override
    public void locationPermissionRefused() {

    }

    @Override
    public void requestGps() {

    }

    @Override
    public void addMarker(LatLng latLng) {

    }

    @Override
    public void start() {
        checkPermission();
    }

    private void checkPermission() {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED) {
//            // Permission not yet granted. Use requestPermissions().
//            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
//            // app-defined int constant. The callback method gets the
//            // result of the request.
//            if (ActivityCompat.shouldShowRequestPermissionRationale(mView., Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//            } else {
//                ActivityCompat.requestPermissions(,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        LOCATION_PERMISSION_REQUEST_CODE);
//                mView.loadMap();
//            }
//        } else {
//            // Permission already granted. Enable the SMS button.
//            //new AsyncTaskTest().execute();
//            mView.loadMap();
//        }
    }
}
