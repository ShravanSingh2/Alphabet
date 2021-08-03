package com.shravan.alphab3t;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.shravan.alphab3t.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class highScoresActivity extends AppCompatActivity {

    ArrayList<String> highScores = new ArrayList<>();
    ArrayList<String> difficulty = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_high_scores);
        TextView textView9 = findViewById(R.id.textView9);
        TextView textView10 = findViewById(R.id.textView10);
        textView10.setMovementMethod(new ScrollingMovementMethod());

        try {
            loadScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String score = "";
        for(int i=highScores.size()-1;i>0;i--){
            score = score +"Score : " + highScores.get(i) + ", Difficulty : " + difficulty.get(i)+"\n";
        }
        textView10.setText(score);
        score="";
        String temp = "";
        for(int i=0;i<highScores.size();i++){
            for(int j=0;j<highScores.size();j++){
                if(Integer.parseInt(highScores.get(i))>Integer.parseInt(highScores.get(j))){
                    temp = highScores.get(i);
                    highScores.set(i,highScores.get(j));
                    highScores.set(j,temp);
                    temp = difficulty.get(i);
                    difficulty.set(i,difficulty.get(j));
                    difficulty.set(j,temp);
                }
            }
        }
        if(highScores.size()>0){
            score = score + "1st : " + highScores.get(0) + ", Difficulty : " + difficulty.get(0) + "\n";
            if(highScores.size()>1){
                score = score + "2nd : " + highScores.get(1) +", Difficulty : " + difficulty.get(1) + "\n";
                if(highScores.size()>2){
                    score = score + "3rd : " + highScores.get(2) +", Difficulty : " + difficulty.get(2) + "\n";
                }
            }
        }
        textView9.setText(score);
    }

    public void loadScores() throws IOException {
        FileInputStream fis = null;
        fis = openFileInput("test1.txt");
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String score = br.readLine();

        while(score!=null){
            highScores.add(score);
            difficulty.add(br.readLine());
            score = br.readLine();
        }
        if(fis!=null){
            fis.close();
        }
    }

    public void goHome(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        final MediaPlayer buttonSound = MediaPlayer.create(this,R.raw.button_sound);
        buttonSound.start();
    }
}
