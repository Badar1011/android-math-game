package com.example.badarmunir.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class GameMode extends AppCompatActivity {


    // these fields are used to store reference to image buttons
    private ImageButton plusModeButton;
    private ImageButton minusModeButton;
    private ImageButton multiplyModeButton;
    private ImageButton divideModeButton;
    // used to store user's name
    private String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);









        // the getExtra function is used to retrieve any extra information passed by the user from the last activity
        // which is name
        Bundle extras = getIntent().getExtras();
        // it checks if extras not null then it passes the key to get the values
        if(extras!=null)
        {
            name = extras.getString("name");
            // this toast is used for testing purposes
            Toast.makeText(GameMode.this, "Welcome "+name, Toast.LENGTH_LONG).show();
        }


        // here we are getting the ids of the image buttons and assigning them to the fields
        plusModeButton = findViewById(R.id.plusButton);
        minusModeButton = findViewById(R.id.minusButton);
        multiplyModeButton = findViewById(R.id.multiplyButton);
        divideModeButton = findViewById(R.id.divideButton);




        // click listener used to check which button is select.
        // then it start the next activity and pass the information forward
        plusModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectDifficultyPage = new Intent(GameMode.this,SelectDifficultyLevel.class);
                String mode = "+";
                selectDifficultyPage.putExtra("name", name);
                selectDifficultyPage.putExtra( "mode",mode);
                startActivity(selectDifficultyPage);
                // overrides the transition
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });




        // click listener used to check which button is select.
        // then it start the next activity and pass the information forward
        minusModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectDifficultyPage = new Intent(GameMode.this,SelectDifficultyLevel.class);
                String mode = "-";
                selectDifficultyPage.putExtra("name", name);
                selectDifficultyPage.putExtra( "mode",mode);
                startActivity(selectDifficultyPage);
                // overrides the transition
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });




        // click listener used to check which button is select.
        // then it start the next activity and pass the information forward
        multiplyModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectDifficultyPage = new Intent(GameMode.this,SelectDifficultyLevel.class);
                String mode = "*";
                selectDifficultyPage.putExtra("name", name);
                selectDifficultyPage.putExtra( "mode",mode);
                startActivity(selectDifficultyPage);
                // overrides the transition
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });




        // click listener used to check which button is select.
        // then it start the next activity and pass the information forward
        divideModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectDifficultyPage = new Intent(GameMode.this,SelectDifficultyLevel.class);
                String mode = "/";
                selectDifficultyPage.putExtra("name", name);
                selectDifficultyPage.putExtra( "mode",mode);
                startActivity(selectDifficultyPage);
                // overrides the transition
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });




    }

}
