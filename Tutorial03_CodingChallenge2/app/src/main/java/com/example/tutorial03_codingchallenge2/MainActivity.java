package com.example.tutorial03_codingchallenge2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int TEXT_REQUEST = 1;

    private TextView mReplyTextViews[] = new TextView[10];
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReplyTextViews[0] = findViewById(R.id.textView1);
        mReplyTextViews[1] = findViewById(R.id.textView2);
        mReplyTextViews[2] = findViewById(R.id.textView3);
        mReplyTextViews[3] = findViewById(R.id.textView4);
        mReplyTextViews[4] = findViewById(R.id.textView5);
        mReplyTextViews[5] = findViewById(R.id.textView6);
        mReplyTextViews[6] = findViewById(R.id.textView7);
        mReplyTextViews[7] = findViewById(R.id.textView8);
        mReplyTextViews[8] = findViewById(R.id.textView9);
        mReplyTextViews[9] = findViewById(R.id.textView10);



    }

    public void addItem(View view) {

        Intent intent = new Intent(this,ShoppingItems.class);
        startActivityForResult(intent,TEXT_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == TEXT_REQUEST){
            System.out.println(requestCode);


                String reply = data.getStringExtra(ShoppingItems.EXTRA_REPLY);
                System.out.println(reply);
                mReplyTextViews[count].setText(reply);
                count++;

        }
    }
}