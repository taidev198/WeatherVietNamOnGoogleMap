package com.company.weathervietnamongooglemap.data;

public interface OnGetData<T> {

    void onSuccess(T t);
    void onFailure(Exception e);

}
