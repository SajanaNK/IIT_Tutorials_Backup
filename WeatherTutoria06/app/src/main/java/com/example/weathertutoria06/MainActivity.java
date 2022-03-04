package com.example.weathertutoria06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private TextView mShowWeatherTextView;
    private TextView mShowTempTextView;

    final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=London,UK&units=metric&appid=37f0f0bc39d912c6e09e0a6050815171";

    private URL requestURL;
    private HttpURLConnection connection;
    private String contentAsString;
    private StringBuilder contentResult;
    private String temperatureString;
    private String feelsLike;
    private String wheatherDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShowTempTextView = findViewById(R.id.current_temperature_textview);
        mShowWeatherTextView = findViewById(R.id.current_weather_textview);

        contentResult = new StringBuilder();

    }


    public void showWeather(View view) {

        try {
            convertJsonToString(getWeather());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mShowWeatherTextView.setText(wheatherDescription);
        mShowTempTextView.setText(temperatureString);
    }

    public String getWeather(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                try {

                    requestURL = new URL(BASE_URL);
                    URLConnection connection = requestURL.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    while ( (contentAsString = br.readLine()) != null){
                        contentResult.append(contentAsString);
                    }
                    br.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });


        thread.start();

        String returnResult = contentResult.toString();
        System.out.println("RETURN RESULT : " + returnResult);
        return returnResult;
    }

    public void convertJsonToString(String response) throws JSONException {
        System.out.println("RESPONSE IN COVERTING : " + response);
        JSONObject data = new JSONObject(response);

        JSONObject main = data.getJSONObject("main");
        temperatureString = main.getString("temp");
        feelsLike = main.getString("feels_like");

//        JSONObject weather = data.getJSONObject("weather");
//        wheatherDescription = weather.getString("description");

        JSONArray ItemArray = data.getJSONArray("weather");
        JSONObject descJson = ItemArray.getJSONObject(0);
        wheatherDescription = descJson.getString("description");
    }

}