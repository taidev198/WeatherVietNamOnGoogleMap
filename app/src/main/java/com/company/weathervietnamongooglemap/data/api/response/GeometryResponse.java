package com.company.weathervietnamongooglemap.data.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeometryResponse {

    @Expose
    @SerializedName("location")
    private LocationResponse location;

    public LocationResponse getLocation() {
        return location;
    }

    public void setLocation(LocationResponse location) {
        this.location = location;
    }
}
