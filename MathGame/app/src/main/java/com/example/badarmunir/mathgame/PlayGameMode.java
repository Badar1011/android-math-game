package com.example.badarmunir.mathgame;

import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class PlayGameMode extends AppCompatActivity {

    // name is used to store user name
    private String name;
    // mode is used to store mode selected
    private String mode;
    // difficulty level is used to store difficulty level selected
    private String difficultLevel;
    // show question is used to display question to user
    private TextView showQuestions;
    // these 4 buttons are used to show answers of the question
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;
    // this field is used to store real answer of the question
    private int realAnswer;
    // this field is used to store user score
    private int score;
    // this field is used to store lives left
    private int lives;
    // this text view is used to display lives
    private TextView showLives;
    // this text view is used to display score
    private TextView showScore1;
    // this field is used to store a reference to the vibrator
    private Vibrator vibrator1;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener sensorEventListener;
    private View root;
    // this is used to check when the user has beaten the top score
    private Boolean flag;
    // this is used to show timer
    private ProgressBar showProgressBar;


    // reference media player lost
    MediaPlayer mediaPlayerlost;
    // reference media player won
    MediaPlayer mediaPlayerwon;
    // store reference to other fragments
    ItemFragment itemFragment2;
    BlankFragment blankFragment;
    FragmentManager fragmentManager;
    // this is used to show best score
    TextView showBestScore;
    // text to speech
    TextToSpeech textToSpeech;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game_mode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // my work starts from here
        // set flag to true
        flag = true;
        root = findViewById(R.id.root);
        // this gets the light sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        // it checks if the lighsensor exists in the phone used
        if (lightSensor == null) {
            Toast.makeText(PlayGameMode.this, "The device does not have a light sensor.", Toast.LENGTH_SHORT).show();
            finish();
        }

        // it checks every time sensor is changed and then change background color according to it
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                //   getSupportActionBar().setTitle("Luminosity : " + value + " lx");

                if (value <= 7)
                {
                    root.setBackgroundColor(Color.rgb(249, 249, 249));

                }
                else if ((value > 8) && (value <= 14))
                {
                    root.setBackgroundColor(Color.rgb(175, 184, 188));
                }
                else if ((value > 15) && (value <= 25))
                {
                    root.setBackgroundColor(Color.rgb(180, 191, 198));
                }
                else if (value > 26)
                {
                    root.setBackgroundColor(Color.rgb(89, 97, 102));
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        // creates fragment objects and assign it to the fields
        itemFragment2 = new ItemFragment();
        blankFragment = new BlankFragment();
        fragmentManager = getSupportFragmentManager();


        // text to speech

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR)
                {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });


        // this get vibrator sensor
        vibrator1 = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // brings the data over from the last activity
        Bundle extras = getIntent().getExtras();

        // the getExtra function is used to retrieve any extra information passed by the user from the last activity
        // which is name, mode and difficulty level.
        if(extras != null)
        {
            name = extras.getString("name");
            mode = extras.getString("mode");
            difficultLevel = extras.getString("difficultLevel");
            //Toast.makeText(PlayMode.this, "Welcome "+name+mode+difficultLevel, Toast.LENGTH_LONG).show();
        }

        // get all the ids
        showQuestions = findViewById(R.id.showQuestion);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        showScore1 = findViewById(R.id.showScore);
        showLives = findViewById(R.id.showLivesLeft);
        showBestScore = findViewById(R.id.bestScore);
        showProgressBar = findViewById(R.id.showTime);
        // sets the best score
        showBestScore.setText("Score to beat: "+getBestScore());
        // sets the sound and assign it to the button
        mediaPlayerlost = MediaPlayer.create(this,R.raw.think);
        mediaPlayerwon = MediaPlayer.create(this,R.raw.smart);
        // updates the question
        updateQuestions(mode,difficultLevel);
        // start the timer as soon as the page loads
        countDownTimer.start();
        // user lives
        lives = 3;

        // this is an answer 1 button event listener
        // it checks that the answer shown on this button is the right one for the question
        // if it is updates the score and question and start timer again
        // if it isn't then calls onFinish function of countdowntimer
        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans = Integer.toString(realAnswer);
                if ( answer1.getText().toString().equalsIgnoreCase(ans))
                {
                    score++;
                    showScore1.setText("Score: "+ score);
                    if (score > getBestScore() && flag)
                    {
                        mediaPlayerwon.start();
                        flag = false;
                    }
                    updateQuestions(mode, difficultLevel);
                    countDownTimer.start();

                }
                else
                {
                    countDownTimer.onFinish();
                }


            }
        });

        // this is an answer 2 button event listener
        // it checks that the answer shown on this button is the right one for the question
        // if it is updates the score and question and start timer again
        // if it isn't then calls onFinish function of countdowntimer
        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans = Integer.toString(realAnswer);
                if ( answer2.getText().toString().equalsIgnoreCase(ans))
                {
                    score++;
                    showScore1.setText("Score: "+ score);
                    if (score > getBestScore() && flag)
                    {
                        mediaPlayerwon.start();
                        flag = false;
                    }
                    updateQuestions(mode, difficultLevel);
                    countDownTimer.start();
                }
                else
                {
                    countDownTimer.onFinish();
                }


            }
        });



        // this is an answer 3 button event listener
        // it checks that the answer shown on this button is the right one for the question
        // if it is updates the score and question and start timer again
        // if it isn't then calls onFinish function of countdowntimer

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans = Integer.toString(realAnswer);
                if ( answer3.getText().toString().equalsIgnoreCase(ans))
                {
                    score++;
                    showScore1.setText("Score: "+ score);
                    if (score > getBestScore() && flag)
                    {
                        mediaPlayerwon.start();
                        flag = false;
                    }
                    updateQuestions(mode, difficultLevel);
                    countDownTimer.start();
                }
                else
                {
                    countDownTimer.onFinish();
                }

            }
        });

        // this is an answer 4 button event listener
        // it checks that the answer shown on this button is the right one for the question
        // if it is updates the score and question and start timer again
        // if it isn't then calls onFinish function of countdowntimer
        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans = Integer.toString(realAnswer);
                if ( answer4.getText().toString().equalsIgnoreCase(ans))
                {
                    score++;
                    showScore1.setText("Score: "+ score);
                    if (score > getBestScore() && flag)
                    {
                        mediaPlayerwon.start();
                        flag = false;
                    }
                    updateQuestions(mode, difficultLevel);
                    countDownTimer.start();

                }
                else
                {
                    countDownTimer.onFinish();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        saveData(name,score);
        super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }



    // this function save data
    // it takes name, score and mode selected
    public void saveData(String name, int score)
    {
        // makes the name and score comma separated
        String data = name + "," + score;
        // calls to savetofile to save the data
        itemFragment2.saveToFile2(data,difficultLevel+".txt", this);
    }



    // if answer is incorrect then this function is called
    public void ifIncorrectAnswer()
    {
        lives--; // decrease live by 1
        if (Build.VERSION.SDK_INT >= 26) // check build version bigger than 26
        {
            vibrator1.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else
        {
            vibrator1.vibrate(200);
        }
        if (lives >= 0) // this updates the live counter for user to see
        {
            showLives.setText("Lives: " + lives);
        }
    }

    // count down timer class is called to count down the time left for each question
    CountDownTimer countDownTimer = new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            int secondsLeft = (int) millisUntilFinished;
            showProgressBar.setProgress(secondsLeft/100);
        }

        @Override
        public void onFinish() {
            // this function is either called when the time finishes before user selects an answer
            // or when user selects an incorrect answer
            ifIncorrectAnswer();
            // it checks if lives are 0 then calls endgame
            if (lives == 0)
            {
                endGame();
            } else if (lives > 0) {
                updateQuestions(mode, difficultLevel); // updates the question and then restart the timer
                start();
            }
        }
    };





    // this function updates the question, it requires 2 parameters mode, and difficulty level
    // and then updates the question

    public void updateQuestions(String mode, String difficultLevel)
    {
        // it sets progress bar to 100 everytime question is updated
        showProgressBar.setProgress(100);
        // calls get values to get the values of the question and save them in the local array
        int[] values = getValues(mode, difficultLevel);
        // it creates array list to store answers
        ArrayList<Integer> answers = new ArrayList<>();
        // calls get answers to get real answer for the given question
        realAnswer = getAnswer(values[0], values[1]);
        // it adds real answer to the array list
        answers.add(realAnswer);
        // then use a for loop to generate 3 fake answers and adds them to the array list as well
        for (int i = 0; i < 3; i++) {
            int fakeAnswers = getRandomNumber(-5, 5);
            while (fakeAnswers == 0) {
                fakeAnswers = getRandomNumber(-5, 5);
            }
            answers.add(realAnswer + fakeAnswers);
        }
        // shuffle the array list so answers will not show up at the same button every time
        Collections.shuffle(answers);
        // it sets the new question and answers
        String m = "";
        if (mode.equalsIgnoreCase("+"))
        {
            m = "plus";
        } else if (mode.equalsIgnoreCase("-"))
        {
            m = "minus";
        } else if (mode.equalsIgnoreCase("*"))
        {
            m = "multiplied by";
        }
        else
        {
            m = "divided by";
        }
        String q = "What is " + values[0] + " " + m + " " + values[1] + " ?";
        textToSpeech.speak(q,TextToSpeech.QUEUE_FLUSH,null);
        showQuestions.setText("What is " + values[0] + " " + mode + " " + values[1] + " ?");


        answer1.setText(answers.get(0) + "");
        answer2.setText(answers.get(1) + "");
        answer3.setText(answers.get(2) + "");
        answer4.setText(answers.get(3) + "");
    }


    public void onPause1()
    {
        if (textToSpeech!=null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }



    // this gets the answer of the question selected.
    // it takes in 2 parameters value 1 and 2
    // depending on the mode it performs the operation on the values
    public int getAnswer(int value1, int value2)
    {
        if (mode.equalsIgnoreCase("+"))
        {
            realAnswer = value1+value2;
        }
        else if (mode.equalsIgnoreCase("-"))
        {
            realAnswer = value1-value2;
        }
        else if (mode.equalsIgnoreCase("/"))
        {
            realAnswer = value1/value2;
        }
        else if (mode.equalsIgnoreCase("*"))
        {
            realAnswer = value1*value2;
        }
        return realAnswer;
    }






    // this function is called when the game is finished i.e. when user has lost all of his/her lives
    public void endGame()
    {
        countDownTimer.cancel();
        if (flag) // it checks if flag is true then play a song otherwise dont // flag is true when player didnt beat top score
        {
            mediaPlayerlost.start();
        }
        saveData(name,score); // saves data in the file
        // shows an alert dialog with a button
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PlayGameMode.this);
        alertDialog.setMessage("Game Over!, your score is "+ score).setCancelable(false).
                setPositiveButton("Select another mode", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
    }


    // this function is called to get the next values for the question, it takes the mode and difficulty level to do this.
    // it takes 2 parameter the mode and the difficulty level and then generates values depending on that
    public int[] getValues(String mode, String difficultLevel)
    {
        int[] values = new int[2];
        if (mode.equalsIgnoreCase("+"))
        {
            if (difficultLevel.equalsIgnoreCase("easy"))
            {
                // if easy the values are between 1 and 100
                values[0] = getRandomNumber(1,100);
                values[1] = getRandomNumber(1,100);
                return values;
            }
            else if (difficultLevel.equalsIgnoreCase("medium"))
            {
                // if medium the values are between 101 and 500
                values[0] =  getRandomNumber(101,500);
                values[1] =  getRandomNumber(101,500);
                return values;
            }
            else if (difficultLevel.equalsIgnoreCase("hard"))
            {
                // if hard the values are between 501 and 1000
                values[0] =  getRandomNumber(501,1000);
                values[1] =  getRandomNumber(501,1000);
                return values;
            }
        }
        else if (mode.equalsIgnoreCase("-"))
        {
            if (difficultLevel.equalsIgnoreCase("easy"))
            {
                values[0] =  getRandomNumber(1,100);
                values[1] =  getRandomNumber(1,100);
                return values;
            }
            else if (difficultLevel.equalsIgnoreCase("medium"))
            {
                values[0] =  getRandomNumber(101,500);
                values[1] =  getRandomNumber(101,500);
                return values;
            }
            else if (difficultLevel.equalsIgnoreCase("hard"))
            {
                values[0] =  getRandomNumber(501,1000);
                values[1] =  getRandomNumber(501,1000);
                return values;
            }
        }
        else if (mode.equalsIgnoreCase("*"))
        {
            if (difficultLevel.equalsIgnoreCase("easy"))
            {
                values[0] =  getRandomNumber(1,100);
                values[1] =  getRandomNumber(1,100);
                return values;
            }
            else if (difficultLevel.equalsIgnoreCase("medium"))
            {
                values[0] =  getRandomNumber(101,500);
                values[1] =  getRandomNumber(101,500);
                return values;
            }
            else if (difficultLevel.equalsIgnoreCase("hard"))
            {
                values[0] =  getRandomNumber(501,1000);
                values[1] =  getRandomNumber(501,1000);
                return values;
            }
        }
        else if (mode.equalsIgnoreCase("/"))
        {
            if (difficultLevel.equalsIgnoreCase("easy"))
            {
                values[0] =  getRandomNumber(1,100);
                values[1] =  getRandomNumber(1,100);
                return values;
            }
            else if (difficultLevel.equalsIgnoreCase("medium"))
            {
                values[0] =  getRandomNumber(101,500);
                values[1] =  getRandomNumber(101,500);
                return values;
            }
            else if (difficultLevel.equalsIgnoreCase("hard"))
            {
                values[0] =  getRandomNumber(501,1000);
                values[1] =  getRandomNumber(501,1000);
                return values;
            }
        }
        return values;
    }


    // this function is called to generate random number between a specified limit.
    public int getRandomNumber(int minimumValue,int maximumValue)
    {
        Random random = new Random();
        return random.nextInt((maximumValue - minimumValue) + 1) + minimumValue;
    }


    // this function gets the best score depending on the difficulty level selected
    // then it reads the data from the file and calls gethighestscore function that returns high score
    public int getBestScore()
    {
        int score = 0;
        if (difficultLevel.equalsIgnoreCase("easy"))
        {
            itemFragment2.readFromFile("easy.txt",this);
            score = itemFragment2.getHighestScore();
        }
        else if (difficultLevel.equalsIgnoreCase("medium"))
        {
            itemFragment2.readFromFile("medium.txt",this);
            score = itemFragment2.getHighestScore();
        }
        else if (difficultLevel.equalsIgnoreCase("hard"))
        {
            itemFragment2.readFromFile("hard.txt",this);
            score = itemFragment2.getHighestScore();
        }
        return score;
    }





}
