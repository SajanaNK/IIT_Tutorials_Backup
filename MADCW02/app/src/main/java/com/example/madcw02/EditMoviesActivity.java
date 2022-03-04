package com.example.madcw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.madcw02.Constant.FAVOURITE;
import static com.example.madcw02.Constant.MOVIE_ACTORS_NAMES;
import static com.example.madcw02.Constant.MOVIE_DIRECTOR_NAME;
import static com.example.madcw02.Constant.MOVIE_RATING;
import static com.example.madcw02.Constant.MOVIE_REVIEW;
import static com.example.madcw02.Constant.MOVIE_TITLE;
import static com.example.madcw02.Constant.MOVIE_YEAR;
import static com.example.madcw02.Constant.TABLE_NAME;

public class EditMoviesActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.android.madcw02.extra.message";
    private static String[] FROM ={MOVIE_TITLE , MOVIE_YEAR,MOVIE_DIRECTOR_NAME,MOVIE_ACTORS_NAMES,MOVIE_RATING,MOVIE_REVIEW,FAVOURITE};
    private MoviesData moviesData;

    private String[] movieTitleArray;
    ArrayList<String> temp = new ArrayList();

    private ListView mEditMoviesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movies);

        moviesData = new MoviesData(this);

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

        setListView();
    }

    // List view for movies
    public void setListView(){

        mEditMoviesListView = findViewById(R.id.show_all_movies_list_view_em);

        ArrayAdapter adapter= new ArrayAdapter<String>(this,R.layout.show_edit_movies_raw_em,R.id.show_edit_movies_text_view_em,movieTitleArray);

        mEditMoviesListView.setAdapter(adapter);

        mEditMoviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                System.out.println(mEditMoviesListView.getItemAtPosition(position));

                String movieName = (String) mEditMoviesListView.getItemAtPosition(position);
                System.out.println(movieName);

                Intent intent = new Intent(EditMoviesActivity.this,ShowEditMovieActivity.class);

                intent.putExtra(EXTRA_MESSAGE,movieName);
                startActivity(intent);

            }
        });
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
}