package com.appiness.finalweather.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by appiness on 24/3/18.
 */

public class Weather implements Parcelable {
    long dateInMillis;
    double tempMin;
    double tempMax;
    //double temp;
    double humidity;
    double pressure;
    double wind;

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    String weatherDescription;
    String iconCode;

    public Calendar getDate() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(dateInMillis);
        return date;
    }

    public void setDateInMillis(long dateInMillis) {
        this.dateInMillis = dateInMillis;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public Weather(){}

    Weather(Parcel in) {
           this.dateInMillis = in.readLong();
           this.tempMax = in.readDouble();
           this.tempMin = in.readDouble();
           this.humidity = in.readDouble();
           this.pressure = in.readDouble();
           this.wind = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(dateInMillis);
        dest.writeDouble(tempMax);
        dest.writeDouble(tempMin);
        dest.writeString(weatherDescription);
        dest.writeString(iconCode);
        dest.writeDouble(humidity);
        dest.writeDouble(pressure);
        dest.writeDouble(wind);
        //dest.writeString(windDirection);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
}
