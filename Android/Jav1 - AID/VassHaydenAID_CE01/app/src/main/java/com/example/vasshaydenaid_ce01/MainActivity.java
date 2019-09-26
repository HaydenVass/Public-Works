//Hayden Vass
// AID 1904
//CE01

package com.example.vasshaydenaid_ce01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private View constraintView;
    private View linearView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintView = findViewById(R.id.constraintView);
        linearView = findViewById(R.id.linearView);

        constraintView.setVisibility(View.GONE);
        findViewById(R.id.linearButton).setOnClickListener(linearBtnPresed);
        findViewById(R.id.constraintButton).setOnClickListener(constraintBtnPressed);
    }

    private final View.OnClickListener linearBtnPresed = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            constraintView.setVisibility(View.VISIBLE);
            linearView.setVisibility(View.GONE);
        }
    };


    private final View.OnClickListener constraintBtnPressed = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            linearView.setVisibility(View.VISIBLE);
            constraintView.setVisibility(View.GONE);

        }
    };
}
