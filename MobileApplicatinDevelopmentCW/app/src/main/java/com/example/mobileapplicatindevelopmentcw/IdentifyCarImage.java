package com.example.mobileapplicatindevelopmentcw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class IdentifyCarImage extends AppCompatActivity {

    // Declaring the imageViews, Text Views , Edit Texts , Buttons
    private ImageView mCarImageViewOne;
    private ImageView mCarImageViewTwo;
    private ImageView mCarImageViewThree;
    private Button mButtonNext;
    private TextView mCarNameTextView;
    private TextView mResponseTextView;
    private TextView mTimerTextView;

    // Declaring the Variables are that using
    private int[] imagesArray;
    private int[] chosenImagesArray;
    private ArrayList<String> imagesContentDescriptionArray;
    private boolean attemptTaken;
    private int milliSeconds = 30000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_image);

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

        attemptTaken = false;

        mCarImageViewOne = findViewById(R.id.car_imageview_one);
        mCarImageViewTwo = findViewById(R.id.car_imageview_two);
        mCarImageViewThree = findViewById(R.id.car_imageview_three);
        mButtonNext = findViewById(R.id.next_button_ICI);
        mResponseTextView = findViewById(R.id.response_textView_ICI);
        mCarNameTextView = findViewById(R.id.car_name_textview_ICI);
        mTimerTextView = findViewById(R.id.timer_text_view_ICI);

        // For generating a random number to get a random image and set that image to the image Viewer
        setImages();

        mCarImageViewOne.setImageResource(chosenImagesArray[0]);
        mCarImageViewOne.setContentDescription(imagesContentDescriptionArray.get(0));

        mCarImageViewTwo.setImageResource(chosenImagesArray[1]);
        mCarImageViewTwo.setContentDescription(imagesContentDescriptionArray.get(1));

        mCarImageViewThree.setImageResource(chosenImagesArray[2]);
        mCarImageViewThree.setContentDescription(imagesContentDescriptionArray.get(2));

        Random rnd = new Random();
        int randomNumber = rnd.nextInt(chosenImagesArray.length);

        String carName = imagesContentDescriptionArray.get(randomNumber);
        mCarNameTextView.setText(carName);

        System.out.println("Car Name : " + "["+carName+ "]");

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

        if (imagesContentDescriptionArray.get(x).equals(imagesContentDescriptionArray.get(x + 1)) || imagesContentDescriptionArray.get(x + 1).equals(imagesContentDescriptionArray.get(x + 2)) || imagesContentDescriptionArray.get(x).equals(imagesContentDescriptionArray.get(x + 2))){
            setImages();
        }

        System.out.println(imagesContentDescriptionArray.toString());
    }

    // To restart the game
    public void restartGame(View view) {
        finish();
        startActivity(getIntent());

    }

    // Image onclick checking
    public void checkClickedImageOne(View view) {

        if (!attemptTaken) {
            System.out.println("Image One : " + "[" + mCarImageViewOne.getContentDescription() + "]");
            if (mCarImageViewOne.getContentDescription().equals(mCarNameTextView.getText())) {
                System.out.println("CORRECT");

                mResponseTextView.setText(R.string.correct);
                mResponseTextView.setTextColor(Color.GREEN);

                mCarImageViewOne.setBackgroundColor(Color.GREEN);
            } else {
                mResponseTextView.setText(R.string.wrong);
                mResponseTextView.setTextColor(Color.RED);

                mCarImageViewOne.setBackgroundColor(Color.RED);

                if (mCarImageViewTwo.getContentDescription().equals(mCarNameTextView.getText())){

                    mCarImageViewTwo.setBackgroundColor(Color.GREEN);
                }
                else if (mCarImageViewThree.getContentDescription().equals(mCarNameTextView.getText())){

                    mCarImageViewThree.setBackgroundColor(Color.GREEN);
                }

            }
        }
        attemptTaken = true;
        mButtonNext.setVisibility(Button.VISIBLE);
    }

    public void checkClickedImageTwo(View view) {

        if (!attemptTaken) {
            System.out.println("Image two : " + "[" + mCarImageViewTwo.getContentDescription() + "]");
            if (mCarImageViewTwo.getContentDescription().equals(mCarNameTextView.getText())) {
                System.out.println("CORRECT");

                mResponseTextView.setText(R.string.correct);
                mResponseTextView.setTextColor(Color.GREEN);

                mCarImageViewTwo.setBackgroundColor(Color.GREEN);
            } else {
                mResponseTextView.setText(R.string.wrong);
                mResponseTextView.setTextColor(Color.RED);

                mCarImageViewTwo.setBackgroundColor(Color.RED);

                if (mCarImageViewOne.getContentDescription().equals(mCarNameTextView.getText())){

                    mCarImageViewOne.setBackgroundColor(Color.GREEN);
                }

                else if (mCarImageViewThree.getContentDescription().equals(mCarNameTextView.getText())){

                    mCarImageViewThree.setBackgroundColor(Color.GREEN);
                }

            }
        }
        attemptTaken = true;
        mButtonNext.setVisibility(Button.VISIBLE);
    }

    public void checkClickedImageThree(View view) {

        if (!attemptTaken) {

            System.out.println("Image Three : " + "[" + mCarImageViewThree.getContentDescription() + "]");
            if (mCarImageViewThree.getContentDescription().equals(mCarNameTextView.getText())) {
                System.out.println("CORRECT");

                mResponseTextView.setText(R.string.correct);
                mResponseTextView.setTextColor(Color.GREEN);

                mCarImageViewThree.setBackgroundColor(Color.GREEN);
            } else {
                mResponseTextView.setText(R.string.wrong);
                mResponseTextView.setTextColor(Color.RED);

                mCarImageViewThree.setBackgroundColor(Color.RED);

                if (mCarImageViewOne.getContentDescription().equals(mCarNameTextView.getText())){

                    mCarImageViewTwo.setBackgroundColor(Color.GREEN);
                }
                else if(mCarImageViewTwo.getContentDescription().equals(mCarNameTextView.getText())){

                    mCarImageViewOne.setBackgroundColor(Color.GREEN);
                }
            }
        }

        attemptTaken = true;
        mButtonNext.setVisibility(Button.VISIBLE);
    }

    private void startTimer() {

        CountDownTimer countDownTimer = new CountDownTimer(milliSeconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                mTimerTextView.setText(String.valueOf(millisUntilFinished/1000));

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

            @Override
            public void onFinish() {
                mButtonNext.setVisibility(Button.VISIBLE);

                mResponseTextView.setText(R.string.wrong);
                mResponseTextView.setTextColor(Color.RED);

                if (mCarImageViewOne.getContentDescription().equals(mCarNameTextView.getText())){

                    mCarImageViewTwo.setBackgroundColor(Color.GREEN);
                }
                else if(mCarImageViewTwo.getContentDescription().equals(mCarNameTextView.getText())){

                    mCarImageViewOne.setBackgroundColor(Color.GREEN);
                }

                else if (mCarImageViewThree.getContentDescription().equals(mCarNameTextView.getText())){

                    mCarImageViewThree.setBackgroundColor(Color.GREEN);
                }

            }
        }.start();
    }

}