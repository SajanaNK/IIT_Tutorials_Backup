package com.example.madcw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Migrate in to Register Movie
    public void openRegisterMovie(View view) {
        Intent intent = new Intent(this,RegisterMovieActivity.class);
        startActivity(intent);
    }

    // Migrate in to Display Movies
    public void displayMovies(View view) {
        Intent intent = new Intent(this,DisplayMovies.class);
        startActivity(intent);
    }

    // Migrate in to Edit Movie
    public void showEditMovies(View view) {
        Intent intent = new Intent(this,EditMoviesActivity.class);
        startActivity(intent);
    }

    // Migrate in to Search Movie
    public void searchMovie(View view) {
        Intent intent = new Intent(this,SearchActivity.class);
        startActivity(intent);
    }

    // Migrate in to Ratings of Movie
    public void showRatings(View view) {
        Intent intent = new Intent(this,ShowRatingsActivity.class);
        startActivity(intent);
    }

    // Migrate in to Favourite Movies
    public void showFavouriteMovies(View view) {
        Intent intent = new Intent(this,DisplayFavouritesActivity.class);
        startActivity(intent);
    }



}