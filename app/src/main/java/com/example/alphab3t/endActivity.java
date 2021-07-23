package com.example.alphab3t;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static java.lang.String.*;

public class endActivity extends AppCompatActivity {

    ArrayList<String> wordCorrect = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_end);

        Bundle bundleObject = getIntent().getExtras();
        wordCorrect = (ArrayList<String>) bundleObject.getSerializable("ARRAYLIST");

        Animation slidedown = AnimationUtils.loadAnimation(this,R.anim.slidedown);
        Animation bounce = AnimationUtils.loadAnimation(this,R.anim.bounce);
        TextView display = findViewById(R.id.textView6);
        TextView words = findViewById(R.id.textView2);
        display.startAnimation(slidedown);
        words.startAnimation(bounce);
        display.setText(format("Words Correct : %d", wordCorrect.size()));
        try {
            saveScore(wordCorrect.size());
        } catch (IOException e) {
            e.printStackTrace();
            display.setText("vfd");
        }
        String output = "";
        for(int i=0;i<wordCorrect.size();i++){
            String newLine = System.getProperty("line.separator");
            output = output + wordCorrect.get(i) + newLine;
        }
        words.setText(output);

    }

    public void onBackPressed(){

    }

    public void saveScore(int score) throws IOException {
        String sc = Integer.toString(score) + "\n";
        String difficulty = getIntent().getStringExtra("difficulty") + "\n";
        TextView words = findViewById(R.id.textView6);
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("test1.txt",MODE_APPEND);
            fos.write(sc.getBytes());
            fos.write(difficulty.getBytes());
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            words.setText("l");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos!=null){
                fos.close();
            }
        }
    }

    public void PlayAgain(View view) {
        final MediaPlayer buttonSound = MediaPlayer.create(this,R.raw.button_sound);
        buttonSound.start();
        Intent intent = new Intent(this,gameActivity.class);
        intent.putExtra("theme",getIntent().getStringExtra("theme"));
        intent.putExtra("difficulty",getIntent().getStringExtra("difficulty"));
        startActivity(intent);
    }

    public void goHome(View view) {
        final MediaPlayer buttonSound = MediaPlayer.create(this,R.raw.button_sound);
        buttonSound.start();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("theme",getIntent().getStringExtra("theme"));
        intent.putExtra("difficulty",getIntent().getStringExtra("difficulty"));
        startActivity(intent);
    }

    public void goToHighScores(View view) {
        final MediaPlayer buttonSound = MediaPlayer.create(this,R.raw.button_sound);
        buttonSound.start();
        Intent intent = new Intent(this,highScoresActivity.class);
        intent.putExtra("theme",getIntent().getStringExtra("theme"));
        intent.putExtra("difficulty",getIntent().getStringExtra("difficulty"));
        startActivity(intent);
    }
}
