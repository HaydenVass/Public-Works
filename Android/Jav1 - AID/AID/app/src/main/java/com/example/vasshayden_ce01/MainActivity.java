//Hayden Vass
// AID - 1904
// CE01
package com.example.vasshayden_ce01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private View linearLayout;
    private View constraintsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.linearLayout2);
        //linearLayout.setVisibility(View.GONE);

        constraintsLayout = findViewById(R.id.constraintLayout);
        //constraintsLayout.setVisibility(View.GONE);

        //findViewById(R.id.button1).setOnClickListener(showConstraintsView);

    }







}
