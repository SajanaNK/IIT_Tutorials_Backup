package com.example.madcw02;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.example.madcw02.Constant.FAVOURITE;
import static com.example.madcw02.Constant.MOVIE_ACTORS_NAMES;
import static com.example.madcw02.Constant.MOVIE_DIRECTOR_NAME;
import static com.example.madcw02.Constant.MOVIE_RATING;
import static com.example.madcw02.Constant.MOVIE_REVIEW;
import static com.example.madcw02.Constant.MOVIE_TITLE;
import static com.example.madcw02.Constant.MOVIE_YEAR;
import static com.example.madcw02.Constant.TABLE_NAME;

public class ShowRatingsActivity extends AppCompatActivity {

    private static String[] FROM ={MOVIE_TITLE , MOVIE_YEAR,MOVIE_DIRECTOR_NAME,MOVIE_ACTORS_NAMES,MOVIE_RATING,MOVIE_REVIEW,FAVOURITE};
    private MoviesData moviesData;

    private String[] movieTitleArray;
    ArrayList<String> temp = new ArrayList();

    private LinearLayout ll ;

    private String selectedMovie = "";
    private String id;
    private String imageURL;

    private ImageView imageView;
    Bitmap bitmap;
    private TextView mShowTotalRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ratings);

        imageView = findViewById(R.id.movie_image_show_sr);
        mShowTotalRating = findViewById(R.id.show_current_rating_sr);

        moviesData = new MoviesData(this);

        // Reading from Database
        try {
            Cursor cursor = getMoviesFromDB();
            showEvent(cursor);

        }
        finally {
            moviesData.close();
        }

        movieTitleArray = new String[temp.size()];


        for (int i = 0 ;i < movieTitleArray.length;i++){
            movieTitleArray[i] = temp.get(i);
        }

        ll = findViewById(R.id.radio_buttons_inside_ll);
        createRadioButtons();

    }
    public Cursor getMoviesFromDB(){

        SQLiteDatabase db =moviesData.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,FROM,null,null,null,null,null);

        return cursor;
    }

    public void showEvent(Cursor cursor){

        String title ;

        while (cursor.moveToNext()){

            title = cursor.getString(0);
            temp.add(title);
        }

        cursor.close();


    }

    public void createRadioButtons(){

        // Create RadioButtons
        final RadioButton[] rb = new RadioButton[movieTitleArray.length];
        RadioGroup rg = new RadioGroup(this);

        rg.setOrientation(RadioGroup.VERTICAL);

        for (int i = 0; i < movieTitleArray.length;i++){
            rb[i] = new RadioButton(this);
            rb[i].setText(movieTitleArray[i]);
            rb[i].setId(i+1000);
            rb[i].setTextSize(Float.parseFloat(String.valueOf(25.0)));
            rg.addView(rb[i]);

            System.out.println("Adding : " + movieTitleArray[i]);
        }

        ll.addView(rg);

        //Radio button on click
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton= findViewById(rg.getCheckedRadioButtonId());
                selectedMovie = (String) radioButton.getText();
                System.out.println("Clicked : " + radioButton.getText());

            }
        });

    }


    public void showRatingsFromAPI(View view) {
        getAPIData();
    }


    public void getAPIData(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                StringBuilder contentResult = new StringBuilder();

                // Link to get the ID of API
                String url = "https://imdb-api.com/en/API/SearchTitle/k_564iqvml/" + selectedMovie;

                try {
                    URL requestURL =new URL(url);
                    URLConnection connection =requestURL.openConnection();

                    // To read the data
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String contentAsString;

                    while ((contentAsString  =  br.readLine()) != null){
                        contentResult.append(contentAsString);
                    }
                    br.close();
                    System.out.println("[ID] : Took URLL DATA");


                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            convertToString(contentResult.toString());
                            getRatingData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        thread.start();

    }

    public void convertToString(String result) throws JSONException {
        //Getting the ID
        System.out.println("[ID] : Took Results");

        JSONObject data = new JSONObject(result);

        JSONArray resultsJsonArray = data.getJSONArray("results");
        JSONObject getIDObject = resultsJsonArray.getJSONObject(0);

        id = getIDObject.getString("id");
        imageURL = getIDObject.getString("image");
        System.out.println("Image URL : "+imageURL);

    }

    public void getRatingData(){

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Get Image Daata");

                StringBuilder ratingData = new StringBuilder();
                StringBuilder ratingDataSec = new StringBuilder();

                // URL s for getting IMDB total Rating
                String secondUrl =  "https://imdb-api.com/en/API/UserRatings/k_564iqvml/"+id;
                String thirdUrl = "https://imdb-api.com/en/API/Ratings/k_564iqvml/"+id;

                try {
                    URL secondReqURL = new URL(secondUrl);
                    URLConnection secondConnection = secondReqURL.openConnection();
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(secondConnection.getInputStream()));

                    String content ;
                    while ((content = br2.readLine()) != null){
                        ratingData.append(content);
                    }
                    br2.close();



                    URL thirdReq = new URL(thirdUrl);
                    URLConnection thirdConnection = thirdReq.openConnection();
                    BufferedReader br3 = new BufferedReader(new InputStreamReader(thirdConnection.getInputStream()));

                    String contentOther ;
                    while ((contentOther = br3.readLine()) != null){
                        ratingDataSec.append(contentOther);
                    }
                    br3.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            convertRatingToString(ratingData.toString(),ratingDataSec.toString());
                            getImage();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
        t2.start();

    }

    public void convertRatingToString(String result,String result2) throws JSONException {

        // Read JSON Data
        JSONObject jsonObject = new JSONObject(result);

        //JSONObject data = jsonObject.getJSONObject("totalRating");
        String totalRating  = jsonObject.getString("totalRating");

        JSONObject jsonObject1 = new JSONObject(result2);
        String totalRating2 = jsonObject1.getString("imDb");

        if (totalRating == null || totalRating.equals("")){
            mShowTotalRating.setText(totalRating2);
        }
        else {
            mShowTotalRating.setText(totalRating);
        }

    }

    public void getImage(){

        // Get iMage
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL getImageURL = new URL(imageURL);

                    URLConnection connectionImage = getImageURL.openConnection();

                    bitmap = BitmapFactory.decodeStream(connectionImage.getInputStream());

                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        imageView.setImageBitmap(bitmap);
                    }
                });

            }
        });
        t3.start();

    }

}