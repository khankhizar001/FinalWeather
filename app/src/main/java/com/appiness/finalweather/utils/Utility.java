package com.appiness.finalweather.utils;

/**
 * Created by appiness on 24/3/18.
 */

public class Utility {
    public static double convertFahrenheitToCelcius(double degreesInFahrenheit) {
        return (degreesInFahrenheit - 32) * 5/9;
    }

    public static double convertCelciusToFahrenheit(double degreesInCelcius) {
        return degreesInCelcius * 9/5 + 32;
    }
}
