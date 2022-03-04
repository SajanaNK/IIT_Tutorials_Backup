package com.example.mobileapplicatindevelopmentcw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Hints extends AppCompatActivity {

    // Declaring the imageViews, Text Views , Edit Texts , Buttons
    private ImageView mImageViewer;
    private EditText mCharacterEditText;
    private TextView mCarNameTextView;
    private TextView mResponseTextView;
    private TextView mAnswerTextView;
    private Button mSubmitButton;
    private TextView mTimerTextView;

    // Declaring the Variables are that using
    private StringBuilder emptyCharacters;
    private String carName;
    private int numOfGuesses;
    private int[] imagesArray;
    private String[] nameCharactersArray;
    private int milliSeconds = 30000;
    private CountDownTimer countDownTimer;
    private boolean countdownStarted = false;

    private String[] guessesStored;
    private StringBuilder gussesStringBuilder = new StringBuilder();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Get the message from the main activity. If the timer switch is on timer will start
        if (message.equals("Switch On")){
            countdownStarted = true;
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

        mImageViewer = findViewById(R.id.car_img_imageView);
        mCharacterEditText = findViewById(R.id.char_guess_textBox);
        mCarNameTextView = findViewById(R.id.car_name_textVIew);
        mAnswerTextView = findViewById(R.id.correct_answer_hints_textView);
        mResponseTextView = findViewById(R.id.response_hints_textView);
        mSubmitButton = findViewById(R.id.submit_button);
        mTimerTextView = findViewById(R.id.timer_text_view_hints);

        // For generating a random number to get a random image and set that image to the image Viewer
        Random rnd = new Random();
        int rndNumber = rnd.nextInt(imagesArray.length);
        mImageViewer.setImageResource(imagesArray[rndNumber]);
        mImageViewer.setContentDescription(getResources().getResourceName(imagesArray[rndNumber]));

        // Getting the car name and its properties
        carName = getCarName();
        emptyCharacters = new StringBuilder();
        nameCharactersArray =  new String[carName.length()];

        // appending the "-" to the string builder
        for (int i = 0 ; i < carName.length() ; i++){
            emptyCharacters.append("-");
            nameCharactersArray[i] = String.valueOf(carName.charAt(i));
        }

        // Displaying the number of characters
        mCarNameTextView.setText(emptyCharacters);

        guessesStored = new String[nameCharactersArray.length];
        numOfGuesses = 0;
    }

    // To get the car name
    public String getCarName(){

        String carName = mImageViewer.getContentDescription().toString();
        carName = carName.replace("com.example.mobileapplicatindevelopmentcw:drawable/","");

        if (carName.contains("_01") ){
            carName = carName.replace("_01","");
        }
        else if (carName.contains("_02")){
            carName = carName.replace("_02","");
        }

        return carName.toUpperCase();

    }

    // Submit button on Click
    public void submitCharacter(View view) {

        // Reset the timer
        if (countdownStarted == true){
            countDownTimer.cancel();
            countDownTimer.start();
        }

        Editable guess = mCharacterEditText.getText();
        carName = getCarName();
        int possition = 0;
        ArrayList<Integer> storedChar = new ArrayList();

        // If the submit button text is "Next". The game mode will restart
        if (mSubmitButton.getText().equals("Next") ){

            finish();
            startActivity(getIntent());
        }

        // Checking the user guesses
        if (carName.contains(guess)){
            //Storing the similar letters in the car name
            for (int k = 0 ; k < nameCharactersArray.length ; k++){

                if (nameCharactersArray[k].equals(String.valueOf(guess))){
                    storedChar.add(k);
                }
            }

            int x = 0;
            // setting the stored letters
            for (int n = 0 ; n < nameCharactersArray.length ; n++){
                if (n == x){

                    if (x == storedChar.size()){
                        break;
                    }
                    else{
                        System.out.println("n = " + n);
                        possition = storedChar.get(x);
                        guessesStored[possition] = String.valueOf(guess);
                        x = x + 1;
                    }

                }
            }


            System.out.println(Arrays.toString(guessesStored));
            gussesStringBuilder.setLength(0);
            for (int i = 0 ; i < nameCharactersArray.length ; i++){


                if (guessesStored[i] == null){
                    gussesStringBuilder.append("-");
                }

                else {
                    gussesStringBuilder.insert(i, guessesStored[i]);
                }

            }
            System.out.println(gussesStringBuilder.toString());
            mCarNameTextView.setText(gussesStringBuilder);


        }
        else {
            numOfGuesses = numOfGuesses + 1;
        }

        // Checking the number of guesses
        if (numOfGuesses == 3){
            mResponseTextView.setText(R.string.wrong);
            mResponseTextView.setTextColor(Color.RED);

            mAnswerTextView.setText(carName);
            mAnswerTextView.setTextColor(Color.YELLOW);
            mAnswerTextView.setBackgroundColor(getResources().getColor(R.color.green_lower));

            mSubmitButton.setText(R.string.next);
            mCharacterEditText.setEnabled(false);
        }
        // IF the the guesses are correct
        if (gussesStringBuilder.toString().equals(carName)){
            mResponseTextView.setText(R.string.correct);
            mResponseTextView.setTextColor(Color.GREEN);

            mSubmitButton.setText(R.string.next);
        }

        mCharacterEditText.getText().clear();

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

                System.out.println("carname : "+ carName);
                mResponseTextView.setText(R.string.wrong);
                mResponseTextView.setTextColor(Color.RED);

                mAnswerTextView.setText(carName);
                mAnswerTextView.setTextColor(Color.YELLOW);
                mAnswerTextView.setBackgroundColor(getResources().getColor(R.color.green_lower));

                mSubmitButton.setText(R.string.next);


            }
        }.start();
    }

}