package com.company.weathervietnamongooglemap.data.api.response;

import com.google.gson.annotations.SerializedName;

public class HourlyWeatherResponse {
    @SerializedName("")
    private int stargazersCount;

    @SerializedName("pushed_at")
    private String pushedAt;

    @SerializedName("subscription_url")
    private String subscriptionUrl;


}
