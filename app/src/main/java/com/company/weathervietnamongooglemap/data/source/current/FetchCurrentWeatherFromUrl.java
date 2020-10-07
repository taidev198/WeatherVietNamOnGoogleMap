package com.company.weathervietnamongooglemap.data.source.current;

import android.os.AsyncTask;

import com.company.weathervietnamongooglemap.constants.Constants;
import com.company.weathervietnamongooglemap.data.model.CurrentWeather;
import com.company.weathervietnamongooglemap.data.model.WeatherForecast;
import com.company.weathervietnamongooglemap.data.source.CurrentWeatherDataSource;
import com.company.weathervietnamongooglemap.utls.JSONWeatherParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchCurrentWeatherFromUrl extends AsyncTask<String, Void, WeatherForecast> {

    private CurrentWeatherDataSource.OnFetchDataListener mListener;
    private Exception mException;

    public FetchCurrentWeatherFromUrl(CurrentWeatherDataSource.OnFetchDataListener listener) {
        mListener = listener;
    }

    @Override
    protected WeatherForecast doInBackground(String... strings) {
        String url = strings[0];
        try {
            String data = getStringDataFromUrl(url);

            return  JSONWeatherParser.getWeather(data);
        } catch (IOException | JSONException e) {
            mException = e;
            System.out.println(e);
        }
        return null;
    }

    private CurrentWeather getCurrentWeatherFromStringData(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);

        return null;
    }

    private String getStringDataFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(Constants.REQUEST_METHOD_GET);
        connection.setConnectTimeout(Constants.CONNECT_TIME_OUT);
        connection.setReadTimeout(Constants.READ_TIME_OUT);
        connection.setDoOutput(true);
        connection.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(Constants.BREAK_LINE);
        }
        br.close();
        connection.disconnect();
        return sb.toString();
    }

    @Override
    protected void onPostExecute(WeatherForecast weatherForecast) {
        if (mListener == null) {
            return;
        }
        if (mException == null) {
            System.out.println(weatherForecast.toString());
            mListener.onFetchDataSuccess(weatherForecast);
        } else {
            System.out.println(mException.getLocalizedMessage());
            mListener.onFetchDataFailure(mException);
        }
    }
}
