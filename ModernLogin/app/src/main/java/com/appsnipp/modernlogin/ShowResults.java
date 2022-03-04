package com.appsnipp.modernlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ShowResults extends AppCompatActivity {

    private StringBuilder stringBuilder = new StringBuilder();
    private TextView mShowInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);

        mShowInput = findViewById(R.id.show_output_text_view);

        getBackendData();
    }

    public void getBackendData(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                final StringBuilder contentResult = new StringBuilder();

                String url = "http://192.168.8.101:5000/api/getData";

                try {
                    URL requestURL =new URL(url);
                    URLConnection connection =requestURL.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String contentString ;

                    while ((contentString = br.readLine()) != null){
                        contentResult.append(contentString);
                    }

                    br.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            convertToJson(contentResult.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    public void convertToJson(String response) throws JSONException {

//        JSONObject data = new JSONObject(response);

        JSONArray jsonArray = new JSONArray(response);

        for (int i = 0 ; i < jsonArray.length() ; i ++){
            JSONObject obj = (JSONObject) jsonArray.get(i);

            String timeFrame = obj.getString("TimeFrame");
            String emotion = obj.getString("Emotion");

            stringBuilder.append(timeFrame).append(" : ").append(emotion).append("\n");

        }

        System.out.println(stringBuilder.toString());

        String output = stringBuilder.toString();
        mShowInput.setText(output);
        System.out.println(jsonArray.length());
    }

    public void goBackButton(View view) {

        finish();
    }
}