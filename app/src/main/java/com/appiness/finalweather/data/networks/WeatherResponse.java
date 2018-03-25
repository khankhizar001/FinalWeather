package com.appiness.finalweather.data.networks;

import android.support.annotation.NonNull;

import com.appiness.finalweather.data.database.WeatherEntry;

/**
 * Created by appiness on 24/3/18.
 */

public class WeatherResponse {
    @NonNull
    private final WeatherEntry[] mWeatherForecast;

    public WeatherResponse(@NonNull final WeatherEntry[] weatherForecast) {
        mWeatherForecast = weatherForecast;
    }

    public WeatherEntry[] getWeatherForecast() {
        return mWeatherForecast;
    }
}
