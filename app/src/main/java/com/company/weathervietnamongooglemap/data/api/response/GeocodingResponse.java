package com.company.weathervietnamongooglemap.data.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeocodingResponse {
    @Expose
    @SerializedName("results")
    private List<ResultsResponse> resultsResponseList;

    public List<ResultsResponse> getResultsResponseList() {
        return resultsResponseList;
    }

    public void setResultsResponseList(List<ResultsResponse> resultsResponseList) {
        this.resultsResponseList = resultsResponseList;
    }
}
