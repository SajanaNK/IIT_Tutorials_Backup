package com.example.threebuttons_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Activity02 extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_02);

        mTextView =findViewById(R.id.textView);

        Intent intent = getIntent();
        String message =intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        if (message.equals("Button one clicked!")){
            mTextView.setText(getResources().getString(R.string.paragraph_one));
        }

        else if (message.equals("Button two clicked!")){
            mTextView.setText(getResources().getString(R.string.paragraph_two));
        }
        else if (message.equals("Button three clicked!")){
            mTextView.setText(getResources().getString(R.string.paragraph_three));
        }
    }
}