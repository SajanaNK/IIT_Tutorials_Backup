package com.example.mobileapplicatindevelopmentcw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

public class IdentifyTheCarName extends AppCompatActivity {

    // Declaring the Button , ImageView , TextViews, Spinner [For dropdown List]
    private Button mIdentifyButton;
    private ImageView mImageViewer;
    private TextView mResponseTextView;
    private TextView mCorrectAnswerTextView;
    private Spinner spinner;
    private TextView mTimerTextView;

    // Declaring using variables
    private int[] imagesArray;
    private int milliSeconds = 30000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_the_class_name);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Get the message from the main activity. If the timer switch is on timer will start
        if (message.equals("Switch On")){
            startTimer();
        }

        // Declaring the images
        imagesArray = new int[]{
                R.drawable.audi,R.drawable.audi_01,R.drawable.audi_02,
                R.drawable.benz,R.drawable.benz_02,R.drawable.benz_01,
                R.drawable.bmw,R.drawable.bmw_01, R.drawable.bmw_02,
                R.drawable.ford,R.drawable.ford_01,R.drawable.ford_02,
                R.drawable.honda,R.drawable.honda_02,R.drawable.honda_01,
                R.drawable.micro,R.drawable.micro_01,R.drawable.micro_02,
                R.drawable.toyota,R.drawable.toyota_01,R.drawable.toyota_02,
                R.drawable.tesla,R.drawable.tesla_01,R.drawable.tesla_02,
                R.drawable.lamborghini,R.drawable.lamborghini_01,R.drawable.lamborghini_02,
                R.drawable.ferrari,R.drawable.ferrari_01,R.drawable.ferrari_02
        };

        mImageViewer = findViewById(R.id.car_image_viewer);
        mResponseTextView = findViewById(R.id.response_textview);
        mCorrectAnswerTextView = findViewById((R.id.correct_ansewer_textview));
        mIdentifyButton = findViewById(R.id.identify_button);
        mTimerTextView = findViewById(R.id.timer_text_view_ICN);

        // For generating a random number to get a random image and set that image to the image Viewer
        Random rnd = new Random();
        int rndNumber = rnd.nextInt(imagesArray.length);
        mImageViewer.setImageResource(imagesArray[rndNumber]);
        mImageViewer.setContentDescription(getResources().getResourceName(imagesArray[rndNumber]));

        // Dropdown List
        spinner =findViewById(R.id.car_list_dropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.car_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        if (spinner != null){
            spinner.setAdapter(adapter);
        }

    }

    // Submit button onclick
    public void checkGuess(View view) {
        getAnswer();
    }

    // Getting the Answer from Dropdown List
    public void getAnswer(){

        String userAnswer = spinner.getSelectedItem().toString().toLowerCase();
        String correctAnswer = mImageViewer.getContentDescription().toString();
        correctAnswer = correctAnswer.replace("com.example.mobileapplicatindevelopmentcw:drawable/","");
        System.out.println("Correct Answer : " + correctAnswer);

        System.out.println(mIdentifyButton.getText() );
        if (mIdentifyButton.getText().equals("Identify") ) {

            if (correctAnswer.contains(userAnswer)) {
                mResponseTextView.setText(R.string.correct);
                mResponseTextView.setTextColor(Color.GREEN);
            }
            else {
                mResponseTextView.setText(R.string.wrong);
                mResponseTextView.setTextColor(Color.RED);

                correctAnswer = correctAnswer.replace("_01","");
                correctAnswer = correctAnswer.replace("_02","");
                correctAnswer = correctAnswer.toUpperCase();

                mCorrectAnswerTextView.setText(correctAnswer);
                mCorrectAnswerTextView.setTextColor(Color.YELLOW);
                mCorrectAnswerTextView.setBackgroundColor(getResources().getColor(R.color.green_lower));
            }

            mIdentifyButton.setText(R.string.next);
        }
        else if(mIdentifyButton.getText().equals("Next") ){
            finish();
            startActivity(getIntent());
        }
    }

    // Countdown Timer
    private void startTimer() {

        CountDownTimer countDownTimer = new CountDownTimer(milliSeconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimerTextView.setText(String.valueOf(millisUntilFinished/1000));

                // Setting the background colors to the Timer TextView
                if (millisUntilFinished <= 30000){
                    mTimerTextView.setBackgroundColor(getResources().getColor(R.color.green_lower));
                }
                if (millisUntilFinished < 20000){
                    mTimerTextView.setBackgroundColor(getResources().getColor(R.color.green_low));
                }
                if (millisUntilFinished < 15000){
                    mTimerTextView.setBackgroundColor(getResources().getColor(R.color.red_pink));
                }
                if (millisUntilFinished < 10000){
                    mTimerTextView.setBackgroundColor(getResources().getColor(R.color.red_lowest));
                }
                if (millisUntilFinished < 5000){
                    mTimerTextView.setBackgroundColor(getResources().getColor(R.color.red_low));
                }
            }

            // On finishing the Timer
            @Override
            public void onFinish() {
                mTimerTextView.setText(String.valueOf("DONE"));
                getAnswer();

            }
        }.start();
    }


//    public void spinnerOnclick(View view) {
//        startTimer();
//    }
}