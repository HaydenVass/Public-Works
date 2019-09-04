package com.example.aid_day7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //get starter intent
        Intent starter = getIntent();
        if(starter != null){
            // uses exgtra some text key to retireve some text from starter intent extras
            String stringSomeText = starter.getStringExtra(MainActivity.EXTRA_SOME_TEXT);

            //display passed text
            TextView textViewSXomeText = findViewById(R.id.textViewSomeText);
            textViewSXomeText.setText(stringSomeText);
        }
    }
}
