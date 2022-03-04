package com.example.cocktailapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchByIngredientEditText;
    private TextView mShowDrinklNames;
    private EditText mSearchCocktail;
    private TextView mShowInstructionsTextView;
    private ImageView mShowCocktailImageView;
    private Spinner spinner;
    private TextView mCocktailNameTextView;

    private String ingredient;
    private StringBuilder drinksSet;

    private String cocktailName;

    private boolean isCocktailByIngrediant ;
    private boolean isInstructions;
    private boolean isRandomCocktail;

    private Bitmap bitmap;
    private String bitmapURL;
    private ArrayList<String> cocktailsList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchByIngredientEditText = findViewById(R.id.search_ingrediant_edit_text);
        mShowDrinklNames = findViewById(R.id.search_drinks_text_view);
        mSearchCocktail = findViewById(R.id.search_cocktail_edit_text);
        mShowInstructionsTextView = findViewById(R.id.search_cocktail_instructions_text_view);
        mShowCocktailImageView = findViewById(R.id.display_cocktail_image_viewer);
        mCocktailNameTextView = findViewById(R.id.cocktail_name_text_view);

        drinksSet = new StringBuilder();

        isCocktailByIngrediant = false;
        isInstructions = false;
        isRandomCocktail = false;

        spinner = findViewById(R.id.spinner);

        getCocktailBySpinner();
    }

    public void getCocktailData(){

        Thread t1  = new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder contentResult = new StringBuilder();

                String baseURL = null;

                if (isCocktailByIngrediant){
                    baseURL = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=" + ingredient;
                    System.out.println(baseURL);
                }
                else if (isInstructions){
                    baseURL = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + cocktailName;
                    System.out.println(baseURL);
                }
                else if (isRandomCocktail){
                    baseURL = "https://www.thecocktaildb.com/api/json/v1/1/random.php";
                    System.out.println(baseURL);
                }

                try {

                    URL requestURL = new URL(baseURL);
                    URLConnection connection =requestURL.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String contentAsString;

                    while ((contentAsString = reader.readLine()) != null){
                        contentResult.append(contentAsString);
                    }
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (isCocktailByIngrediant == true){
                                cocktailByIngrediantJsonToString(contentResult.toString());
                                isCocktailByIngrediant = false;
                            }
                            else if (isInstructions == true){
                                System.out.println("isInstructions" + isInstructions);
                                cocktailDescriptionJsonToString(contentResult.toString());
                                isInstructions = false;
                                System.out.println("isInstructions" + isInstructions);
                            }
                            else if (isRandomCocktail == true){
                                System.out.println("isRandomCocktail" + isRandomCocktail);
                                cocktailDescriptionJsonToString(contentResult.toString());
                                isRandomCocktail = false;
                                System.out.println("isRandomCocktail" + isRandomCocktail);
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mShowDrinklNames.setText(drinksSet.toString());
                    }
                });


            }
        });

        t1.start();


    }




    public void getIngredient(View view) {
        isCocktailByIngrediant = true;
        ingredient = mSearchByIngredientEditText.getText().toString();
        getCocktailData();

    }

    public void getCocktail(View view) {
        isInstructions = true;
        cocktailName = mSearchCocktail.getText().toString();
        getCocktailData();

    }

    public void getRandom(View view) {
        isRandomCocktail = true;
        getCocktailData();

    }

    public void cocktailByIngrediantJsonToString(String response) throws JSONException {

        System.out.println(response);

        JSONObject data = new JSONObject(response);

        JSONArray drinksArray = data.getJSONArray("drinks");

        drinksSet.append("Drink List On : ").append(ingredient).append("\n");
        for (int i = 0; i < drinksArray.length() ; i++){
            JSONObject drinkJson = drinksArray.getJSONObject(i);
            String drink = drinkJson.getString("strDrink");
            drinksSet.append("   ").append(drink).append("\n");
            cocktailsList.add(drink);
        }

        System.out.println(drinksSet.toString());

    }

    public void cocktailDescriptionJsonToString(String response02) throws JSONException {

        mCocktailNameTextView.setText("");
        mShowInstructionsTextView.setText("");
        mShowInstructionsTextView.setText("");

        String cocktailDescription;

        JSONObject data = new JSONObject(response02);

        JSONArray drinksArray = data.getJSONArray("drinks");

        JSONObject drink = drinksArray.getJSONObject(0);

        cocktailDescription = drink.getString("strInstructions");
        mShowInstructionsTextView.setText("Instructions : "+"\n \n" + cocktailDescription);
        System.out.println(cocktailDescription);

        bitmapURL = drink.getString("strDrinkThumb");

        String cocktailNameTemp = drink.getString("strDrink");
        mCocktailNameTextView.setText(cocktailNameTemp);
        getImage();
    }

    public void getImage(){


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                URL url = null;
                try {

                    url = new URL(bitmapURL);
                    bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mShowCocktailImageView.setImageBitmap(bitmap);

                    }
                });
            }
        });
        t2.start();
    }

    public void getCocktailBySpinner(){

        cocktailsList = new ArrayList<>();

        cocktailsList.add("Select Cocktail");
        System.out.println(cocktailsList.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,cocktailsList);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isInstructions = true;
                cocktailName = (String) parent.getItemAtPosition(position);
                getCocktailData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



}