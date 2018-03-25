package com.appiness.finalweather.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.appiness.finalweather.data.database.ListWeatherEntry;
import com.appiness.finalweather.data.WeatherRepository;

import java.util.List;

/**
 * Created by appiness on 24/3/18.
 */

public class MainActivityViewModel extends ViewModel {
    private final WeatherRepository mRepository;
    private final LiveData<List<ListWeatherEntry>> mForecast;

    public MainActivityViewModel(WeatherRepository repository) {
        mRepository = repository;
        mForecast = mRepository.getCurrentWeatherForecasts();
    }

    public LiveData<List<ListWeatherEntry>> getForecast() {
        return mForecast;
    }
}
