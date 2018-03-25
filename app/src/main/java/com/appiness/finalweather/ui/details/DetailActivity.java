package com.appiness.finalweather.ui.details;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.appiness.finalweather.R;
import com.appiness.finalweather.data.database.WeatherEntry;
import com.appiness.finalweather.databinding.ActivityDetailBinding;
import com.appiness.finalweather.utils.DateUtils;
import com.appiness.finalweather.utils.InjectorUtils;
import com.appiness.finalweather.utils.WeatherUtils;

import java.util.Date;

/**
 * Created by appiness on 24/3/18.
 */

public class DetailActivity extends LifecycleActivity {

    public static final String WEATHER_ID_EXTRA = "WEATHER_ID_EXTRA";

    private ActivityDetailBinding mDetailBinding;
    private DetailActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        long timestamp = getIntent().getLongExtra(WEATHER_ID_EXTRA, -1);
        Date date = new Date(timestamp);

        DetailViewModelFactory factory = InjectorUtils.provideDetailViewModelFactory(this.getApplicationContext(), date);
        mViewModel = ViewModelProviders.of(this, factory).get(DetailActivityViewModel.class);

        mViewModel.getWeather().observe(this, weatherEntry -> {
            if (weatherEntry != null) bindWeatherToUI(weatherEntry);
        });

    }

    private void bindWeatherToUI(WeatherEntry weatherEntry) {

        int weatherId = weatherEntry.getWeatherIconId();
        int weatherImageId = WeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId);

        /* Set the resource ID on the icon to display the art */
        mDetailBinding.primaryInfo.weatherIcon.setImageResource(weatherImageId);

        long localDateMidnightGmt = weatherEntry.getDate().getTime();
        String dateText = DateUtils.getFriendlyDateString(DetailActivity.this, localDateMidnightGmt, true);
        mDetailBinding.primaryInfo.date.setText(dateText);

        String description = WeatherUtils.getStringForWeatherCondition(DetailActivity.this, weatherId);

        String descriptionA11y = getString(R.string.a11y_forecast, description);

        mDetailBinding.primaryInfo.weatherDescription.setText(description);
        mDetailBinding.primaryInfo.weatherDescription.setContentDescription(descriptionA11y);

        mDetailBinding.primaryInfo.weatherIcon.setContentDescription(descriptionA11y);


        double maxInCelsius = weatherEntry.getMax();

        String highString = WeatherUtils.formatTemperature(DetailActivity.this, maxInCelsius);

        String highA11y = getString(R.string.a11y_high_temp, highString);

        mDetailBinding.primaryInfo.highTemperature.setText(highString);
        mDetailBinding.primaryInfo.highTemperature.setContentDescription(highA11y);


        double minInCelsius = weatherEntry.getMin();

        String lowString = WeatherUtils.formatTemperature(DetailActivity.this, minInCelsius);

        String lowA11y = getString(R.string.a11y_low_temp, lowString);

        /* Set the text and content description (for accessibility purposes) */
        mDetailBinding.primaryInfo.lowTemperature.setText(lowString);
        mDetailBinding.primaryInfo.lowTemperature.setContentDescription(lowA11y);


        double humidity = weatherEntry.getHumidity();
        String humidityString = getString(R.string.format_humidity, humidity);
        String humidityA11y = getString(R.string.a11y_humidity, humidityString);

        mDetailBinding.extraDetails.humidity.setText(humidityString);
        mDetailBinding.extraDetails.humidity.setContentDescription(humidityA11y);

        mDetailBinding.extraDetails.humidityLabel.setContentDescription(humidityA11y);

        double windSpeed = weatherEntry.getWind();
        double windDirection = weatherEntry.getDegrees();
        String windString = WeatherUtils.getFormattedWind(DetailActivity.this, windSpeed, windDirection);
        String windA11y = getString(R.string.a11y_wind, windString);

        mDetailBinding.extraDetails.windMeasurement.setText(windString);
        mDetailBinding.extraDetails.windMeasurement.setContentDescription(windA11y);
        mDetailBinding.extraDetails.windLabel.setContentDescription(windA11y);

        double pressure = weatherEntry.getPressure();

        String pressureString = getString(R.string.format_pressure, pressure);

        String pressureA11y = getString(R.string.a11y_pressure, pressureString);

        mDetailBinding.extraDetails.pressure.setText(pressureString);
        mDetailBinding.extraDetails.pressure.setContentDescription(pressureA11y);
        mDetailBinding.extraDetails.pressureLabel.setContentDescription(pressureA11y);
    }
}
