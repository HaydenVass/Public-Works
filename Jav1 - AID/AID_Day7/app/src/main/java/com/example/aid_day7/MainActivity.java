package com.example.aid_day7;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Context c;
    private static final int REQUEST_SOME_TEXT = 0;
    public static final String  EXTRA_SOME_TEXT = "EXTRA_SOME_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = this;

        Button buttonSomeText = findViewById(R.id.button_submitsometext);
        Button implicitButton = findViewById(R.id.buttonImplicitIntent);

        implicitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewID = v.getId();
                EditText editTextSomeText = findViewById(R.id.editText_sometext);
                String stringSomeText = editTextSomeText.getText().toString();

                //crteate a new Implicit intent to open "something"
                Intent implicitIntent = new Intent(Intent.ACTION_SEND);


                // attach data
                implicitIntent.putExtra(Intent.EXTRA_TEXT, stringSomeText);
                //set data type
                implicitIntent.setType("text/plain");

                // start intent
                //startActivity(implicitIntent);
                startActivityForResult(implicitIntent, REQUEST_SOME_TEXT);
            }
        });


        //explicit

        buttonSomeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int viewID = v.getId();
                EditText editTextSomeText = findViewById(R.id.editText_sometext);
                String stringSomeText = editTextSomeText.getText().toString();

                //creates intent to open other activity
                Intent detailsIntent = new Intent(c, DetailActivity.class);

                //passes data
                detailsIntent.putExtra(EXTRA_SOME_TEXT, stringSomeText);

                //start activity
                startActivity(detailsIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_SOME_TEXT){
            if(resultCode == RESULT_OK){
                Intent returnIntent = data;

                if(returnIntent != null){
                    String returnText = data.getStringExtra(Intent.EXTRA_TEXT);
                    Toast.makeText(c, returnText, Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
}
