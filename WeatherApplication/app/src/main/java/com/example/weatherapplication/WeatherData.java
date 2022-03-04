package com.example.weatherapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.weatherapplication.Constant.CITY_NAME__SHOWN;
import static com.example.weatherapplication.Constant.DATE;
import static com.example.weatherapplication.Constant.FEELS_LIKE_TEMPERATURE_SHOWN;
import static com.example.weatherapplication.Constant.SUNRISE_SHOWN;
import static com.example.weatherapplication.Constant.SUNSET_SHOWN;
import static com.example.weatherapplication.Constant.TABLE_NAME;
import static com.example.weatherapplication.Constant.TEMPERATURE_SHOWN;
import static com.example.weatherapplication.Constant.TIME;
import static com.example.weatherapplication.Constant.WEATHER_DESCRIPTION_SHOWN;

public class WeatherData extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="Weather.db";
    public static final int DATABASE_VERSION = 1;

    public WeatherData(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
            "CREATE TABLE " + TABLE_NAME +" (" +
                    DATE + " TEXT," +
                    TIME + " TEXT," +
                    CITY_NAME__SHOWN + " TEXT," +
                    TEMPERATURE_SHOWN + " REAL," +
                    FEELS_LIKE_TEMPERATURE_SHOWN + " REAL," +
                    WEATHER_DESCRIPTION_SHOWN + " TEXT," +
                    SUNRISE_SHOWN + " TEXT," +
                    SUNSET_SHOWN + " TEXT);"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
