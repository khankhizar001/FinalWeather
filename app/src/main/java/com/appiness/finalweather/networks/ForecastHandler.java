package com.appiness.finalweather.networks;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.appiness.finalweather.R;
import com.appiness.finalweather.model.Weather;
import com.appiness.finalweather.utils.DataParser;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by appiness on 24/3/18.
 */

public class ForecastHandler extends AsyncTask<Location,Void,ArrayList<Weather>> {
    private Context context;
    ArrayList<Weather> forecast;
    private final static String OWM_BASEURL = "api.openweathermap.org";
    private final static String OWM_VERSION = "2.5";
    private final static String OWM_DATA = "data";
    private final static String OWM_FORECAST = "forecast";
    private final static String OWM_DAILY = "daily";
    private final static String OWM_PARAM_LATITUDE = "lat";
    private final static String OWM_PARAM_LONGITUDE = "log";
    private final static String OWM_PARAM_MODE = "mode";
    private final static String OWM_PARAM_UNITS = "units";
    private final static String OWM_PARAM_DAYCOUNT = "cnt";
    private final static String OWM_PARAM_APIKEY = "APPID";
    private final static String OWM_APIKEY = "b587dd9d0a06eb6377452fa06613df80";
    private String dataProtocol = "http";
    private String dataMode = "json";
    private String dataUnits = "imperial";
    private String dataDays = "2";
    private HttpURLConnection urlConnection = null;
    private BufferedReader reader = null;
    private double lat = 45.7640;
    private double log = 4.8357;


    public ForecastHandler(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Weather> doInBackground(Location... params) {
        forecast = getforecast(params[0]);
        return forecast;
    }

    private ArrayList<Weather> getforecast(Location location) {

        ArrayList<Weather> forecastDays = null;
        String forecastResponse;

        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme(dataProtocol)
                    .authority(OWM_BASEURL)
                    .appendPath(OWM_DATA)
                    .appendPath(OWM_VERSION)
                    .appendPath(OWM_FORECAST)
                    .appendPath(OWM_DAILY)
                    /*.appendQueryParameter(OWM_PARAM_LATITUDE, Double.toString(location.getLatitude()))
                    .appendQueryParameter(OWM_PARAM_LONGITUDE, Double.toString(location.getLongitude()))*/
                    .appendQueryParameter(OWM_PARAM_LATITUDE, Double.toString(lat))
                    .appendQueryParameter(OWM_PARAM_LONGITUDE, Double.toString(log))
                    .appendQueryParameter(OWM_PARAM_MODE, dataMode)
                    .appendQueryParameter(OWM_PARAM_UNITS, dataUnits)
                    .appendQueryParameter(OWM_PARAM_DAYCOUNT, dataDays)
                    .appendQueryParameter(OWM_PARAM_APIKEY, OWM_APIKEY);
            String mUrl = builder.build().toString();
            URL url = new URL (mUrl);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            forecastResponse = buffer.toString();

            try {
                forecastDays = DataParser.getWeatherDataFromJson(forecastResponse);
            } catch (JSONException e) {
                //TODO: handle exception appropriately
            }
        } catch (IOException e) {

            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                   // Log.e(LOG_TAG, context.getString(R.string.error_closing_stream), e);
                }
            }
        }

        return forecastDays;
    }
}
