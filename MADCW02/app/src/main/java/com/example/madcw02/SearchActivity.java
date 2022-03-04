package com.example.madcw02;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.madcw02.Constant.MOVIE_ACTORS_NAMES;
import static com.example.madcw02.Constant.MOVIE_DIRECTOR_NAME;
import static com.example.madcw02.Constant.MOVIE_TITLE;
import static com.example.madcw02.Constant.TABLE_NAME;

public class SearchActivity extends AppCompatActivity {

    private EditText mSearchEditText;
    private TextView mShowTitlesTextView;
    private TextView mShowDirTextView;
    private TextView mShowActors;
    private TextView mTitleLabel;
    private TextView mActorLabel;
    private TextView mDirLabel;


    private MoviesData moviesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchEditText = findViewById(R.id.search_edit_text_sa);
        mShowTitlesTextView = findViewById(R.id.set_title_names_text_view);
        mShowDirTextView = findViewById(R.id.set_producer_names_text_view);
        mShowActors = findViewById(R.id.set_actor_names_text_view);
        mTitleLabel = findViewById(R.id.show_titles_label_sm);
        mActorLabel = findViewById(R.id.show_actors_label_sm);
        mDirLabel = findViewById(R.id.show_dir_label_sm);


    }

    // Search Item On Click
    public void searchItem(View view) {

        String searchItem = mSearchEditText.getText().toString();
        forTitles(searchItem);
        forActors(searchItem);
        forDirectors(searchItem);

        mTitleLabel.setVisibility(View.VISIBLE);
        mActorLabel.setVisibility(View.VISIBLE);
        mDirLabel.setVisibility(View.VISIBLE);

    }

    // For Titles
    public void showEventTitle(Cursor cursor){

        StringBuilder builder = new StringBuilder();

        while (cursor.moveToNext()){

            String title = cursor.getString(0);

            builder.append(title).append("\n");
        }

        cursor.close();

        mShowTitlesTextView.setText(builder.toString());

    }

    // For Actors
    public void showEventActor(Cursor cursor){

        StringBuilder builder = new StringBuilder();

        while (cursor.moveToNext()){

            String actorNames = cursor.getString(3);
            builder.append(actorNames).append("\n");
        }

        cursor.close();


        String[] parts = builder.toString().split(",");
        StringBuilder finale = new StringBuilder();

        for (String s : parts){
            if (s.contains(mSearchEditText.getText().toString())){

                finale.append(s).append("\n");
            }
        }
        mShowActors.setText(finale.toString());

    }

    // For Directors
    public void showEventDirector(Cursor cursor){

        StringBuilder builder = new StringBuilder();

        while (cursor.moveToNext()){

            String dir = cursor.getString(2);
            builder.append(dir).append("\n");
        }

        cursor.close();
        mShowDirTextView.setText(builder.toString());

    }

    // Search Titles
    public void forTitles(String search){

        moviesData = new MoviesData(this);
        SQLiteDatabase db =moviesData.getReadableDatabase();

        try {
            Cursor cursor ;
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + MOVIE_TITLE + " LIKE '%" + search + "%'" ,null );
            showEventTitle(cursor);

        }
        finally {
            moviesData.close();
        }

    }

    // Search Directors
    public void forDirectors(String search){

        moviesData = new MoviesData(this);
        SQLiteDatabase db =moviesData.getReadableDatabase();

        try {
            Cursor cursor ;
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + MOVIE_DIRECTOR_NAME + " LIKE '%" + search + "%'" ,null );
            showEventDirector(cursor);

        }
        finally {
            moviesData.close();
        }

    }

    //Search Actors
    public void forActors(String search){

        moviesData = new MoviesData(this);
        SQLiteDatabase db =moviesData.getReadableDatabase();

        try {
            Cursor cursor ;
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + MOVIE_ACTORS_NAMES + " LIKE '%" + search + "%'" ,null );
            showEventActor(cursor);

        }
        finally {
            moviesData.close();
        }

    }

}