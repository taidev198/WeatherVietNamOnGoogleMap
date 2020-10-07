package com.company.weathervietnamongooglemap.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.company.weathervietnamongooglemap.BuildConfig;
import com.company.weathervietnamongooglemap.R;
import com.company.weathervietnamongooglemap.data.api.UtilsApi;
import com.company.weathervietnamongooglemap.data.api.WeatherApi;
import com.company.weathervietnamongooglemap.data.api.response.WeatherForecastResponse;
import com.company.weathervietnamongooglemap.data.api.response.WeatherResponse;
import com.company.weathervietnamongooglemap.data.model.PlaceMarkList;
import com.company.weathervietnamongooglemap.data.model.WeatherRenderer;
import com.company.weathervietnamongooglemap.data.service.ParserCoorFromKML;
import com.company.weathervietnamongooglemap.ui.addressspinner.SpinnerProvinceListener;
import com.company.weathervietnamongooglemap.ui.appwidget.WeatherAppWidget;
import com.company.weathervietnamongooglemap.utls.Methods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**https://guides.codepath.com/android/Google-Maps-API-v2-Usage
 * https://stackoverflow.com/questions/15636303/extract-coordinates-from-kml-file-in-java
 * https://stackoverflow.com/questions/42516114/geoxml3-accessing-kml-attribute-datas
 * https://stackoverflow.com/questions/46346531/parsing-kml-in-java
 * https://stackoverflow.com/questions/1140144/read-and-parse-kml-in-java
 * https://developers.google.com/maps/documentation/geocoding/overview
 * https://stackoverflow.com/questions/3109158/how-to-draw-a-path-on-a-map-using-kml-file
 * https://www.tutlane.com/tutorial/android/android-xml-parsing-using-sax-parser
 * https://github.com/openstreetmap/splitter/blob/master/src/uk/me/parabola/splitter/kml/KmlParser.java
 * tutlane.com/tutorial/android/android-xml-parsing-using-sax-parser
 * //spinner
 * https://mkyong.com/android/android-spinner-drop-down-list-example/
 * //get current location
 * https://www.google.com/search?q=reading+coordinates+in+kml+file+with+jsoup&oq=reading+coordinates+in+kml+file+with+jsoup&aqs=chrome..69i57.19692j0j4&sourceid=chrome&ie=UTF-8
 * */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        ParserCoorFromKML.OnParsingData,
        AdapterView.OnItemSelectedListener,
        Methods.OnGetWeatherInfoFromAddress,
        Methods.OnGetWeatherInfo,
        GoogleMap.OnInfoWindowClickListener,
        Methods.OnDownloadImage,
        View.OnClickListener,
        SensorEventListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 123;
    private GoogleMap mMap;
    private PlacesClient placesClient;
    private boolean locationPermissionGranted = false;
    private static final int DEFAULT_ZOOM = 15;
    private static final int DISTRICT_ZOOM = 12;
    private WeatherApi mWeatherApi;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastKnownLocation;
    private WeatherForecastResponse mWeatherForecastResponse;
    private PlaceMarkList mPlaceMarkList;
    private ViewPager mPager;
    private Spinner mSpinnerProvince;
    private Spinner mSpinnerDistrict;
    private ClusterManager<WeatherResponse> mCMWeather;
    private FragmentStatePagerAdapter pagerAdapter;
    private ArrayList<LatLng>  mLatLngList = new ArrayList<>();
    private Methods.OnGetWeatherInfoFromAddress mListener;
    private ClusterManager<WeatherForecastResponse> mClusterManager;
    private Random mRandom = new Random(1984);
    private Methods.OnGetWeatherInfo mWeatherListener;
    private HeatmapTileProvider mProvider;
    private Map<Bitmap, LatLng> mRadar;
    private Button mShowRadarBtn;
    private GroundOverlay mGroundOverlay;
    private boolean mIsRadarShown;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        init();
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    private void init() {
        mListener = this;
        mWeatherListener = this;
        mSpinnerProvince = findViewById(R.id.spinner_province);
        mSpinnerDistrict = findViewById(R.id.spinner_district);
        mShowRadarBtn = findViewById(R.id.showRadarStorm);
        mShowRadarBtn.setOnClickListener(this);
        mRadar = new HashMap<>();
        new ParserCoorFromKML(getApplicationContext(), this).execute();
        mWeatherForecastResponse = new WeatherForecastResponse();
        mPager = findViewById(R.id.weather_pager);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mWeatherApi = UtilsApi.getAPIService();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //init autocomplete
        initAutocomplete();

    }

    private void initAutocomplete() {

    }

    private void setUpMap() {
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        //change position of my location
        View locationButton = ((View) findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);rlp.setMargins(0,0,30,30);
        mMap.setOnCircleClickListener(circle -> {
            // Flip the r, g and b components of the circle's
            // stroke color.
            int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
            circle.setStrokeColor(strokeColor);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }

    }
    updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (!locationPermissionGranted) {
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
//
    }


    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
            mMap.setMyLocationEnabled(true);
            setUpMap();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }
    }
//https://stackoverflow.com/questions/9409195/how-to-get-complete-address-from-latitude-and-longitude
    //https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        lastKnownLocation = task.getResult();
                        //get address from lat and long
                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                        try {
                            addresses = geocoder.getFromLocation(lastKnownLocation.getLatitude(),
                                    lastKnownLocation.getLongitude(),
                                    1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String city = addresses.get(0).getLocality();
                            System.out.println(address + "---" + city);
                            if (lastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                Methods.fetchingWeatherForecast(mWeatherApi, Double.toString(lastKnownLocation.getLatitude()),
                                        Double.toString(lastKnownLocation.getLongitude()), address, "", mWeatherListener);
                                Intent intent = new Intent(WeatherAppWidget.ACTION_TEXT_CHANGED);
                                intent.putExtra("NewString", "tai");
                                getApplicationContext().sendBroadcast(intent);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // Set the map's camera position to the current location of the device.
                    } else {

//                            mMap.moveCamera(CameraUpdateFactory
//                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    @Override
    public void onSuccess(PlaceMarkList placeMarkList) {
        mPlaceMarkList = placeMarkList;
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,
                placeMarkList.getLocation().keySet().toArray(new String[0]));
        mSpinnerProvince.setOnItemSelectedListener(new SpinnerProvinceListener(this, placeMarkList.getLocation()));
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        mSpinnerProvince.setAdapter(aa);
    }

    @Override
    public void onFailure(Exception e) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        String province = mSpinnerProvince.getSelectedItem().toString();
        String district = adapterView.getItemAtPosition(pos).toString();

        List<LatLng> latLngsFromAddress = new ArrayList<>();
        if (!province.equals("TỈNH") && !district.equals("HUYỆN") && !district.equals("TẤT CẢ")) {
            List<LatLng> latLngs = mPlaceMarkList.getLocation()
                    .get(province)
                    .get(district);
            Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .addAll(latLngs));
          Methods.getCoorsFromAddress(mWeatherApi, province, district, this);

        }else if (district.equals("TẤT CẢ")) {
            Map<String, List<LatLng>> districtList = mPlaceMarkList.getLocation()
                    .get(province);
            String[] disString = districtList.keySet().toArray(new String[0]);
            for (String s : disString) {
                if (!s.equals("HUYỆN") && !s.equals("TẤT CẢ")) {
                    Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                            .clickable(true)
                            .addAll(districtList.get(s)));
                    Methods.getCoorsFromAddress(mWeatherApi, province, s, this);
                }
            }

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void addMarkerToMap(LatLng latLng){
        mMap.addMarker(new MarkerOptions().position(latLng));
    }


/***https://stackoverflow.com/questions/25968486/how-to-add-info-window-for-clustering-marker-in-android
 * https://stackoverflow.com/questions/21885225/showing-custom-infowindow-for-android-maps-utility-library-for-android/21964693#21964693*/
    @Override
    public void OnGetWeatherInfoSuccess(WeatherForecastResponse weatherForecastResponse) {
        Marker marker = mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(Methods.getDrawable(weatherForecastResponse.getCurrentWeather().getWeathers().get(0).getIcon(), this)))
                .position(weatherForecastResponse.getPosition())
        .snippet(weatherForecastResponse.getCurrentWeather().getWeathers().get(0).getDescription())
        .title(weatherForecastResponse.getAddress()));
        marker.showInfoWindow();
    }

    @Override
    public void OnGetWeatherInfoFailure(Exception e) {

    }

    @Override
    public void OnGetWeatherInfoFromAddressSuccess(LatLng latLng, String province, String district) {
        Methods.fetchingWeatherForecast(mWeatherApi,
                Double.toString(latLng.latitude),
                Double.toString(latLng.longitude),
                province,
                district,
                this);
        mMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(latLng, DISTRICT_ZOOM));
        Methods.DownloadImage(mWeatherApi, latLng.latitude, latLng.longitude, this);
    }

    @Override
    public void OnGetWeatherInfoFromAddressFailure(Exception e) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        marker.showInfoWindow();
    }

    @Override
    public void OnDownloadImageSuccess(LatLng latLng, Bitmap bitmap) {
        if (mRadar.size() == 0) {
            mRadar.put(bitmap, latLng);
        }

    }

    @Override
    public void OnDownloadImageFailure(Exception e) {

    }

    @Override
    public void onClick(View view) {

        if (!mIsRadarShown)
            showRadar();
        else hideRadar();
    }

    private void showRadar() {
        if (!mIsRadarShown) {
            Bitmap bitmap = mRadar.keySet().toArray(new Bitmap[0])[0];
            GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromBitmap(bitmap))
                    .position(mRadar.get(bitmap), 450000f, 450000f);

// Add an overlay to the map, retaining a handle to the GroundOverlay object.
            mGroundOverlay = mMap.addGroundOverlay(newarkMap);
            mIsRadarShown = !mIsRadarShown;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float millibarsOfPressure = event.values[0];
        // Do something with this sensor data.
       // System.out.println(millibarsOfPressure + "temp");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void hideRadar() {
        mGroundOverlay.remove();
        mIsRadarShown = !mIsRadarShown;
    }

}
