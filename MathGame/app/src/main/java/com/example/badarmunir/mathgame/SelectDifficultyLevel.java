package com.example.badarmunir.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class SelectDifficultyLevel extends AppCompatActivity {


    // this field is used to store the users name
    private String name;
    // this field is used to store the mode selected
    private String mode;
    // these are image button fields that are going to be used to store reference to the image
    private ImageButton difficultyEasyButton;
    private ImageButton difficultyMediumButton;
    private ImageButton difficultyHardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_difficulty_level);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        // the getExtra function is used to retrieve any extra information passed by the user from the last activity
        // which is name and mode selected
        Bundle extras = getIntent().getExtras();
        // it checks if extras not null then it passes the key to get the values
        if(extras!=null)
        {
            name = extras.getString("name");
            mode = extras.getString("mode");
            // this toast is used for testing purposes
            //Toast.makeText(DifficultyMode.this, "Welcome "+name+mode, Toast.LENGTH_LONG).show();
        }


        // here we are getting the ids of the image buttons and assigning them to the fields
        difficultyEasyButton = findViewById(R.id.easyButton);
        difficultyMediumButton = findViewById(R.id.mediumButton);
        difficultyHardButton = findViewById(R.id.hardButton);

        // click listener used to check which button is select.
        // then it start the next activity and pass the information forward
        // then it finish this activity as user should select level of difficulty every time he selects a mode
        difficultyEasyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectPlayModePage = new Intent(SelectDifficultyLevel.this,PlayGameMode.class);
                String difficultLevel = "easy";
                selectPlayModePage.putExtra("name", name);
                selectPlayModePage.putExtra( "mode",mode);
                selectPlayModePage.putExtra( "difficultLevel",difficultLevel);

                startActivity(selectPlayModePage);

                // overrides the transition
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });


        // click listener used to check which button is select.
        // then it start the next activity and pass the information forward
        // then it finish this activity as user should select level of difficulty every time he selects a mode
        difficultyMediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectPlayModePage = new Intent(SelectDifficultyLevel.this,PlayGameMode.class);
                String difficultLevel = "medium";
                selectPlayModePage.putExtra("name", name);
                selectPlayModePage.putExtra( "mode",mode);
                selectPlayModePage.putExtra( "difficultLevel",difficultLevel);
                startActivity(selectPlayModePage);

                // overrides the transition
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });

        // click listener used to check which button is select.
        // then it start the next activity and pass the information forward
        // then it finish this activity as user should select level of difficulty every time he selects a mode
        difficultyHardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectPlayModePage = new Intent(SelectDifficultyLevel.this,PlayGameMode.class);
                String difficultLevel = "hard";
                selectPlayModePage.putExtra("name", name);
                selectPlayModePage.putExtra( "mode",mode);
                selectPlayModePage.putExtra( "difficultLevel",difficultLevel);
                startActivity(selectPlayModePage);
                // overrides the transition
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });



    }

}
