package com.example.weatherapplication;

import android.provider.BaseColumns;

public interface Constant extends BaseColumns {

    public static final String TABLE_NAME = "Weather";

    public static final String DATE = "DateSelected";
    public static final String TIME = "TimeSelected";
    public static final String CITY_NAME__SHOWN = "CityNameShown";
    public static final String TEMPERATURE_SHOWN = "TemperatureShown";
    public static final String FEELS_LIKE_TEMPERATURE_SHOWN = "FeelsLikeTemperatureShown";
    public static final String WEATHER_DESCRIPTION_SHOWN = "WeatherDescriptionShown";
    public static final String SUNRISE_SHOWN = "SunRiseShown";
    public static final String SUNSET_SHOWN = "SunSetShown";
}
