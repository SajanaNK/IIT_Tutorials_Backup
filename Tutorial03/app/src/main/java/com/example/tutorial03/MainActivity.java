package com.example.tutorial03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private boolean isBrittany = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView v1 = findViewById(R.id.im_view);

        String resource = "brittany_02625";
        isBrittany = true;
        int resource_id = getResources().getIdentifier(resource,"drawable", "com.example.tutorial03");


        v1.setImageResource(resource_id);
    }

    public void changeImage(View view) {


        if (isBrittany){

            ImageView v1 = findViewById(R.id.im_view);

            String rescource = "welsh_springer_spaniel_08203";
            int resourceID = getResources().getIdentifier(rescource,"drawable","com.example.tutorial03");

            v1.setImageResource(resourceID);
            isBrittany = false;
        }

        else {

            ImageView v1 = findViewById(R.id.im_view);

            String resource = "brittany_02625";
            isBrittany = true;
            int resource_id = getResources().getIdentifier(resource,"drawable", "com.example.tutorial03");


            v1.setImageResource(resource_id);
        }



    }
}