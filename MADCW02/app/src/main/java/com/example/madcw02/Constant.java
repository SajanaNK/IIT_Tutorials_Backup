package com.example.madcw02;

import android.provider.BaseColumns;

public interface Constant extends BaseColumns {

    public static final String TABLE_NAME = "Movies";

    public static final String MOVIE_TITLE = "MovieTitle";
    public static final String MOVIE_YEAR = "MovieYear";
    public static final String MOVIE_DIRECTOR_NAME = "MovieDirector";
    public static final String MOVIE_ACTORS_NAMES = "MovieActors";
    public static final String MOVIE_RATING = "MovieRating";
    public static final String MOVIE_REVIEW = "MovieReview";
    public static final String FAVOURITE = "IsFavourite";

}
