package com.company.weathervietnamongooglemap.utls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.google.gson.JsonParser;
import com.company.weathervietnamongooglemap.data.model.DailyWeather;
import com.company.weathervietnamongooglemap.data.model.HourlyWeather;
import com.company.weathervietnamongooglemap.data.model.Location;
import com.company.weathervietnamongooglemap.data.model.Weather;
import com.company.weathervietnamongooglemap.data.model.WeatherForecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import static com.company.weathervietnamongooglemap.constants.Constants.IMG_URL;

public class JSONWeatherParser {

    public final static String COORDINATES = "coord",
            LATITUDE = "lat",
            LONGITUDE = "lon",
            EXTRAS = "sys",
            WEATHER = "weather",
            CURRENT = "current",
            WEATHER_TITLE = "main",
            WEATHER_DESCRIPTION = "description",
            EXTRAS_COUNTRY = "country",
            CITY = "name",
            WEATHER_ICON_STRING = "icon",
            MAIN = "main",
            CURRENT_TEMPERATURE = "temp",
            CURRENT_HUMIDITY = "humidity",
            CURRENT_PRESSURE = "pressure",
            CURRENT_MAX_TEMPERATURE = "max",
            CURRENT_MIN_TEMPERATURE = "min",
            WIND = "wind",
            WIND_SPEED = "speed",
            WIND_DEGREES = "deg",
            CLOUDS = "clouds",
            CLOUDINESS = "all",
            HOURLY = "hourly",
            DAILY = "daily",
            DAY = "day",
            EVE = "eve",
            MOR = "mor",
            NIGHT = "night";

    public static WeatherForecast getWeather(String data) throws JSONException {
        JsonParser jsonParser = new JsonParser();
        JSONObject mainObj = new JSONObject(data);
        Location location = new Location();
        location.setLatitude(getFloat(LATITUDE, mainObj));
        location.setLongitude(getFloat(LONGITUDE, mainObj));
        HourlyWeather hourlyWeather = new HourlyWeather();
        hourlyWeather.setListWeather(getHourlyWeather(mainObj, location));
        Weather currentWeather = getCurrentWeather(mainObj.getJSONObject(CURRENT), location, false);
        DailyWeather dailyWeather = new DailyWeather();
        dailyWeather.setWeatherList(getDailyWeather(mainObj, location));
        return new WeatherForecast(currentWeather, hourlyWeather, dailyWeather);
    }


    private static List<Weather> getHourlyWeather(JSONObject mainObj, Location location) throws JSONException {
        JSONArray hourlyArr = mainObj.getJSONArray(HOURLY);
        //HOURLY
        HourlyWeather hourlyWeather = new HourlyWeather();
        List<Weather> weathers = new ArrayList<>();
        for (int i = 0; i < hourlyArr.length(); i++) {
            JSONObject jsonObject = hourlyArr.getJSONObject(i);
            weathers.add(getCurrentWeather(jsonObject, location, false));
        }
        hourlyWeather.setListWeather(weathers);
        return weathers;

    }

    private static List<Weather> getDailyWeather(JSONObject mainObj, Location location) throws JSONException {
        JSONArray hourlyArr = mainObj.getJSONArray(DAILY);
        DailyWeather dailyWeather = new DailyWeather();
        List<Weather> weathers = new ArrayList<>();
        for (int i = 0; i < hourlyArr.length(); i++) {
            JSONObject jsonObject = hourlyArr.getJSONObject(i);
            weathers.add(getCurrentWeather(jsonObject, location, true));
        }
        dailyWeather.setWeatherList(weathers);
        return weathers;

    }

    private static Weather getCurrentWeather(JSONObject mainObj, Location location, boolean isTempArr) throws JSONException {
        JSONObject weatherObj = mainObj.getJSONArray(WEATHER).getJSONObject(0);
        Weather weather = new Weather();
        // CURRENT
        weather.setWeather(getString(WEATHER_DESCRIPTION, weatherObj));
        weather.setIcon(getBitmapFromURL(getString(WEATHER_ICON_STRING, weatherObj)));
        weather.getLocation().setLatitude(location.getLatitude());
        weather.getLocation().setLongitude(location.getLongitude());
        if (!isTempArr){
            weather.getTemp().setTemperature(getFloat(CURRENT_TEMPERATURE, mainObj));
        }

        else {
            JSONObject tempObj = getObject(CURRENT_TEMPERATURE, mainObj);
            weather.getTemp().setMaxTemperature(getFloat(CURRENT_MAX_TEMPERATURE, tempObj));
            weather.getTemp().setMinTemperature(getFloat(CURRENT_MIN_TEMPERATURE, tempObj));

        }
        return weather;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        return jObj.getJSONObject(tagName);
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

    public static Bitmap getBitmapFromURL(String icon) {
        try {
            java.net.URL url = new java.net.URL(IMG_URL + icon + ".png");
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getResizedBitmap(Bitmap bmp, int newHeight, int newWidth) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height,
                matrix, false);

        return resizedBitmap;
    }

}
