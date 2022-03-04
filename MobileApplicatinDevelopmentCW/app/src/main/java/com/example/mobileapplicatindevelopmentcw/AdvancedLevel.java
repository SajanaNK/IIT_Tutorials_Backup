package com.example.mobileapplicatindevelopmentcw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class AdvancedLevel extends AppCompatActivity {

    // Declaring the imageViews, Text Views , Edit Texts , Buttons
    private ImageView mCarImageViewOne;
    private ImageView mCarImageViewTwo;
    private ImageView mCarImageViewThree;
    private EditText mCarNameEditText01;
    private EditText mCarNameEditText02;
    private EditText mCarNameEditText03;
    private TextView mPointsViewTextView;
    private Button mSubmitButton;
    private TextView mTimerTextView;
    private TextView mCorrectAnswerTextView01;
    private TextView mCorrectAnswerTextView02;
    private TextView mCorrectAnswerTextView03;

    // Declaring the Variables are that using
    private int[] imagesArray;
    private int[] chosenImagesArray;
    private ArrayList<String> imagesContentDescriptionArray;
    private int numOfPoints = 0;
    private int numOfTries = 0;
    private int milliSeconds = 30000;
    private CountDownTimer countDownTimer;
    private boolean isCorrectGuess1 = false;
    private boolean isCorrectGuess2 = false;
    private boolean isCorrectGuess3 = false;
    private boolean timerStarted = false;

    private String userCarNameOne;
    private String correctCarNameOne;
    private String userCarNameTwo;
    private String correctCarNameTwo;
    private String userCarNameThree;
    private String correctCarNameThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Get the message from the main activity. If the timer switch is on timer will start
        if (message.equals("Switch On")){
            timerStarted = true;
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

        mCarImageViewOne = findViewById(R.id.AL_imageView01);
        mCarImageViewTwo = findViewById(R.id.AL_imageView02);
        mCarImageViewThree = findViewById(R.id.AL_imageView03);
        mCarNameEditText01 = findViewById(R.id.answer_AL_editText01);
        mCarNameEditText02 = findViewById(R.id.answer_AL_editText02);
        mCarNameEditText03 = findViewById(R.id.answer_AL_editText03);
        mPointsViewTextView = findViewById(R.id.points_display_AL_textView);
        mSubmitButton = findViewById(R.id.submit_AL_button);
        mTimerTextView = findViewById(R.id.timer_text_view_AL);
        mCorrectAnswerTextView01 = findViewById(R.id.correct_answer01_text_view_AL);
        mCorrectAnswerTextView02 = findViewById(R.id.correct_answer02_text_view_AL);
        mCorrectAnswerTextView03 = findViewById(R.id.correct_answer03_text_view_AL);

        // Setting the images and image description
        setImages();

        mCarImageViewOne.setImageResource(chosenImagesArray[0]);
        mCarImageViewOne.setContentDescription(imagesContentDescriptionArray.get(0));

        mCarImageViewTwo.setImageResource(chosenImagesArray[1]);
        mCarImageViewTwo.setContentDescription(imagesContentDescriptionArray.get(1));

        mCarImageViewThree.setImageResource(chosenImagesArray[2]);
        mCarImageViewThree.setContentDescription(imagesContentDescriptionArray.get(2));


        correctCarNameOne = mCarImageViewOne.getContentDescription().toString();
        correctCarNameTwo = mCarImageViewTwo.getContentDescription().toString();
        correctCarNameThree = mCarImageViewThree.getContentDescription().toString();


    }

    // Setting images
    public void setImages(){

        imagesContentDescriptionArray = new ArrayList<>();

        Random rnd = new Random();
        int rndNumber = 0;

        chosenImagesArray = new int[3];

        for (int i = 0 ; i < chosenImagesArray.length ; i++){
            rndNumber = rnd.nextInt(imagesArray.length);
            chosenImagesArray[i] = imagesArray[rndNumber];
            imagesContentDescriptionArray.add(getResources().getResourceName(imagesArray[rndNumber]));
        }


        for (int i = 0 ; i < chosenImagesArray.length ; i++){
            String carName = imagesContentDescriptionArray.get(i);
            carName = carName.replace("com.example.mobileapplicatindevelopmentcw:drawable/","");
            imagesContentDescriptionArray.remove(i);
            imagesContentDescriptionArray.add(i,carName);


            if (carName.contains("_01") ){
                carName = carName.replace("_01","");
                imagesContentDescriptionArray.remove(i);
                imagesContentDescriptionArray.add(i,carName.toUpperCase());
            }
            else if (carName.contains("_02")){
                carName = carName.replace("_02","");
                imagesContentDescriptionArray.remove(i);
                imagesContentDescriptionArray.add(i,carName.toUpperCase());
            }
            else {
                imagesContentDescriptionArray.remove(i);
                imagesContentDescriptionArray.add(i,carName.toUpperCase());
            }

        }

        int x = 0;
        //Checking whether u have the same images  or not. if it is running the setImages() again
        if (imagesContentDescriptionArray.get(x).equals(imagesContentDescriptionArray.get(x + 1)) || imagesContentDescriptionArray.get(x + 1).equals(imagesContentDescriptionArray.get(x + 2)) || imagesContentDescriptionArray.get(x).equals(imagesContentDescriptionArray.get(x + 2))){
            setImages();
        }

        System.out.println(imagesContentDescriptionArray.toString());
    }

    // Submit button onclick
    public void submitCarNames(View view) {

        // Resetting the timer
        if (timerStarted == true){
            countDownTimer.cancel();
            countDownTimer.start();
        }

        //Getting the user's typed car names and checking them [after]
        userCarNameOne = mCarNameEditText01.getText().toString().toUpperCase();
        userCarNameTwo = mCarNameEditText02.getText().toString().toUpperCase();
        userCarNameThree = mCarNameEditText03.getText().toString().toUpperCase();

        numOfTries = numOfTries + 1;

        if (mSubmitButton.getText().equals("Next")){
            finish();
            startActivity(getIntent());
        }

        if (userCarNameOne.equals(correctCarNameOne)){
            if (!isCorrectGuess1){
                mCarNameEditText01.setEnabled(false);
                mCarNameEditText01.setBackgroundColor(Color.GREEN);

                numOfPoints = numOfPoints + 1;
                mPointsViewTextView.setText(Integer.toString(numOfPoints));
            }

            isCorrectGuess1 = true;
        }

        else {
            mCarNameEditText01.setBackgroundColor(Color.RED);
        }

        if (userCarNameTwo.equals(correctCarNameTwo)){

            if (!isCorrectGuess2){
                mCarNameEditText02.setEnabled(false);
                mCarNameEditText02.setBackgroundColor(Color.GREEN);

                numOfPoints = numOfPoints + 1;
                mPointsViewTextView.setText(Integer.toString(numOfPoints));
            }

            isCorrectGuess2 = true;
        }

        else {
            mCarNameEditText02.setBackgroundColor(Color.RED);
        }

        if (userCarNameThree.equals(correctCarNameThree)){

            if (!isCorrectGuess3){
                mCarNameEditText03.setEnabled(false);
                mCarNameEditText03.setBackgroundColor(Color.GREEN);
                numOfPoints = numOfPoints + 1;
                mPointsViewTextView.setText(Integer.toString(numOfPoints));
            }
            isCorrectGuess3 = true;
        }

        else {
            mCarNameEditText03.setBackgroundColor(Color.RED);
        }

        if (numOfTries == 3){

            if (!isCorrectGuess1){
                mCorrectAnswerTextView01.setText(correctCarNameOne);
                mCorrectAnswerTextView01.setTextColor(Color.YELLOW);
                mCorrectAnswerTextView01.setBackgroundColor(getResources().getColor(R.color.purple_one));
            }

            if(!isCorrectGuess2){
                mCorrectAnswerTextView02.setText(correctCarNameTwo);
                mCorrectAnswerTextView02.setTextColor(Color.YELLOW);
                mCorrectAnswerTextView02.setBackgroundColor(getResources().getColor(R.color.purple_one));
            }

            if (!isCorrectGuess3){
                mCorrectAnswerTextView03.setText(correctCarNameThree);
                mCorrectAnswerTextView03.setTextColor(Color.YELLOW);
                mCorrectAnswerTextView03.setBackgroundColor(getResources().getColor(R.color.purple_one));
            }


            mSubmitButton.setText(R.string.next);
        }

    }

    // Countdown Timer
    private void startTimer() {

        countDownTimer = new CountDownTimer(milliSeconds,1000) {
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
                boolean emptyAns = false;

                if (mCarNameEditText01.getText().toString().equals("") || mCarNameEditText02.getText().toString().equals("") || mCarNameEditText03.getText().toString().equals("")){
                    emptyAns = true;
                }

                if (!isCorrectGuess1 || !emptyAns){
                    mCorrectAnswerTextView01.setText(correctCarNameOne);
                    mCorrectAnswerTextView01.setTextColor(Color.YELLOW);
                    mCorrectAnswerTextView01.setBackgroundColor(getResources().getColor(R.color.purple_one));
                }

                if(!isCorrectGuess2 || !emptyAns){
                    mCorrectAnswerTextView02.setText(correctCarNameTwo);
                    mCorrectAnswerTextView02.setTextColor(Color.YELLOW);
                    mCorrectAnswerTextView02.setBackgroundColor(getResources().getColor(R.color.purple_one));
                }

                if (!isCorrectGuess3 || !emptyAns){
                    mCorrectAnswerTextView03.setText(correctCarNameThree);
                    mCorrectAnswerTextView03.setTextColor(Color.YELLOW);
                    mCorrectAnswerTextView03.setBackgroundColor(getResources().getColor(R.color.purple_one));
                }

                mSubmitButton.setText(R.string.next);

            }
        }.start();
    }
}