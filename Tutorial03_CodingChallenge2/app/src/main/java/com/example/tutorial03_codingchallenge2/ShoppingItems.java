package com.example.tutorial03_codingchallenge2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShoppingItems extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.tutorial03_codingchallange2.extra.REPLY";
    private Button mShoppingItemButton;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_items);
    }

    public void getShoppingItem(View view) {

        Intent intent;

        switch (view.getId()){
            case R.id.noodles_button:
                mShoppingItemButton = findViewById(R.id.noodles_button);
                message = (String) mShoppingItemButton.getText();

                System.out.println(message);
                intent = new Intent();
                intent.putExtra(EXTRA_REPLY,message);
                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.apple_button:
                mShoppingItemButton = findViewById(R.id.apple_button);
                message = (String) mShoppingItemButton.getText();

                intent = new Intent();
                intent.putExtra(EXTRA_REPLY,message);
                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.biscuits_button:
                mShoppingItemButton = findViewById(R.id.biscuits_button);
                message = (String) mShoppingItemButton.getText();

                intent = new Intent();
                intent.putExtra(EXTRA_REPLY,message);
                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.cereal_button:
                mShoppingItemButton = findViewById(R.id.cereal_button);
                message = (String) mShoppingItemButton.getText();

                intent = new Intent();
                intent.putExtra(EXTRA_REPLY,message);
                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.cheese_button:
                mShoppingItemButton = findViewById(R.id.cheese_button);
                message = (String) mShoppingItemButton.getText();

                System.out.println(message);
                intent = new Intent();
                intent.putExtra(EXTRA_REPLY,message);
                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.chocolate_button:
                mShoppingItemButton = findViewById(R.id.chocolate_button);
                message = (String) mShoppingItemButton.getText();

                intent = new Intent();
                intent.putExtra(EXTRA_REPLY,message);
                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.icecream_button:
                mShoppingItemButton = findViewById(R.id.icecream_button);
                message = (String) mShoppingItemButton.getText();

                intent = new Intent();
                intent.putExtra(EXTRA_REPLY,message);
                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.juice_button:
                mShoppingItemButton = findViewById(R.id.juice_button);
                message = (String) mShoppingItemButton.getText();

                intent = new Intent();
                intent.putExtra(EXTRA_REPLY,message);
                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.milk_button:
                mShoppingItemButton = findViewById(R.id.milk_button);
                message = (String) mShoppingItemButton.getText();

                intent = new Intent();
                intent.putExtra(EXTRA_REPLY,message);
                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.onion_button:
                mShoppingItemButton = findViewById(R.id.onion_button);
                message = (String) mShoppingItemButton.getText();

                intent = new Intent();
                intent.putExtra(EXTRA_REPLY,message);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }

    }
}