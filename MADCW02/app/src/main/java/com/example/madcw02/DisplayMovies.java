package com.example.madcw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.madcw02.Constant.FAVOURITE;
import static com.example.madcw02.Constant.MOVIE_ACTORS_NAMES;
import static com.example.madcw02.Constant.MOVIE_DIRECTOR_NAME;
import static com.example.madcw02.Constant.MOVIE_RATING;
import static com.example.madcw02.Constant.MOVIE_REVIEW;
import static com.example.madcw02.Constant.MOVIE_TITLE;
import static com.example.madcw02.Constant.MOVIE_YEAR;
import static com.example.madcw02.Constant.TABLE_NAME;

public class DisplayMovies extends AppCompatActivity {

    private static String[] FROM ={MOVIE_TITLE , MOVIE_YEAR,MOVIE_DIRECTOR_NAME,MOVIE_ACTORS_NAMES,MOVIE_RATING,MOVIE_REVIEW,FAVOURITE};
    private MoviesData moviesData;

    private ListView mMoviesListView;

    private String[] movieTitleArray;
    ArrayList<String> temp = new ArrayList();
    private boolean[] storeStuff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);

        moviesData = new MoviesData(this);

        // Reading data from DB
        try {
            Cursor cursor = getMoviesFromDB();
            showEvent(cursor);

        }
        finally {
            moviesData.close();
        }

        movieTitleArray = new String[temp.size()];

//        movieTitleArray = (String[]) temp.toArray();

        for (int i = 0 ;i < movieTitleArray.length;i++){
            movieTitleArray[i] = temp.get(i);
        }

        Arrays.sort(movieTitleArray);

        storeStuff = new boolean[movieTitleArray.length];

        // Adapter
        ArrayAdapter adapter= new ArrayAdapter(this,R.layout.item_row_movies_dm,movieTitleArray);
        mMoviesListView = findViewById(R.id.movie_titles_list_view_dm);
        mMoviesListView.setAdapter(adapter);

        mMoviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox =(CheckBox) view;
                checkBox.setChecked(!checkBox.isChecked());
                System.out.println(checkBox.isChecked());
                storeStuff[position] = true;

            }
        });


    }



    public Cursor getMoviesFromDB(){

        SQLiteDatabase db =moviesData.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,FROM,null,null,null,null,null);

        return cursor;
    }

    // Getting all the titles
    public void showEvent(Cursor cursor){

        String title ;

        while (cursor.moveToNext()){

            title = cursor.getString(0);
            temp.add(title);
        }

        cursor.close();


    }

    // Save the Selected Favourites
    public void saveFavourites(View view) {

        System.out.println(Arrays.toString(movieTitleArray));
        System.out.println(Arrays.toString(storeStuff));

        SQLiteDatabase db = moviesData.getWritableDatabase();


        for (int i = 0;i < storeStuff.length ; i++){

            if (storeStuff[i] == true){
                ContentValues values = new ContentValues();
                values.put(FAVOURITE,"Favourite");

                String[] where = {movieTitleArray[i]};
                db.update(TABLE_NAME,values," MovieTitle = ?",where);
            }

        }

        finish();

    }

    public void cancelDisplayMovies(View view) {
        finish();
    }
}
