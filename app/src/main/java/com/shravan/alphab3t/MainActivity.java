package com.shravan.alphab3t;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.shravan.alphab3t.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn_Play);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameActivity();
            }
        });
    }

    public void openGameActivity(){
        final MediaPlayer buttonSound = MediaPlayer.create(this,R.raw.button_sound);
        buttonSound.start();
        Intent intent = new Intent(this,gameActivity.class);
        if(getIntent().hasExtra("theme")){
            intent.putExtra("theme",getIntent().getStringExtra("theme"));
        }
        else{
            intent.putExtra("theme","Animals");
        }
        if(getIntent().hasExtra("difficulty")){
            intent.putExtra("difficulty",getIntent().getStringExtra("difficulty"));
        }
        else{
            intent.putExtra("difficulty","Easy");
        }
        startActivity(intent);
    }

    public void tutorialActivity(View view) {
        final MediaPlayer buttonSound = MediaPlayer.create(this,R.raw.button_sound);
        buttonSound.start();
        Intent intent = new Intent(this,tutorialActivity.class);
        if(getIntent().hasExtra("theme")){
            intent.putExtra("theme",getIntent().getStringExtra("theme"));
        }
        else{
            intent.putExtra("theme","Animals");
        }
        if(getIntent().hasExtra("difficulty")){
            intent.putExtra("difficulty",getIntent().getStringExtra("difficulty"));
        }
        else{
            intent.putExtra("difficulty","Easy");
        }
        startActivity(intent);
    }

    public void goToDifficulty(View view) {
        final MediaPlayer buttonSound = MediaPlayer.create(this,R.raw.button_sound);
        buttonSound.start();
        Intent intent = new Intent(this,difficultyActivity.class);
        if(getIntent().hasExtra("theme")){
            intent.putExtra("theme",getIntent().getStringExtra("theme"));
        }
        else{
            intent.putExtra("theme","Animals");
        }
        if(getIntent().hasExtra("difficulty")){
            intent.putExtra("difficulty",getIntent().getStringExtra("difficulty"));
        }
        else{
            intent.putExtra("difficulty","Easy");
        }
        startActivity(intent);
    }

    public void goToThemes(View view) {
        final MediaPlayer buttonSound = MediaPlayer.create(this,R.raw.button_sound);
        buttonSound.start();
        Intent intent = new Intent(this,themesActivity.class);
        if(getIntent().hasExtra("theme")){
            intent.putExtra("theme",getIntent().getStringExtra("theme"));
        }
        else{
            intent.putExtra("theme","Animals");
        }
        if(getIntent().hasExtra("difficulty")){
            intent.putExtra("difficulty",getIntent().getStringExtra("difficulty"));
        }
        else{
            intent.putExtra("difficulty","Easy");
        }
        startActivity(intent);
    }
}
