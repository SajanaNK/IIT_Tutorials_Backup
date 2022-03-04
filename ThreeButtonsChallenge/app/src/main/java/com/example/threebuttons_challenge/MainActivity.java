package com.example.threebuttons_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButton01;
    private Button mButton02;
    private Button mButton03;
    public static final int TEXT_REQUEST_ONE = 1;
    public static final int TEXT_REQUEST_TWO = 2;
    public static final int TEXT_REQUEST_THREE = 3;
    public static final String EXTRA_MESSAGE = "com.example.android.threebuttons.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton01 = findViewById(R.id.button01);
        mButton02 = findViewById(R.id.button02);
        mButton03 = findViewById(R.id.button03);


    }

    public void buttonOneClick(View view) {

        Intent intent = new Intent(this,Activity02.class);
        String message = "Button one clicked!";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivityForResult(intent,TEXT_REQUEST_ONE);

    }

    public void buttonTwoClick(View view) {

        Intent intent = new Intent(this,Activity02.class);
        String message = "Button two clicked!";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivityForResult(intent,TEXT_REQUEST_TWO);

    }

    public void buttonThreeClick(View view) {

        Intent intent = new Intent(this,Activity02.class);
        String message = "Button three clicked!";
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivityForResult(intent,TEXT_REQUEST_THREE);

    }
}