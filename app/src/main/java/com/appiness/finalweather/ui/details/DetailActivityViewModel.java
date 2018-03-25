package com.appiness.finalweather.ui.details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.appiness.finalweather.data.database.WeatherEntry;
import com.appiness.finalweather.data.WeatherRepository;

import java.util.Date;

/**
 * Created by appiness on 24/3/18.
 */

class DetailActivityViewModel extends ViewModel {

    private final LiveData<WeatherEntry> mWeather;

    private final Date mDate;
    private final WeatherRepository mRepository;

    public DetailActivityViewModel(WeatherRepository repository, Date date) {
        mRepository = repository;
        mDate = date;
        mWeather = mRepository.getWeatherByDate(mDate);
    }

    public LiveData<WeatherEntry> getWeather() {
        return mWeather;
    }
}