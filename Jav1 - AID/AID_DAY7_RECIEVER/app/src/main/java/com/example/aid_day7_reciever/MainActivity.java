package com.example.aid_day7_reciever;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "today";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assign view variables
        final EditText editTextNewText = findViewById(R.id.editTextNewText);
        Button buttonReturnText = findViewById(R.id.buttonReturnText);


        // get starter text
        Intent starter = getIntent();
        if(starter != null){
            // get string extra from implicit intents
            Log.i(TAG, "onCreate: ");
            String starterText = starter.getStringExtra(Intent.EXTRA_TEXT);
            // show shared text
            editTextNewText.setText(starterText);
        }

        buttonReturnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = editTextNewText.getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra(Intent.EXTRA_TEXT, newText);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
