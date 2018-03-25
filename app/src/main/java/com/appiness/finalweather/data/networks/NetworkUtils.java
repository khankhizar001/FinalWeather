package com.appiness.finalweather.data.networks;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by appiness on 24/3/18.
 */

final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String FORECAST_BASE_URL =
            "http://api.openweathermap.org/data/2.5/forecast";


    /* The query parameter allows us to provide a location string to the API */
    private static final String QUERY_PARAM = "q";

    private final static String OWN_PARAM_MODE = "mode";
    private final static String OWN_PARAM_UNITS = "units";
    private final static String OWN_PARAM_DAYCOUNT = "cnt";
    private final static String OWN_PARAM_APIKEY = "APPID";
    private final static String OWN_APIKEY = "b587dd9d0a06eb6377452fa06613df80";
    private static String dataMode = "json";
    private static String dataUnits = "imperial";
    private static String dataDays = "3";


    static URL getUrl() {
        String locationQuery = "Lyon,FR";
        return buildUrlWithLocationQuery(locationQuery);
    }


    private static URL buildUrlWithLocationQuery(String locationQuery) {
        Uri weatherQueryUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, locationQuery)
                .appendQueryParameter(OWN_PARAM_MODE, dataMode)
                .appendQueryParameter(OWN_PARAM_UNITS, dataUnits)
                .appendQueryParameter(OWN_PARAM_DAYCOUNT, dataDays)
                .appendQueryParameter(OWN_PARAM_APIKEY, OWN_APIKEY)
                .build();

        try {
            URL weatherQueryUrl = new URL(weatherQueryUri.toString());
            Log.v(TAG, "URL: " + weatherQueryUrl);
            return weatherQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            Log.d("response :", response);
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }
}

