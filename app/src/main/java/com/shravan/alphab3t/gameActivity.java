package com.shravan.alphab3t;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shravan.alphab3t.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class gameActivity extends AppCompatActivity {

    public ArrayList<String> original_data = new ArrayList<String>();
    public ArrayList<String> wordCorrect = new ArrayList<String>();
    public ArrayList<String> generatedWords = new ArrayList<String>();

    int modifier;
    String randomWord,theme,difficulty;
    int time,incorrectTries,lengthWord,modifierBound;
    CountDownTimer Timer;

    final Handler handler = new Handler();
    Runnable counter = null;

    Boolean Myboolean = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        TextView test = findViewById(R.id.textView);
        EditText inputField = findViewById(R.id.editText);
        inputField.setVisibility(View.INVISIBLE);
        Button button = findViewById(R.id.button);
        button.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        inputField.requestFocus();
        theme = intent.getStringExtra("theme");
        difficulty = intent.getStringExtra("difficulty");
        if(difficulty.equals("Easy")){
            time = 60000;
            incorrectTries = 6;
            lengthWord = 4;
            modifierBound = 3;
        }
        if(difficulty.equals("Medium")){
            time = 50000;
            incorrectTries = 4;
            lengthWord = 6;
            modifierBound = 6;
        }
        if(difficulty.equals("Hard")){
            time = 40000;
            incorrectTries = 2;
            modifierBound = 10;
        }
        ReadDataIn(theme);
        StartTimer();
    }

    public void onBackPressed() {
        if(Timer!=null) {
            Timer.cancel();
            Myboolean = false;
        }
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("theme",getIntent().getStringExtra("theme"));
        intent.putExtra("difficulty",getIntent().getStringExtra("difficulty"));
        startActivity(intent);
    }

    public void StartAnimation(){
        Animation blink = AnimationUtils.loadAnimation(this,R.anim.blink);
        Animation slidedown = AnimationUtils.loadAnimation(this,R.anim.slidedown);
        Animation bounce = AnimationUtils.loadAnimation(this,R.anim.bounce);
        TextView timerField = findViewById(R.id.textView5);
        TextView wordDisplay = findViewById(R.id.textView);
        TextView modifierDisplay = findViewById(R.id.textView3);
        TextView attemptsRemaining = findViewById(R.id.textView17);
        timerField.startAnimation(blink);
        wordDisplay.startAnimation(slidedown);
        modifierDisplay.startAnimation(bounce);
        attemptsRemaining.startAnimation(slidedown);
    }

    public void StartTimer(){
        final TextView timerField = findViewById(R.id.textView5);
        final Animation blink = AnimationUtils.loadAnimation(this,R.anim.blink);
        final Button button = findViewById(R.id.button);
        final java.util.concurrent.atomic.AtomicInteger n = new AtomicInteger(3);
        counter = new Runnable() {
            @Override
            public void run() {

                timerField.setText(Integer.toString(n.get()));
                if(n.getAndDecrement() >= 1 )
                    handler.postDelayed(this, 1000);
                else {
                    startCountDown();

                    button.startAnimation(blink);
                    timerField.setTextSize(28);
                    StartGame();
                }
            }
        };
        handler.postDelayed(counter, 750);
    }

    public void ReadDataIn(String theme){
        Scanner data_in=null;
        if(theme.equals("Animals")) {
            data_in = new Scanner(getResources().openRawResource(R.raw.animals));
        }
        if(theme.equals("Locations")) {
            data_in = new Scanner(getResources().openRawResource(R.raw.locations));
        }
        if(theme.equals("Fruits")) {
            data_in = new Scanner(getResources().openRawResource(R.raw.fruits));
        }
        if(theme.equals("Random")) {
            data_in = new Scanner(getResources().openRawResource(R.raw.random));
        }
        while(data_in.hasNext()){
            original_data.add(data_in.nextLine());
        }
    }

    public void startCountDown(){
        final TextView timerField = findViewById(R.id.textView5);
        final TextView wordField = findViewById(R.id.textView);
        final EditText field = findViewById(R.id.editText);
        final MediaPlayer endSound = MediaPlayer.create(this,R.raw.endgame_sound);
        Timer = new CountDownTimer(time, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                timerField.setText("Time remaining: " + millisUntilFinished / 1000);
                if(!Myboolean ) { cancel(); }
            }

            @SuppressLint("SetTextI18n")
            public void onFinish() {
                timerField.setText("Times Up!");
                wordField.setText("Your word was " + randomWord);
                endSound.start();
                field.setText("");
                closeKeyboard();
                openEndActivity();
            }
        }.start();
    }

    public void openEndActivity(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(gameActivity.this, endActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ARRAYLIST",(Serializable)wordCorrect);
                intent.putExtras(bundle);
                intent.putExtra("theme",getIntent().getStringExtra("theme"));
                intent.putExtra("difficulty",getIntent().getStringExtra("difficulty"));
                startActivity(intent);
            }
        }, 1500);
    }

    @SuppressLint("SetTextI18n")
    public void StartGame(){
        EditText inputField = findViewById(R.id.editText);
        inputField.setVisibility(View.VISIBLE);
        StartAnimation();
        Button button = findViewById(R.id.button);
        button.setVisibility(View.VISIBLE);
        TextView wordDisplay = findViewById(R.id.textView);
        TextView modifierDisplay = findViewById(R.id.textView3);
        TextView attempts = findViewById(R.id.textView17);
        attempts.setText("Incorrect Attempts : " + incorrectTries);
        randomWord = getWord();
        Random rand = new Random();
        modifier = rand.nextInt(modifierBound);
            while (modifier==0) {
                modifier = rand.nextInt(modifierBound);
            }
        String encryptedWord = encryptWord(randomWord,modifier);
        randomWord = randomWord.toLowerCase();

        wordDisplay.setText("Your word is " + encryptedWord);
        modifierDisplay.setText("Modifier : " + modifier);
    }

    public String getWord(){
        int sizeList = original_data.size();
        Random rand = new Random();
        randomWord = original_data.get(rand.nextInt(sizeList));
        if(!difficulty.equals("Hard")) {
            while (randomWord.length() > lengthWord) {
                randomWord = original_data.get(rand.nextInt(sizeList));
            }
        }
        generatedWords.add(randomWord);
        int instances=0;
        for(int i=0;i<generatedWords.size();i++){
            if(generatedWords.get(i).equals(randomWord)){
                instances++;
            }
        }
        while(instances>1){
            randomWord = original_data.get(rand.nextInt(sizeList));
            if(!difficulty.equals("Hard")) {
                while (randomWord.length() > lengthWord) {
                    randomWord = original_data.get(rand.nextInt(sizeList));
                }
            }
            generatedWords.add(randomWord);
            instances=0;
            for(int i=0;i<generatedWords.size();i++){
                if(generatedWords.get(i).equals(randomWord)){
                    instances++;
                }
            }
        }
        return randomWord;
    }

    public String encryptWord(String word, int modifier){
        String result = "";
        for(int i=0;i<word.length();i++){
            int iNum = (int)(word.charAt(i)) - modifier;
            if(i==0){iNum=iNum+32;}
            if(iNum<97){
                iNum = iNum + 26;
            }
            String currChar = Character.toString ((char) iNum);
            result = result + currChar;
        }
        return result;
    }

    public void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view!= null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    public void EnterWord(View view) {
        final MediaPlayer correctSound = MediaPlayer.create(this,R.raw.correct_sound);
        final Handler handler = new Handler();
        final EditText inputField = findViewById(R.id.editText);
        final TextView wordDisplay = findViewById(R.id.textView);
        final TextView status = findViewById(R.id.textView17);
        String decryptedWord = inputField.getText().toString();
        decryptedWord = decryptedWord.toLowerCase();
        if(decryptedWord.equals(randomWord)){
            wordDisplay.setText("");
            correctSound.start();
            nextWord();
        }else{
            if(incorrectTries==0){
                status.setTextColor(Color.parseColor("#FF0000"));
                final MediaPlayer endSound = MediaPlayer.create(this,R.raw.endgame_sound);
                endSound.start();
                status.setText("Game over!");
                closeKeyboard();
                Timer.cancel();
                wordDisplay.setText("Your word was " + randomWord);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(gameActivity.this, endActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ARRAYLIST",(Serializable)wordCorrect);
                        intent.putExtras(bundle);
                        intent.putExtra("theme",getIntent().getStringExtra("theme"));
                        intent.putExtra("difficulty",getIntent().getStringExtra("difficulty"));
                        startActivity(intent);
                    }
                }, 1500);
            }else {
                final MediaPlayer incorrectSound = MediaPlayer.create(this,R.raw.incorrect_sound);
                incorrectSound.start();
                status.setText("Incorrect Word!");
                handler.postDelayed(new Runnable() {
                    public void run() {
                        status.setText("Incorrect Attempts : " + incorrectTries);
                    }
                }, 1000);
                incorrectTries = incorrectTries - 1;
                if (incorrectTries == 0) {
                    status.setTextColor(Color.parseColor("#FF0000"));
                    status.setText("Incorrect Attempts : 0");
                } else {
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void nextWord(){
        EditText inputField = findViewById(R.id.editText);
        TextView wordDisplay = findViewById(R.id.textView);
        TextView modifierDisplay = findViewById(R.id.textView3);
        modifierDisplay.setText("");
        inputField.setText("");
        wordDisplay.setText("Correct Word!");
        inputField.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(inputField, InputMethodManager.SHOW_IMPLICIT);
        wordCorrect.add(randomWord);

        StartGame();
    }
}
