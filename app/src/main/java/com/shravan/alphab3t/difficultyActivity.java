package com.shravan.alphab3t;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.shravan.alphab3t.R;

public class difficultyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_difficulty);

        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.difficulty,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(adapter.getPosition(getIntent().getStringExtra("difficulty")));
    }

    public void onBackPressed() {
        Spinner spinner = findViewById(R.id.spinner2);
        String difficulty = spinner.getSelectedItem().toString();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("difficulty",difficulty);
        intent.putExtra("theme",getIntent().getStringExtra("theme"));
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView modifierBound = findViewById(R.id.textView15);
        TextView timeAllocated = findViewById(R.id.textView13);
        TextView maxWordSize = findViewById(R.id.textView14);
        TextView numChances = findViewById(R.id.textView4);
        String difficulty = parent.getItemAtPosition(position).toString();
        if(difficulty.equals("Easy")){
            timeAllocated.setText("Time Allocated : 60s");
            maxWordSize.setText("Maximum length of word : 4 letters");
            numChances.setText("Number of incorrect chances given : 6");
            modifierBound.setText("Maximum Modifier : 3");

        }
        if(difficulty.equals("Medium")){
            timeAllocated.setText("Time Allocated : 50s");
            maxWordSize.setText("Maximum length of word : 6 letters");
            numChances.setText("Number of incorrect chances given : 4");
            modifierBound.setText("Maximum Modifier : 6");
        }
        if(difficulty.equals("Hard")){
            timeAllocated.setText("Time Allocated : 40s");
            maxWordSize.setText("Maximum length of word : Any length");
            numChances.setText("Number of incorrect chances given : 2");
            modifierBound.setText("Maximum Modifier : 10");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
