package com.appiness.finalweather.utils;

import android.content.Context;

import com.appiness.finalweather.AppExecutors;
import com.appiness.finalweather.data.database.WeatherDatabase;
import com.appiness.finalweather.data.networks.WeatherNetworkDataSource;
import com.appiness.finalweather.data.WeatherRepository;
import com.appiness.finalweather.ui.details.DetailViewModelFactory;
import com.appiness.finalweather.ui.main.MainViewModelFactory;

import java.util.Date;

/**
 * Created by appiness on 24/3/18.
 */

public class InjectorUtils {

    public static WeatherRepository provideRepository(Context context) {
        WeatherDatabase database = WeatherDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        WeatherNetworkDataSource networkDataSource =
                WeatherNetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return WeatherRepository.getInstance(database.weatherDao(), networkDataSource, executors);
    }

    public static WeatherNetworkDataSource provideNetworkDataSource(Context context) {
        provideRepository(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return WeatherNetworkDataSource.getInstance(context.getApplicationContext(), executors);
    }

    public static DetailViewModelFactory provideDetailViewModelFactory(Context context, Date date) {
        WeatherRepository repository = provideRepository(context.getApplicationContext());
        return new DetailViewModelFactory(repository, date);
    }

    public static MainViewModelFactory provideMainActivityViewModelFactory(Context context) {
        WeatherRepository repository = provideRepository(context.getApplicationContext());
        return new MainViewModelFactory(repository);
    }

}
