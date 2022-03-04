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

public class DisplayFavouritesActivity extends AppCompatActivity {

    private static String[] FROM ={MOVIE_TITLE , MOVIE_YEAR,MOVIE_DIRECTOR_NAME,MOVIE_ACTORS_NAMES,MOVIE_RATING,MOVIE_REVIEW,FAVOURITE};
    private MoviesData moviesData;

    private String[] movieTitleArray;
    ArrayList<String> temp = new ArrayList();
    private boolean[] storeStuff;
    String[] favArray;
    private int count = 0;

    private ListView showFavListView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_favourites);

        moviesData = new MoviesData(this);

        try {
            Cursor cursor = getMoviesFromDB();
            showEvent(cursor);

        }
        finally {
            moviesData.close();
        }

        movieTitleArray = new String[temp.size()];
        favArray = new String[count];
        int storeValue = 0;

        for (int i = 0 ;i < movieTitleArray.length;i++){

            if (storeStuff[i] == true){
                System.out.println(temp.get(i));
                favArray[storeValue] = temp.get(i);
                storeValue++;
            }
            movieTitleArray[i] = temp.get(i);
        }

        showFavListView = findViewById(R.id.show_favourites_list_view_sf);

        //Adapter
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.show_layout_for_favourites,favArray);
        showFavListView.setAdapter(adapter);

        showFavListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CheckBox checkBox =(CheckBox) view;
                checkBox.setChecked(!checkBox.isChecked());

                for (int i = 0; i < movieTitleArray.length; i++){

                    if (movieTitleArray[i] == checkBox.getText()){

                        if (storeStuff[i] == true){
                            storeStuff[i] = false;
                        }
                        else {
                            storeStuff[i] = true;
                        }

                    }
                }



            }
        });


    }


    public Cursor getMoviesFromDB(){

        SQLiteDatabase db =moviesData.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,FROM,null,null,null,null,null);

        return cursor;
    }

    //Get data from DB
    public void showEvent(Cursor cursor){

        String title ;
        String fav;

        ArrayList<Boolean> favStore = new ArrayList();

        while (cursor.moveToNext()){

            title = cursor.getString(0);
            temp.add(title);

            fav = cursor.getString(6);
            if (fav.equals("Favourite")){
                favStore.add(true);
                count++;
            }
            else {
                favStore.add(false);
            }

        }
        cursor.close();

        storeStuff = new boolean[favStore.size()];

        for (int i = 0; i < favStore.size();i++){
            storeStuff[i] = favStore.get(i);
        }

        System.out.println(Arrays.toString(storeStuff));

    }

    //Saving Favourites
    public void saveNewFavourites(View view) {

        SQLiteDatabase db = moviesData.getWritableDatabase();

        System.out.println("Array : " + Arrays.toString(storeStuff));
        System.out.println("MOVIES = " + Arrays.toString(movieTitleArray));

        for (int i = 0;i < storeStuff.length ; i++){

            if (storeStuff[i] == true){
                ContentValues values = new ContentValues();
                values.put(FAVOURITE,"Favourite");

                String[] where = {movieTitleArray[i]};
                db.update(TABLE_NAME,values," MovieTitle = ?",where);
            }

            else {

                ContentValues values = new ContentValues();
                values.put(FAVOURITE,"Not Favourite");

                String[] where = {movieTitleArray[i]};
                db.update(TABLE_NAME,values," MovieTitle = ?",where);

            }

        }

        finish();

    }

    //Cancel
    public void cancelFavourites(View view) {
        finish();
    }
}