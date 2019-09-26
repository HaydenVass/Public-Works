package com.example.vasshayden_ce01.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vasshayden_ce01.MainActivity;
import com.example.vasshayden_ce01.R;
import com.example.vasshayden_ce01.fragments.DetailsFragment;

public class DetailsActivity extends AppCompatActivity implements DetailsFragment.DetailsCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent i = getIntent();
        String tag = i.getStringExtra("tag");
        DetailsFragment frag = DetailsFragment.newInstance(tag);
        getSupportFragmentManager().beginTransaction().replace(R.id.details_frag_container, frag,
                DetailsFragment.TAG).commit();

    }

    @Override
    public void removedLocation() {
        //brings back map and clears stack, flags that the new map shouldnt zoom to position
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("shouldZoom" ,"1");
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}
