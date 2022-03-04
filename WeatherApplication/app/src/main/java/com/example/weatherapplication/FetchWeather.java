package com.example.weatherapplication;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchWeather extends AsyncTask<String, Void, String> {

    private TextView mFeelsLikeTemperatureTextView;
    private TextView mShowTempTextView;
    private EditText mCityNameEditText;
    private TextView mShowSunset;
    private TextView mShowSunRise;
    private TextView mShowDescription;

    private String temperatureString;
    private String feelsLike;
    private String wheatherDescription;
    private String cityName;
    private String sunrise;
    private String sunset;

    public FetchWeather(TextView feelsLikeTemp, TextView tempText, EditText cityNameText,TextView description,TextView sunset,TextView sunrise) {

        this.mFeelsLikeTemperatureTextView = feelsLikeTemp;
        this.mShowTempTextView = tempText;
        this.mCityNameEditText = cityNameText;
        this.mShowSunset = sunset;
        this.mShowSunRise = sunrise;
        this.mShowDescription = description;
    }

    @Override
    protected String doInBackground(String... params) {

        cityName = mCityNameEditText.getText().toString();

        String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric&appid=37f0f0bc39d912c6e09e0a6050815171";

        HttpURLConnection urlConnection = null;
        BufferedReader br = null;
        String dataJSONString = null;

        try {
            URL requestUri = new URL(BASE_URL);
            urlConnection = (HttpURLConnection) requestUri.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream is = urlConnection.getInputStream();

            StringBuilder builder = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(is));

            String line;

            while ((line = br.readLine()) != null) {
                builder.append(line);
            }

            if (builder.length() == 0) {
                return null;
            }

            dataJSONString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(dataJSONString);
        return dataJSONString;
    }

    @Override
    protected void onPostExecute(String s) {

        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);

            JSONObject main = jsonObject.getJSONObject("main");
            temperatureString = main.getString("temp");
            feelsLike = main.getString("feels_like");

            JSONArray ItemArray = jsonObject.getJSONArray("weather");
            JSONObject descJson = ItemArray.getJSONObject(0);
            wheatherDescription = descJson.getString("description");

            JSONObject sysJson = jsonObject.getJSONObject("sys");
            sunrise = sysJson.getString("sunrise");
            sunset = sysJson.getString("sunset");


            if (temperatureString != null && wheatherDescription != null ){
                mShowTempTextView.setText(temperatureString);
                mFeelsLikeTemperatureTextView.setText(feelsLike);
                mShowDescription.setText(wheatherDescription);
                mShowSunset.setText(sunset);
                mShowSunRise.setText(sunrise);
            }
            else {
                mShowTempTextView.setText("NOT FOUND");
                mFeelsLikeTemperatureTextView.setText("NOT FOUND");
                mShowDescription.setText("NOT FOUND");
                mShowSunset.setText("NOT FOUND");
                mShowSunRise.setText("NOT FOUND");

            }

        } catch (JSONException e) {
            mShowTempTextView.setText("NOT FOUND");
            mFeelsLikeTemperatureTextView.setText("NOT FOUND");
            mShowDescription.setText("NOT FOUND");
            mShowSunset.setText("NOT FOUND");
            mShowSunRise.setText("NOT FOUND");
            e.printStackTrace();
        }

    }
}
