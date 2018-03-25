package com.appiness.finalweather.data.networks;

import android.util.Log;

import com.appiness.finalweather.utils.InjectorUtils;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;

/**
 * Created by appiness on 24/3/18.
 */

public class FirebaseJobService extends JobService{

    private static final String LOG_TAG = FirebaseJobService.class.getSimpleName();


    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.d(LOG_TAG, "Job service started");

        WeatherNetworkDataSource networkDataSource =
                InjectorUtils.provideNetworkDataSource(this.getApplicationContext());
        networkDataSource.fetchWeather();

        jobFinished(jobParameters, false);

        return true;
    }


    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
