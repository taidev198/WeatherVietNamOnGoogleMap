package com.company.weathervietnamongooglemap.utls;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.company.weathervietnamongooglemap.R;
import com.company.weathervietnamongooglemap.constants.Constants;
import com.company.weathervietnamongooglemap.data.api.UtilsApi;
import com.company.weathervietnamongooglemap.data.api.WeatherApi;
import com.company.weathervietnamongooglemap.data.api.response.GeocodingResponse;
import com.company.weathervietnamongooglemap.data.api.response.WeatherForecastResponse;
import com.company.weathervietnamongooglemap.data.model.PlaceMark;
import com.company.weathervietnamongooglemap.data.model.PlaceMarkList;
import com.company.weathervietnamongooglemap.ui.appwidget.WeatherAppWidget;
import com.company.weathervietnamongooglemap.data.OnGetData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class Methods {


    public static void fetchingWeatherForecast(WeatherApi weatherApi, String lat, String lon, String province, String district,
                                               OnGetWeatherInfo listener) {
        weatherApi.requestRepos(lat,
                lon,
                "hourly,daily",
                "vi",
                "metric",
                "e370756ec8af6d31ce5f25668bf0bee8").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherForecastResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(WeatherForecastResponse weatherForecastResponses) {
                        weatherForecastResponses.setAddress(district + "," + province);
                        listener.OnGetWeatherInfoSuccess(weatherForecastResponses);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public static void getCoorsFromAddress(WeatherApi weatherApi, String province, String district, OnGetWeatherInfoFromAddress listener) {
        weatherApi.getCoors("https://maps.googleapis.com/maps/api/geocode/json?address="
                + district + Constants.COMMA+province + "&key=" + "AIzaSyAqdRuDwUbXJTQ1WwdIIR6_F3k3etpb5Og")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GeocodingResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GeocodingResponse geocodingResponse) {
                        listener.OnGetWeatherInfoFromAddressSuccess(new LatLng(geocodingResponse.getResultsResponseList().get(0).getGeometryResponse().getLocation().getLat(),
                                            geocodingResponse.getResultsResponseList().get(0).getGeometryResponse().getLocation().getLon()),
                                province, district);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**https://stackoverflow.com/questions/24158321/use-retrofit-to-download-image-file*/
    public static void DownloadImage(WeatherApi weatherApi, double lat, double lon, OnDownloadImage listener){

        @SuppressLint("DefaultLocale") String urlFormatted =
                String.format("https://tilecache.rainviewer.com/v2/radar/1601988600/%d/%d/%f/%f/%d/0_0.png",
                        Constants.IMAGE_SIZE,
                        3,
                        lat,
                        lon,
                        Constants.IMAGE_COLOR);

        System.out.println(urlFormatted);
        weatherApi.downloadImage(urlFormatted)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Bitmap bm = BitmapFactory.decodeStream(responseBody.byteStream());
                        listener.OnDownloadImageSuccess(new LatLng(lat, lon), bm);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public static PlaceMarkList getPlaceMarkList(Context context) {
        InputStream in_s = context.getResources().openRawResource(R.raw.diaphanhuyen);
        PlaceMarkList placeMarkList = new PlaceMarkList();
        List<PlaceMark> mPlaceMarkList = new ArrayList<>();
        Map<String, Map<String, List<LatLng>>> mLocation = new LinkedHashMap<>();
        List<String> disList = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.parse(in_s, "UTF-8", "");
            for(Element e : doc.select("Placemark")) {
                PlaceMark placeMark = new PlaceMark();
                // the contents
                Elements es =  e.select("SimpleData");
                placeMark.setProvince(es.get(2).text());
                placeMark.setDistrict(es.get(3).text());
                if (mLocation.size() == 0 || !mLocation.containsKey(placeMark.getProvince())) {
                    if (mLocation.size() == 0) {
                        Map<String, List<LatLng>> dis = new LinkedHashMap<>();
                        dis.put("HUYỆN", new ArrayList<>());
                        mLocation.put("TỈNH", dis);
                    }
                    mLocation.put(placeMark.getProvince(), new LinkedHashMap<>());
                }

                    Map<String, List<LatLng>> dis = mLocation.get(placeMark.getProvince());
                    if (dis.size() == 0){
                        dis.put("HUYỆN", new ArrayList<>());
                        dis.put("TẤT CẢ", new ArrayList<>());
                    }

                    disList.add(placeMark.getDistrict());
                    placeMark.setPopulation(Integer.parseInt(es.get(4).text()));
                    String[] coors = e.select("coordinates").text().split(" ");
                    List<LatLng> latLngs = new ArrayList<>();
                    for (String coor : coors) {
                        String[] temp = coor.split(",");
                        latLngs.add(new LatLng(Double.parseDouble(temp[1]),
                                Double.parseDouble(temp[0])));
                    }
                    placeMark.setLatLngs(latLngs);
                    mPlaceMarkList.add(placeMark);
                    dis.put(placeMark.getDistrict(), latLngs);//dis - lat
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(mLocation.toString());
        placeMarkList.setPlaceMarkList(mPlaceMarkList);
        placeMarkList.setLocation(mLocation);
            return placeMarkList;
    }

    public static void fetchingWeather(final Context context, WeatherApi weatherApi, String lat, String lon, String address,
                                 OnGetData<WeatherForecastResponse> listener) {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.weather_app_widget);
        final ComponentName watchWidget = new ComponentName(context, WeatherAppWidget.class);
        Toast.makeText(context, "Requested", Toast.LENGTH_SHORT).show();
            weatherApi = UtilsApi.getAPIService();
            weatherApi.requestRepos(lat,
                    lon,
                    "hourly,daily",
                    "vi",
                    "metric",
                    "e370756ec8af6d31ce5f25668bf0bee8").subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<WeatherForecastResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(WeatherForecastResponse weatherForecastResponses) {
                            // Display weather data on widget
                           listener.onSuccess(weatherForecastResponses);

                        }
                        @Override
                        public void onError(Throwable e) {
                            System.out.println(e.toString());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
    }

    public static String formatDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        return df.format(c);
    }

    public static BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public static int getDrawable(String name, Context context) {
        return context.getResources().getIdentifier("icon_" + name,
                "drawable",
                context.getPackageName());
    }
    public interface OnGetWeatherInfo{
        void OnGetWeatherInfoSuccess(WeatherForecastResponse weatherForecastResponse);
        void OnGetWeatherInfoFailure(Exception e);
    }

    public interface OnGetWeatherInfoFromAddress{
        void OnGetWeatherInfoFromAddressSuccess(LatLng latLng, String province, String district);
        void OnGetWeatherInfoFromAddressFailure(Exception e);
    }

    public interface OnDownloadImage{
        void OnDownloadImageSuccess(LatLng latLng, Bitmap bitmap);
        void OnDownloadImageFailure(Exception e);
    }
}
