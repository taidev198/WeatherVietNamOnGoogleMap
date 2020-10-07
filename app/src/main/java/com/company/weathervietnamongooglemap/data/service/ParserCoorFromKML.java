package com.company.weathervietnamongooglemap.data.service;

import android.content.Context;
import android.os.AsyncTask;

import com.company.weathervietnamongooglemap.data.model.PlaceMarkList;
import com.company.weathervietnamongooglemap.utls.Methods;

public class ParserCoorFromKML extends AsyncTask<Void, Void, PlaceMarkList> {

    private PlaceMarkList mPlaceMarkList;
    private OnParsingData mListener;
    private Context mContext;

    public ParserCoorFromKML(Context context, OnParsingData listener) {
        mContext = context;
        mListener = listener;
    }


    @Override
    protected PlaceMarkList doInBackground(Void... voids) {

        return Methods.getPlaceMarkList(mContext);
    }

    @Override
    protected void onPostExecute(PlaceMarkList result) {
        super.onPostExecute(result);

        mListener.onSuccess(result);
    }

    public interface OnParsingData {
        void onSuccess(PlaceMarkList placeMarkList);
        void onFailure(Exception e);
    }
}
