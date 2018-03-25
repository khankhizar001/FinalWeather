package com.appiness.finalweather.data.networks;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.appiness.finalweather.utils.InjectorUtils;

/**
 * Created by appiness on 24/3/18.
 */

public class SyncIntentService extends IntentService{
    private static final String LOG_TAG = SyncIntentService.class.getSimpleName();

    public SyncIntentService() {
        super("SyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "Intent service started");
        WeatherNetworkDataSource networkDataSource =
                InjectorUtils.provideNetworkDataSource(this.getApplicationContext());
        networkDataSource.fetchWeather();
    }
}
