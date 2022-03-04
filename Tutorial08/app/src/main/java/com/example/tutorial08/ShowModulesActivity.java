    package com.example.tutorial08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.HashSet;
import java.util.Set;

public class ShowModulesActivity extends AppCompatActivity {

    ListView mListViewModules;

    String[] modules = {"Mobile Application Development", "Machine Learning", "Client Server", "iOS Programming", "Java Programming", "Python Programming"};

    public static final String preferences = "My preferences";
    public static final String item = "Item";

    SharedPreferences sharedPreferences;
    Set<String> set = new HashSet<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_modules);

        sharedPreferences = getSharedPreferences(preferences, Context.MODE_PRIVATE);

        set = sharedPreferences.getStringSet(item,set);

        for (String s : set){
            int p = Integer.parseInt(s);
            mListViewModules.setItemChecked(p,true);
        }


        ArrayAdapter arrayAdapterModules = new ArrayAdapter<String>(this,R.layout.modules_show,R.id.show_modules_text_view,modules);

        mListViewModules = findViewById(R.id.modules_show_list_view);
        mListViewModules.setAdapter(arrayAdapterModules);
        mListViewModules.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        mListViewModules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CheckedTextView checkBox = view.findViewById(R.id.show_modules_text_view);
                checkBox.setChecked(!checkBox.isChecked());
                mListViewModules.getItemAtPosition(position);

                CheckBox c = view.findViewById(R.id.check_box_modules);
                c.setChecked(!c.isChecked());
                
                System.out.println("XXXXXXXXXXXXX");

                SharedPreferences.Editor editor = sharedPreferences.edit();
                set.add(String.valueOf(position));
                editor.putStringSet(item,set);
                editor.commit();
            }
        });



    }
}