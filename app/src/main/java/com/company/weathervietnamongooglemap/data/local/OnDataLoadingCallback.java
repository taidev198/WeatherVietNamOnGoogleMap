package com.company.weathervietnamongooglemap.data.local;

public interface OnDataLoadingCallback<T> {

    void onDataLoaded(T data);

    void onDataNotAvailable(Exception e);

}