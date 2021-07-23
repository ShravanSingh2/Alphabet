package com.example.alphab3t;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class tutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tutorial);
        TextView instructions = findViewById(R.id.textView7);
        instructions.setText("Welcome to Alphabet\n" +
                "\n" +
                "1. The aim of the game is to deciper words where each character of the word has been shifted up in the alphabet.\n" +
                "\n" +
                "2. The modifier number states how many letters above the original word is.\n" +
                "\n" +
                "3. If you reach the end of the alphabet. You just have to loop back to the front\n" +
                "\n" +
                "Example : Your Word is : cnf\n" +
                "Modifier : 1\n" +
                "\n" +
                "The correct word is dog" +
                "\n" +
                "You have to balance your time and risk in this game. Make an incorrect guess and you come one step closer to losing and may have lost an opportunity on another question. However, make the right guess and you do not have to decipher the rest of the word. It is upto you to balance your risks. You chose");
    }

    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("theme",getIntent().getStringExtra("theme"));
        intent.putExtra("difficulty",getIntent().getStringExtra("difficulty"));
        startActivity(intent);
    }
}
