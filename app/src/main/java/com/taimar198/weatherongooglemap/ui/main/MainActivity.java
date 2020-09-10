package com.taimar198.weatherongooglemap.ui.main;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;
import com.taimar198.weatherongooglemap.R;
import com.taimar198.weatherongooglemap.data.model.CurrentWeather;
import com.taimar198.weatherongooglemap.data.repository.CurrentWeatherRepository;
import com.taimar198.weatherongooglemap.data.source.CurrentWeatherDataSource;
import com.taimar198.weatherongooglemap.ui.search.SearchContract;
import com.taimar198.weatherongooglemap.ui.search.SearchPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**https://guides.codepath.com/android/Google-Maps-API-v2-Usage*/
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        CurrentWeatherDataSource.OnFetchDataListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        SearchContract.View,
        SearchView.OnQueryTextListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private CurrentWeatherRepository mCurrentWeatherRepository;
    private SearchPresenter mSearchPresenter;
    private SearchView mSearchView;
    private Circle circle;

    TileProvider tileProvider = new UrlTileProvider(256, 256) {
        @Override
        public URL getTileUrl(int x, int y, int zoom) {

            /* Define the URL pattern for the tile images */
            String s = String.format("https://openweathermap.org/img/wn/10d@2x.png",
                    zoom, x, y);

//            if (!checkTileExists(x, y, zoom)) {
//                return null;
//            }

            try {
                return new URL(s);
            } catch (MalformedURLException e) {
                throw new AssertionError(e);
            }
        }

        /*
         * Check that the tile server supports the requested x, y and zoom.
         * Complete this stub according to the tile range you support.
         * If you support a limited range of tiles at different zoom levels, then you
         * need to define the supported x, y range at each zoom level.
         */
        private boolean checkTileExists(int x, int y, int zoom) {
            int minZoom = 12;
            int maxZoom = 16;

            if ((zoom < minZoom || zoom > maxZoom)) {
                return false;
            }

            return true;
        }
    };

    TileOverlay tileOverlay ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        init();

    }

    private void init() {
        addAction();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mSearchView = findViewById(R.id.search_view);
        mCurrentWeatherRepository = CurrentWeatherRepository.getInstance();
        mSearchPresenter = new SearchPresenter(this, mCurrentWeatherRepository);
        mSearchPresenter.start();
        mCurrentWeatherRepository.getCurrentWeather(this, "21.027763", "105.834160");

    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            // Permission already granted. Enable the SMS button.
            //new AsyncTaskTest().execute();
            if(mMap != null) {
                mMap.setMyLocationEnabled(true);
                setUpMap();
            }
        }
    }

    private void setUpMap() {

        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        View locationButton = ((View) findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);rlp.setMargins(0,0,30,30);
        circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(21.027763, 105.834160))
                .radius(1000)
                .strokeWidth(10)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(128, 255, 0, 0))
                .clickable(true));

        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                // Flip the r, g and b components of the circle's
                // stroke color.
                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(-37.81319, 144.96298), new LatLng(-31.95285, 115.85734))
                .width(25)
                .color(Color.BLUE)
                .geodesic(true));
        LatLng NEWARK = new LatLng(21.027763, 105.834160);

//        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
//                .image(BitmapDescriptorFactory.fromResource(R.drawable.custom_marker))
//                .position(NEWARK, 8600f, 6500f);
//        mMap.addGroundOverlay(newarkMap);
//        tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
//                .tileProvider(tileProvider)
//                .transparency(0.5f));
    }

    private void setOnClick() {
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
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
        checkPermission();
        setOnClick();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng hanoi = new LatLng(21.027763, 105.834160);
        mMap.addMarker(new MarkerOptions()
        .position(hanoi));
//        mMap.addMarker(new MarkerOptions()
//                .position(hanoi)
//                .title("Ha Noi")
//                .snippet("Description")
//                .icon(BitmapDescriptorFactory
//                        .fromBitmap(createDrawableFromView(
//                                this,
//                                markerView))));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(hanoi));
        try {
            ArrayList<WeightedLatLng> result = generateHeatMapData();
//            new CrimeData().getWeightedPositions();
            HeatmapTileProvider heatmapTileProvider = new HeatmapTileProvider.Builder()
                    .weightedData(result) // load our weighted data
                    .radius(50) // optional, in pixels, can be anything between 20 and 50
                    .maxIntensity(1000.0) // set the maximum intensity
                    .build();
            mMap.addTileOverlay(new TileOverlayOptions().tileProvider(heatmapTileProvider));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void updateTileOverlayTransparency() {
        if (tileOverlay != null) {
            // Switch between 0.0f and 0.5f transparency.
            tileOverlay.setTransparency(0.5f - tileOverlay.getTransparency());
        }
    }

    private ArrayList<WeightedLatLng> generateHeatMapData() throws JSONException {
         ArrayList<WeightedLatLng> result = new ArrayList<>();

        JSONArray jsonData = getJsonDataFromAsset("data.json");
        for (int i =0; i< jsonData.length(); i++){

            JSONObject jsonObject = jsonData.getJSONObject(i);
                double lat = jsonObject.getDouble("lat");
            double lon = jsonObject.getDouble("lon");
            double density = jsonObject.getDouble("density");

                if (density != 0.0) {
                    WeightedLatLng weightedLatLng = new WeightedLatLng(new LatLng(lat, lon), density);
                    result.add(weightedLatLng);
                }
            }
        return result;
    }


    private JSONArray getJsonDataFromAsset(String fileName) throws JSONException {
        String json = null;
        try {
            InputStream is = this.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return new JSONArray(json);
    }

    private Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    public void onFetchDataSuccess(CurrentWeather data) {
        mMap.setInfoWindowAdapter(new CustomWindowAdapter(getLayoutInflater(),data));
    }

    @Override
    public void onFetchDataFailure(Exception e) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        System.out.println("location" + location.getLatitude() +"---" + location.getLongitude());
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        mSearchPresenter.searchCityNameResult(s);
        System.out.println(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void showIntroSearch() {

    }

    @Override
    public void hideIntroSearch() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showSearchResult(CurrentWeather currentWeather) {

    }

    @Override
    public void showError(String errMsg) {

    }

    @Override
    public void showSuccess(String msg) {

    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {

    }
//add action to searchview
    private void addAction() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        mSearchView.setOnQueryTextListener(this);
    }
}
