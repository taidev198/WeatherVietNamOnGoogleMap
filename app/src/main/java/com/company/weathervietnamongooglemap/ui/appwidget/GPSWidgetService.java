package com.company.weathervietnamongooglemap.ui.appwidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;
import androidx.core.content.ContextCompat;
import com.company.weathervietnamongooglemap.R;
import com.company.weathervietnamongooglemap.data.api.WeatherApi;
import com.company.weathervietnamongooglemap.data.api.response.WeatherForecastResponse;
import com.company.weathervietnamongooglemap.data.api.response.WeatherResponse;
import com.company.weathervietnamongooglemap.data.OnGetData;
import com.company.weathervietnamongooglemap.utls.Methods;

import java.util.List;

public class GPSWidgetService extends Service implements OnGetData<WeatherForecastResponse> {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private OnGetData<WeatherForecastResponse> mListener;
    private WeatherApi mWeatherApi;
    private LocationManager manager = null;
    private String mAddress;
    private LocationListener listener = new LocationListener() {
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }

        @Override
        public void onLocationChanged(Location location) {
//            AppLog.logString("Service.onLocationChanged()");

            updateCoordinates(location.getLatitude(), location.getLongitude());

            stopSelf();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mListener = this;
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mAddress = "";
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        waitForGPSCoordinates();
    }

    @Override
    public void onDestroy() {
        stopListening();
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        waitForGPSCoordinates();
        return super.onStartCommand(intent, flags, startId);
    }

    private void waitForGPSCoordinates() {
        startListening();
    }

    private void startListening() {
        final Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        final String bestProvider = manager.getBestProvider(criteria, true);

        if (bestProvider != null && bestProvider.length() > 0) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                manager.requestLocationUpdates(bestProvider, 500, 10, listener);
            }
        }
        else {
            final List<String> providers = manager.getProviders(true);

            for (final String provider : providers) {
                manager.requestLocationUpdates(provider, 500, 10, listener);
            }
        }
    }

    private void stopListening(){
        try {
            if (manager != null && listener != null) {
                manager.removeUpdates(listener);
            }

            manager = null;
        }
        catch (final Exception ignored) {
        }
    }

    private void updateCoordinates(double latitude, double longitude){
        Geocoder coder = new Geocoder(this);
        List<Address> addresses = null;
        String info = "";
        try {
            addresses = coder.getFromLocation(latitude, longitude, 2);

            if(null != addresses && addresses.size() > 0){
                int addressCount = addresses.get(0).getMaxAddressLineIndex();

                if(-1 != addressCount){
                    for(int index=0; index<=addressCount; ++index){
                        info += addresses.get(0).getAddressLine(index);

                        if(index < addressCount)
                            info += ", ";
                    }
                    Methods.fetchingWeather(getApplicationContext(),
                            mWeatherApi,
                            Double.toString(latitude),
                            Double.toString(longitude),
                            info,
                            mListener);
                }
                else {
                    info += addresses.get(0).getFeatureName() + ", " + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getAdminArea();

                }
            }
            mAddress = info;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(WeatherForecastResponse weatherForecastResponse) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.weather_app_widget);
        ComponentName watchWidget = new ComponentName(this, WeatherAppWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        remoteViews.setTextViewText(R.id.date_weather_text, Methods.formatDate());
        List<WeatherResponse> weatherResponseList = weatherForecastResponse
                .getCurrentWeather()
                .getWeathers();
        remoteViews.setImageViewResource(R.id.icon_weather_widget,
                Methods.getDrawable(weatherResponseList.get(0).getIcon(),
                        getApplicationContext()));

        remoteViews.setTextViewText(R.id.address_weather_text, mAddress);
        remoteViews.setTextViewText(R.id.des_weather_widget, weatherResponseList
                .get(0)
                .getDescription());

        remoteViews.setTextViewText(R.id.temp_widget, String.valueOf(weatherForecastResponse
                .getCurrentWeather()
                .getTemp()));
        // Apply the changes
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    @Override
    public void onFailure(Exception e) {

    }
}