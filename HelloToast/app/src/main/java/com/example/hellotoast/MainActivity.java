package com.example.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;
    private float average = 0;
    private TextView mShowCount;
    private TextView mShowTimer;
    private TextView mShwAverage;
    private int milliSeconds = 20000;
    private boolean isStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShowCount = (TextView) findViewById(R.id.show_count);
        mShowTimer = (TextView) findViewById(R.id.show_timer);
        mShwAverage = (TextView) findViewById(R.id.showAverage);



    }

    public void showToast(View view) {
        Toast toast =  Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void countUp(View view) {

        if (!isStarted){
            countTimer();
        }

        mCount++;
        if(mShowCount != null){
            mShowCount.setText(Integer.toString(mCount));
        }

    }

    public void countTimer(){

        CountDownTimer timer = new CountDownTimer(milliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mShowTimer.setText("Time : " + String.valueOf(millisUntilFinished/1000));

//                if(millisUntilFinished <= 15000){
//                    mShowTimer.setBackgroundColor(Color.RED);
//                }

            }

            @Override
            public void onFinish() {
                mShowTimer.setText("Finished");

                float inSeconds = milliSeconds/1000;
                average = mCount/inSeconds;
                mShwAverage.setText("Average : " + String.valueOf(average) + " clicks per Second");
            }
        }.start();

        isStarted = true;
    }


}