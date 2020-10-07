package com.company.weathervietnamongooglemap.data.local.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.company.weathervietnamongooglemap.data.api.response.WeatherForecastResponse;
import com.company.weathervietnamongooglemap.data.local.AppDatabase;
import com.company.weathervietnamongooglemap.data.local.entry.WeatherEntry;
import com.company.weathervietnamongooglemap.data.model.CurrentWeather;

public class WeatherDAOImpl extends AppDatabase implements WeatherDAO {

    private static WeatherDAOImpl sWeatherDAOImpl;
    private static int WEATHER_ID = 1;

    private WeatherDAOImpl(Context context, WeatherDAOImpl weatherDAO) {
        super(context);
        sWeatherDAOImpl = weatherDAO;
    }

    public static WeatherDAOImpl getInstance(Context context, WeatherDAOImpl weatherDAO) {
        if (sWeatherDAOImpl == null) {
            sWeatherDAOImpl = new WeatherDAOImpl(context, weatherDAO);
        }
        return sWeatherDAOImpl;
    }


    @Override
    public CurrentWeather getCurrentWeather() {
        mSQLiteDatabase = getReadableDatabase();
        Cursor cursor = mSQLiteDatabase.query(WeatherEntry.TABLE_NAME,
                null, null,
                null, null, null, null);
        CurrentWeather currentWeather = new CurrentWeather();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                 currentWeather = createCurrentWeather(cursor);
                cursor.moveToNext();
            }
        }
        cursor.close();
        mSQLiteDatabase.close();
        return currentWeather;
    }

    private CurrentWeather createCurrentWeather(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(WeatherEntry.ID));
        return new CurrentWeather.WeatherBuilder(cursor.getString(cursor.getColumnIndex(WeatherEntry.WEATHER_DES)))
                .setAddress(cursor.getString(cursor.getColumnIndex(WeatherEntry.WEATHER_DES)))
                .setIcon(cursor.getString(cursor.getColumnIndex(WeatherEntry.WEATHER_ICON)))
                .setTemp(Double.parseDouble(cursor.getString(cursor.getColumnIndex(WeatherEntry.WEATHER_TEMP))))
                .build();
    }

    @Override
    public void SaveCurrentWeather(WeatherForecastResponse weatherForecastResponse) {
        mSQLiteDatabase = getWritableDatabase();
        String where = WeatherEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(WEATHER_ID)};
        ContentValues values = new ContentValues();
        values.put(WeatherEntry.ID, WEATHER_ID);
        values.put(WeatherEntry.WEATHER_DES, weatherForecastResponse.getCurrentWeather().getWeathers().get(0).getDescription());
        values.put(WeatherEntry.WEATHER_TEMP, weatherForecastResponse.getCurrentWeather().getTemp());
        values.put(WeatherEntry.ADDRESS, weatherForecastResponse.getAddress());
        values.put(WeatherEntry.WEATHER_ICON, weatherForecastResponse.getCurrentWeather().getWeathers().get(0).getIcon());
        int result = mSQLiteDatabase.update(WeatherEntry.TABLE_NAME, values, where, whereArgs);
        mSQLiteDatabase.close();
    }
}
