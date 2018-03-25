package com.appiness.finalweather.data.networks;

import android.support.annotation.Nullable;
import android.util.Log;

import com.appiness.finalweather.data.database.WeatherEntry;
import com.appiness.finalweather.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Date;

/**
 * Created by appiness on 24/3/18.
 */

final class OpenWeatherJsonParser {
    private static final String OWM_LIST = "list";

    private static final String OWM_PRESSURE = "pressure";
    private static final String OWM_HUMIDITY = "humidity";
    private static final String OWM_WINDSPEED = "speed";
    private static final String OWM_WIND_DIRECTION = "deg";

    private static final String OWM_TEMPERATURE = "main";

    private static final String OWN_WIND ="wind";

    private static final String OWM_MAX = "temp_max";
    private static final String OWM_MIN = "temp_min";

    private static final String OWM_WEATHER = "weather";
    private static final String OWM_WEATHER_ID = "id";

    private static final String OWM_MESSAGE_CODE = "cod";

    private static boolean hasHttpError(JSONObject forecastJson) throws JSONException {
        if (forecastJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    return false;
                case HttpURLConnection.HTTP_NOT_FOUND:

                default:
                    return true;
            }
        }
        return false;
    }

    private static WeatherEntry[] fromJson(final JSONObject forecastJson) throws JSONException {
        JSONArray jsonWeatherArray = forecastJson.getJSONArray(OWM_LIST);
        Log.d("LIst : ",jsonWeatherArray.toString());

        WeatherEntry[] weatherEntries = new WeatherEntry[jsonWeatherArray.length()];

        long normalizedUtcStartDay = DateUtils.getNormalizedUtcMsForToday();

        for (int i = 0; i < jsonWeatherArray.length(); i++) {
            JSONObject dayForecast = jsonWeatherArray.getJSONObject(i);

            long dateTimeMillis = normalizedUtcStartDay + DateUtils.DAY_IN_MILLIS * i;
            WeatherEntry weather = fromJson(dayForecast, dateTimeMillis);

            weatherEntries[i] = weather;
        }
        return weatherEntries;
    }

    private static WeatherEntry fromJson(final JSONObject dayForecast,
                                         long dateTimeMillis) throws JSONException {

        JSONObject weatherObject =
                dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);

        int weatherId = weatherObject.getInt(OWM_WEATHER_ID);

        JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
        double pressure = temperatureObject.getDouble(OWM_PRESSURE);
        int humidity = temperatureObject.getInt(OWM_HUMIDITY);
        Log.d("fromJson: ",pressure+"");
        double max = temperatureObject.getDouble(OWM_MAX);
        double min = temperatureObject.getDouble(OWM_MIN);

        JSONObject windObject = dayForecast.getJSONObject(OWN_WIND);

        double windSpeed = windObject.getDouble(OWM_WINDSPEED);
        double windDirection = windObject.getDouble(OWM_WIND_DIRECTION);

        return new WeatherEntry(weatherId, new Date(dateTimeMillis), max, min,
                humidity, pressure, windSpeed, windDirection);
    }

    @Nullable
    WeatherResponse parse(final String forecastJsonStr) throws JSONException {
        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        // Is there an error?
        if (hasHttpError(forecastJson)) {
            return null;
        }

        WeatherEntry[] weatherForecast = fromJson(forecastJson);

        return new WeatherResponse(weatherForecast);
    }
}
