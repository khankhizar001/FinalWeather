package com.appiness.finalweather.networks;

import com.appiness.finalweather.model.Weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by appiness on 24/3/18.
 */

public interface ApiInterface {

    @GET("/forecast")
    void getWeatherInfo (@Query("lat") String latitude,
                         @Query("lon") String longitude,
                         @Query("cnt") String cnt,
                         @Query("appid") String appid,
                         Callback<Weather> cb);
}
