package com.example.madcw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.madcw02.Constant.FAVOURITE;
import static com.example.madcw02.Constant.MOVIE_ACTORS_NAMES;
import static com.example.madcw02.Constant.MOVIE_DIRECTOR_NAME;
import static com.example.madcw02.Constant.MOVIE_RATING;
import static com.example.madcw02.Constant.MOVIE_REVIEW;
import static com.example.madcw02.Constant.MOVIE_TITLE;
import static com.example.madcw02.Constant.MOVIE_YEAR;
import static com.example.madcw02.Constant.TABLE_NAME;

public class ShowEditMovieActivity extends AppCompatActivity {

    private static String[] FROM ={MOVIE_TITLE , MOVIE_YEAR,MOVIE_DIRECTOR_NAME,MOVIE_ACTORS_NAMES,MOVIE_RATING,MOVIE_REVIEW,FAVOURITE};


    String message;

    private MoviesData moviesData;

    private String[] movieTitleArray;
    ArrayList<String> temp = new ArrayList();

    private EditText mTitleEditText;
    private EditText mYearEditText;
    private EditText mDirectorEditText;
    private EditText mActorsNamesEditText;
    private EditText mReviewEditText;
    private EditText mIsFavEditText;
    RadioButton radioButtonFav;
    RadioButton radioButtonNot;
    RadioGroup mRadioGroup;

    private int[] yellowStarImagesArray;
    private int[] greyStarImagesArray;
    private int[] imageViews;

    private int chosenRating;
    private String currentMovieTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_edit_movie);

        Intent intent = getIntent();
        message = intent.getStringExtra(EditMoviesActivity.EXTRA_MESSAGE);

        moviesData = new MoviesData(this);
        SQLiteDatabase db =moviesData.getReadableDatabase();

        // Edit Text Boxes
        mTitleEditText = findViewById(R.id.title_edit_text_sem);
        mYearEditText = findViewById(R.id.year_edit_text_sem);
        mDirectorEditText = findViewById(R.id.director_name_edit_text_sem);
        mActorsNamesEditText = findViewById(R.id.actors_edit_text_sem);
        mReviewEditText = findViewById(R.id.review_edit_text_sem);
        mRadioGroup = findViewById(R.id.show_fav_radio_froup);
        radioButtonFav = findViewById(R.id.is_fav_radio_button);
        radioButtonNot = findViewById(R.id.not_fav_radio_button);

        // Grey Star Images
        greyStarImagesArray = new int[]{R.drawable.g_1, R.drawable.g_2,R.drawable.g_3,R.drawable.g_4,R.drawable.g_5
                ,R.drawable.g_6,R.drawable.g_7,R.drawable.g_8,R.drawable.g_9,R.drawable.g_10};

        //Yellow Star Images
        yellowStarImagesArray = new int[]{R.drawable.y_1, R.drawable.y_2,R.drawable.y_3,R.drawable.y_4,R.drawable.y_5
                ,R.drawable.y_6,R.drawable.y_7,R.drawable.y_8,R.drawable.y_9,R.drawable.y_10};

        //Image Views
        imageViews = new int[]{R.id.star_grey_1,R.id.star_grey_2,R.id.star_grey_3,R.id.star_grey_4,R.id.star_grey_5
                ,R.id.star_grey_6,R.id.star_grey_7,R.id.star_grey_8,R.id.star_grey_9,R.id.star_grey_10};



        try {
            Cursor cursor ;
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + MOVIE_TITLE + " LIKE '" + message + "'" ,null );
            showEvent(cursor);

        }
        finally {
            moviesData.close();
        }


    }

    public Cursor getMoviesFromDB(){

        SQLiteDatabase db =moviesData.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,FROM,null,null,null,null,null);

        return cursor;
    }

    public void showEvent(Cursor cursor){

        while (cursor.moveToNext()){

            String title = cursor.getString(0);
            int year = Integer.parseInt(cursor.getString(1));
            String dir = cursor.getString(2);
            String actorNames = cursor.getString(3);
            int rating = Integer.parseInt(cursor.getString(4));
            String review = cursor.getString(5);
            String isFav = cursor.getString(6);

            if (isFav.equals("Favourite")){

                radioButtonFav.setChecked(true);
            }
            else {

                radioButtonNot.setChecked(true);
            }

            mTitleEditText.setText(title);
            mYearEditText.setText(String.valueOf(year));
            mDirectorEditText.setText(dir);
            mActorsNamesEditText.setText(actorNames);
            System.out.println("Rating : " + rating);
            settingUpStartRating(rating);
            mReviewEditText.setText(review);
            mTitleEditText.setEnabled(false);
            currentMovieTitle = mTitleEditText.getText().toString();

        }

        cursor.close();


    }

    public void update(String mTitle,int mYear,String mdir,String mActors,int mRating,String mReview,String mIsFav){

        SQLiteDatabase db = moviesData.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MOVIE_TITLE,mTitle);
        values.put(MOVIE_YEAR,mYear);
        values.put(MOVIE_DIRECTOR_NAME,mdir);
        values.put(MOVIE_ACTORS_NAMES,mActors);
        values.put(MOVIE_RATING,mRating);
        values.put(MOVIE_REVIEW,mReview);
        values.put(FAVOURITE,mIsFav);

        String[] where = {mTitle};
        db.update(TABLE_NAME,values," MovieTitle = ?",where);
//        db.update()
//        db.execSQL("UPDATE "+TABLE_NAME + " SET ");

    }


    public void updateTheTable(View view) {



        String tempTitle = mTitleEditText.getText().toString();
        int tempYear = Integer.parseInt(mYearEditText.getText().toString());
        String tempDir = mDirectorEditText.getText().toString();
        int tempRating = chosenRating;
        String tempReview = mReviewEditText.getText().toString();
        String tempActors = mActorsNamesEditText.getText().toString();

        if(tempTitle == null || tempDir == null || tempReview == null || tempActors == null ){

            Toast toast = Toast.makeText(this,"Please Fill The Details",Toast.LENGTH_LONG);
            toast.show();
        }



        else {
            String temFav = null;
            if (radioButtonFav.isChecked() == true){
                temFav = radioButtonFav.getText().toString();
            }
            else if (radioButtonNot.isChecked() == true){
                temFav = radioButtonNot.getText().toString();
            }

            if (temFav == null){
                System.out.println("LOG");
            }
            else {
                update(tempTitle,tempYear,tempDir,tempActors,tempRating,tempReview,temFav);
                finish();
            }


        }


    }

    // Rate Stars
    public void rateStarsClick(View view) {

        //Setting up images
        for(int k = 0; k < imageViews.length ; k++){
            ImageView imageView = findViewById(imageViews[k]);
            imageView.setImageResource(greyStarImagesArray[k]);
        }

        switch (view.getId()){

            case R.id.star_grey_1 :
                ImageView imageView =findViewById(R.id.star_grey_1);
                imageView.setImageResource(R.drawable.y_1);
                chosenRating = 1;
                break;
            case R.id.star_grey_2:
                for (int i = 0; i < 2 ; i++){
                    ImageView imageView1 = findViewById(imageViews[i]);
                    imageView1.setImageResource(yellowStarImagesArray[i]);
                    chosenRating = i+1;
                }
                break;
            case R.id.star_grey_3:
                for (int i = 0; i < 3 ; i++){
                    ImageView imageView1 = findViewById(imageViews[i]);
                    imageView1.setImageResource(yellowStarImagesArray[i]);
                    chosenRating = i+1;
                }
                break;
            case R.id.star_grey_4:
                for (int i = 0; i < 4 ; i++){
                    ImageView imageView1 = findViewById(imageViews[i]);
                    imageView1.setImageResource(yellowStarImagesArray[i]);
                    chosenRating = i+1;
                }
                break;
            case R.id.star_grey_5:
                for (int i = 0; i < 5 ; i++){
                    ImageView imageView1 = findViewById(imageViews[i]);
                    imageView1.setImageResource(yellowStarImagesArray[i]);
                    chosenRating = i+1;
                }
                break;
            case R.id.star_grey_6:
                for (int i = 0; i < 6 ; i++){
                    ImageView imageView1 = findViewById(imageViews[i]);
                    imageView1.setImageResource(yellowStarImagesArray[i]);
                    chosenRating = i+1;
                }
                break;
            case R.id.star_grey_7:
                for (int i = 0; i < 7 ; i++){
                    ImageView imageView1 = findViewById(imageViews[i]);
                    imageView1.setImageResource(yellowStarImagesArray[i]);
                    chosenRating = i+1;
                }
                break;
            case R.id.star_grey_8:
                for (int i = 0; i < 8 ; i++){
                    ImageView imageView1 = findViewById(imageViews[i]);
                    imageView1.setImageResource(yellowStarImagesArray[i]);
                    chosenRating = i+1;
                }
                break;
            case R.id.star_grey_9:
                for (int i = 0; i < 9 ; i++){
                    ImageView imageView1 = findViewById(imageViews[i]);
                    imageView1.setImageResource(yellowStarImagesArray[i]);
                    chosenRating = i+1;
                }
                break;
            case R.id.star_grey_10:
                for (int i = 0; i < 10 ; i++){
                    ImageView imageView1 = findViewById(imageViews[i]);
                    imageView1.setImageResource(yellowStarImagesArray[i]);
                    chosenRating = i+1;
                }
                break;

        }
        System.out.println(chosenRating);

    }

    public void settingUpStartRating(int rating){
        // Start rating images
        for(int i = 0; i < rating ; i++){
            ImageView imageView1 = findViewById(imageViews[i]);
            imageView1.setImageResource(yellowStarImagesArray[i]);
        }
    }
}