package com.example.tutorial08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ShowNamesActivity extends AppCompatActivity {

    ListView mListViewNames ;


    String[] namesArray = {"Henry Caville", "Anthony Mackie", "Sebestian Stan","Natalie Portman","Scarlet Johnson"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_names);


        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this,R.layout.names_list_view,R.id.names_show_text_view,namesArray);



        mListViewNames = findViewById(R.id.names_list_view);
        mListViewNames.setAdapter(arrayAdapter);



        mListViewNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = (String) arrayAdapter.getItem(position);

                Toast toast = Toast.makeText(getApplicationContext(),item,Toast.LENGTH_LONG);
                toast.show();

            }
        });

    }

}