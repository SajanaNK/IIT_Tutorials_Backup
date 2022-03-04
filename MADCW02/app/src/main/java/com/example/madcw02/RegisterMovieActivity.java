package com.example.madcw02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.madcw02.Constant.FAVOURITE;
import static com.example.madcw02.Constant.MOVIE_ACTORS_NAMES;
import static com.example.madcw02.Constant.MOVIE_DIRECTOR_NAME;
import static com.example.madcw02.Constant.MOVIE_RATING;
import static com.example.madcw02.Constant.MOVIE_REVIEW;
import static com.example.madcw02.Constant.MOVIE_TITLE;
import static com.example.madcw02.Constant.MOVIE_YEAR;
import static com.example.madcw02.Constant.TABLE_NAME;

public class RegisterMovieActivity extends AppCompatActivity {

    private EditText mMovieTitleEditText;
    private EditText mMovieDirectorEditText;
    private EditText mMovieYearEditText;
    private EditText mMovieActorsNamesEditText;
    private EditText mMovieRatingEditText;
    private EditText mMovieReviewEditText;

    private String movieTitle;
    private String movieDirector;
    private int movieYear;
    private String movieActorNames;
    private int movieRating;
    private String movieReview;

    private MoviesData moviesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

        // Initializing EditTexts
        mMovieTitleEditText = findViewById(R.id.title_of_movie_edit_text_rm);
        mMovieDirectorEditText = findViewById(R.id.director_name_edit_text_rm);
        mMovieYearEditText = findViewById(R.id.year_of_movie_edit_text_rm);
        mMovieActorsNamesEditText = findViewById(R.id.actors_names_edit_text_rm);
        mMovieRatingEditText = findViewById(R.id.rate_movie__edite_text_rm);
        mMovieReviewEditText = findViewById(R.id.review_movie_edit_text_rm);

        // Movies Data Object for DB
        moviesData =new MoviesData(this);

    }

    public void getDetails(){

        System.out.println("STRING : " + mMovieTitleEditText.getText().toString());
        // Get texts from Edit texts
        if (mMovieTitleEditText.getText().toString().equals("") || mMovieDirectorEditText.getText().toString().equals("")
                || mMovieYearEditText.getText().toString().equals("") || mMovieActorsNamesEditText.getText().toString().equals("")
                || mMovieRatingEditText.getText().toString().equals("")|| mMovieReviewEditText.getText().toString().equals("")){

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Please, Fill the Details");
            alert.setCancelable(false);

            alert.setPositiveButton(
                    "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.out.println("Alert Closed");
                        }
                    }
            );

            AlertDialog dialog = alert.create();
            dialog.show();

        }

        else {
            movieTitle = mMovieTitleEditText.getText().toString();
            movieDirector = mMovieDirectorEditText.getText().toString();
            movieYear = Integer.parseInt(mMovieYearEditText.getText().toString());
            movieActorNames = mMovieActorsNamesEditText.getText().toString();
            movieRating = Integer.parseInt(mMovieRatingEditText.getText().toString());
            movieReview = mMovieReviewEditText.getText().toString();
        }


    }

    public void addMovie(String title,String dir,int year, String actors, int rating,String review){

        // Add movie to sql
        SQLiteDatabase db = moviesData.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put(MOVIE_TITLE,title);
        values.put(MOVIE_YEAR,year);
        values.put(MOVIE_DIRECTOR_NAME,dir);
        values.put(MOVIE_ACTORS_NAMES,actors);
        values.put(MOVIE_RATING,rating);
        values.put(MOVIE_REVIEW,review);
        values.put(FAVOURITE,"Not Favourite");
        System.out.println("title : " + title + "  year : " + year);
        db.insert(TABLE_NAME,null,values);

    }


    public void saveMovieDetails(View view) {

        getDetails();

        if (movieTitle == null || movieDirector == null || movieActorNames == null || movieReview == null || mMovieRatingEditText.getText().toString() ==null || mMovieYearEditText.getText().toString() == null){

            // Checking the Edit Text boxed
            Toast toast = Toast.makeText(getApplicationContext(),"Please fill the remaining details",Toast.LENGTH_LONG);
            toast.show();
        }

        else if (movieRating < 1 || movieRating > 10){
            // Checking Rating
            Toast toast = Toast.makeText(getApplicationContext(),"Movie Rating must be in 1 - 10",Toast.LENGTH_LONG);
            toast.show();
        }

        else if (movieYear < 1985){
            //Checking Year
            Toast toast = Toast.makeText(getApplicationContext(),"Movie year must be 1985 or later!",Toast.LENGTH_LONG);
            toast.show();
        }

        else{
            addMovie(movieTitle,movieDirector,movieYear,movieActorNames,movieRating,movieReview);
            System.out.println("Registered");
            finish();
        }


    }

    public void cancelOnClick(View view) {
        finish();
    }
}

