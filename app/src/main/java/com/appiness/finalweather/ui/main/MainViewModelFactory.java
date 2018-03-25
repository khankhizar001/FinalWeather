package com.appiness.finalweather.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.appiness.finalweather.data.WeatherRepository;
import com.appiness.finalweather.ui.main.MainActivityViewModel;

/**
 * Created by appiness on 24/3/18.
 */

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final WeatherRepository mRepository;

    public MainViewModelFactory(WeatherRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainActivityViewModel(mRepository);
    }
}
