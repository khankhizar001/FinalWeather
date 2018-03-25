package com.appiness.finalweather.ui.details;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.appiness.finalweather.data.WeatherRepository;
import com.appiness.finalweather.ui.details.DetailActivityViewModel;

import java.util.Date;

/**
 * Created by appiness on 24/3/18.
 */

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final WeatherRepository mRepository;
    private final Date mDate;

    public DetailViewModelFactory(WeatherRepository repository, Date date) {
        this.mRepository = repository;
        this.mDate = date;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new DetailActivityViewModel(mRepository, mDate);
    }
}
