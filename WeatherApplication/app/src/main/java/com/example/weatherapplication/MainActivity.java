package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.weatherapplication.Constant.CITY_NAME__SHOWN;
import static com.example.weatherapplication.Constant.DATE;
import static com.example.weatherapplication.Constant.FEELS_LIKE_TEMPERATURE_SHOWN;
import static com.example.weatherapplication.Constant.SUNRISE_SHOWN;
import static com.example.weatherapplication.Constant.SUNSET_SHOWN;
import static com.example.weatherapplication.Constant.TABLE_NAME;
import static com.example.weatherapplication.Constant.TEMPERATURE_SHOWN;
import static com.example.weatherapplication.Constant.TIME;
import static com.example.weatherapplication.Constant.WEATHER_DESCRIPTION_SHOWN;


public class MainActivity extends AppCompatActivity {

    private TextView mFeelsLikeTemperatureTextView;
    private TextView mShowTempTextView;
    private TextView mShowDescription;
    private TextView mShowSunset;
    private TextView mShowSunRise;
    private EditText mCityNameEditText;
    private TextView mDatabaseView;
    private EditText mSearchEditText;

    private WeatherData weather;

    private String date;
    private String time;
    private String searchCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShowTempTextView = findViewById(R.id.current_temperature_textview);
        mFeelsLikeTemperatureTextView = findViewById(R.id.current_feels_like_temperature_textview);
        mShowDescription = findViewById(R.id.description_text_view);
        mShowSunRise = findViewById(R.id.sunrise_text_view);
        mShowSunset = findViewById(R.id.sunset_text_view);
        mCityNameEditText = findViewById(R.id.city_name_edit_text);
        mDatabaseView = findViewById(R.id.database_text_view);
        mSearchEditText = findViewById(R.id.search_city_edit_text);

        weather = new WeatherData(this);

        try {
            Cursor cursor = getEvents();
            showEvent(cursor);
        } finally {
            weather.close();
        }

    }

    public void showWeather(View view) {

        if (mCityNameEditText.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "ENTER THE CITY NAME!", Toast.LENGTH_LONG);
            toast.show();
        } else {
            date = String.valueOf(java.time.LocalDate.now());
            time = String.valueOf(java.time.LocalTime.now());
            System.out.println("TIME : " + time + "   DATE : " + date);

            FetchWeather fetchWeather = (FetchWeather) new FetchWeather(mFeelsLikeTemperatureTextView, mShowTempTextView, mCityNameEditText, mShowDescription, mShowSunset, mShowSunRise) {
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    addWeatherData();
                }
            }.execute();

        }



    }

    public void addWeatherData() {

        SQLiteDatabase db = weather.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DATE, date);
        values.put(TIME, time);
        values.put(CITY_NAME__SHOWN, mCityNameEditText.getText().toString());
        values.put(TEMPERATURE_SHOWN, Float.valueOf(mShowTempTextView.getText().toString()));
        values.put(FEELS_LIKE_TEMPERATURE_SHOWN, Float.valueOf(String.valueOf(mFeelsLikeTemperatureTextView.getText())));
        values.put(WEATHER_DESCRIPTION_SHOWN, mShowDescription.getText().toString());
        values.put(SUNRISE_SHOWN, mShowSunRise.getText().toString());
        values.put(SUNSET_SHOWN, mShowSunset.getText().toString());

        db.insertOrThrow(TABLE_NAME, null, values);

        try {
            Cursor cursor = getEvents();
            showEvent(cursor);
        } finally {
            weather.close();
        }
    }

    private Cursor getEvents() {

        SQLiteDatabase db = weather.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{DATE, TIME, CITY_NAME__SHOWN, TEMPERATURE_SHOWN, FEELS_LIKE_TEMPERATURE_SHOWN, WEATHER_DESCRIPTION_SHOWN, SUNRISE_SHOWN, SUNSET_SHOWN}, null, null, null, null, null);
        return cursor;
    }

    private void showEvent(Cursor cursor) {

        StringBuilder builder = new StringBuilder("Saved Weather Data :" + "\n \n");

        if (searchCity == null) {

            while (cursor.moveToNext()) {

                String dateRead = cursor.getString(0);
                String timeRead = cursor.getString(1);
                String cityRead = cursor.getString(2);
                int tempRead = cursor.getInt(3);
                int feelsLikeTempRead = cursor.getInt(4);
                String descRead = cursor.getString(5);
                String sunriseRead = cursor.getString(6);
                String sunsetRead = cursor.getString(7);

                builder.append(dateRead).append("  :").append(timeRead).append("  :").append(cityRead).append("  :").append(tempRead).append("  :").append(feelsLikeTempRead).append("  :").append(descRead).append("  :").append(sunriseRead).append("  :").append(sunsetRead).append("\n \n");

            }

            cursor.close();
            System.out.println("BUILDER  = " + builder.toString());

            mDatabaseView.setText(builder.toString());

        }

        else {


            while (cursor.moveToNext()) {

                String dateRead = cursor.getString(0);
                String timeRead = cursor.getString(1);
                String cityRead = cursor.getString(2);
                int tempRead = cursor.getInt(3);
                int feelsLikeTempRead = cursor.getInt(4);
                String descRead = cursor.getString(5);
                String sunriseRead = cursor.getString(6);
                String sunsetRead = cursor.getString(7);

                if (cityRead.equals(searchCity)) {

                    builder.append(dateRead).append("  :").append(timeRead).append("  :").append(cityRead).append("  :").append(tempRead).append("  :").append(feelsLikeTempRead).append("  :").append(descRead).append("  :").append(sunriseRead).append("  :").append(sunsetRead).append("\n \n");
                }
            }

            cursor.close();
            System.out.println("BUILDER  = " + builder.toString());

            mDatabaseView.setText(builder.toString());

        }


    }

    public void showHistory(View view) {

        searchCity = mSearchEditText.getText().toString();

        try {
            Cursor cursor = getEvents();
            showEvent(cursor);
        } finally {
            weather.close();
        }

    }
}