package com.example.tutorial08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void showCustomListView(View view) {

        Intent intent = new Intent(this,ShowModulesActivity.class);
        startActivity(intent);

    }

    public void showListViewNames(View view) {

        Intent intent = new Intent(this,ShowNamesActivity.class);
        startActivity(intent);

    }
}