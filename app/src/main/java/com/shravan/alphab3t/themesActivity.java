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

public class themesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_themes);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Themes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(adapter.getPosition(getIntent().getStringExtra("theme")));
    }

    public void onBackPressed() {
        Spinner spinner = findViewById(R.id.spinner);
        String themeItem = spinner.getSelectedItem().toString();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("theme",themeItem);
        intent.putExtra("difficulty",getIntent().getStringExtra("difficulty"));
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView themeDescrption = findViewById(R.id.textView8);
        String text = parent.getItemAtPosition(position).toString();
        if(text.equals("Animals")){
            themeDescrption.setText("Chose from a wide range of animals. Creatures that roam the earth. Big or small. See if you can guess them all in time.");
        }
        if(text.equals("Fruits")){
            themeDescrption.setText("Chose from a variety of fruits. Light, dark, smooth skinned or hard. Test your knowledge of the most common fruits. See if you can guess them all in time.");
        }
        if(text.equals("Locations")){
            themeDescrption.setText("These are the worlds most popular locations. Places that some of us dream to go to one day. Places that we may have been to already. Do you know your Geography?");
        }
        if(text.equals("Random")){
            themeDescrption.setText("Daring enough to have no guidance between the three themes? Looking for a challenge to guess words based on no context? This is a random mix of Animals, Locations and Fruits. Good luck!");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
