package com.company.weathervietnamongooglemap.data.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultsResponse {
    @Expose
    @SerializedName("geometry")
    private GeometryResponse geometryResponse;

    public GeometryResponse getGeometryResponse() {
        return geometryResponse;
    }

    public void setGeometryResponse(GeometryResponse geometryResponse) {
        this.geometryResponse = geometryResponse;
    }
}
