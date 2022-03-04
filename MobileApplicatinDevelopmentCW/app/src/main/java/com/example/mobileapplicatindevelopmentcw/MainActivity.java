package com.example.mobileapplicatindevelopmentcw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private boolean isSwitchOn = false;

    public static final String EXTRA_MESSAGE = "com.example.android.mobileapplicatindevelopmentcw.extra.MESSAGE";

    private Button mSwitchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwitchButton = findViewById(R.id.switch_button);
    }

    public void carMakeIdentify(View view) {
        Intent intent = new Intent(this,IdentifyTheCarName.class);
        String message = "Switch On";

        if (isSwitchOn){
            intent.putExtra(EXTRA_MESSAGE,message);
            startActivity(intent);
        }
        else{
            intent.putExtra(EXTRA_MESSAGE,"");
            startActivity(intent);
        }

    }

    public void hints(View view) {
        Intent intent = new Intent(this,Hints.class);

        String message = "Switch On";

        if (isSwitchOn){
            intent.putExtra(EXTRA_MESSAGE,message);
            startActivity(intent);
        }
        else{
            intent.putExtra(EXTRA_MESSAGE,"");
            startActivity(intent);
        }

    }

    public void identifyCarImage(View view) {
        Intent intent = new Intent(this,IdentifyCarImage.class);

        String message = "Switch On";

        if (isSwitchOn){
            intent.putExtra(EXTRA_MESSAGE,message);
            startActivity(intent);
        }
        else{
            intent.putExtra(EXTRA_MESSAGE,"");
            startActivity(intent);
        }

    }

    public void advancedLevel(View view) {

        Intent intent = new Intent(this,AdvancedLevel.class);
        String message = "Switch On";

        if (isSwitchOn){
            intent.putExtra(EXTRA_MESSAGE,message);
            startActivity(intent);
        }
        else{
            intent.putExtra(EXTRA_MESSAGE,"");
            startActivity(intent);
        }


    }

    public void switchStatus(View view) {

        if (isSwitchOn == true){
            isSwitchOn = false;
            mSwitchButton.setText(R.string.switch_text_off);
        }
        else {

            isSwitchOn = true;
            mSwitchButton.setText(R.string.switch_text_on);
        }
        System.out.println(isSwitchOn);
    }
}