package com.example.vasshayden_ce04;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vasshayden_ce04.fragments.ImageGridFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            ImageGridFragment frag = ImageGridFragment.newInstance();
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container, frag).
                    commit();
        }

    }

}