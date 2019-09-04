package com.example.aid_final_day;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aid_final_day.fragments.FormFragment;
import com.example.aid_final_day.objects.AndroidVersion;

import java.text.Normalizer;

public class FormActivity extends AppCompatActivity implements FormFragment.AndroidVersionListner {

    public static final String EXTRA_VERSION = "EXTRA_VERSION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        getSupportFragmentManager().beginTransaction().add
                (R.id.formFragmentContainer, FormFragment.newInstance()).commit();
    }

    @Override
    public void addAndroidVersion(AndroidVersion version) {
        // create empty intent and attach version as a serializable exgra
        Intent returnVersionIntent = new Intent();
        returnVersionIntent.putExtra(EXTRA_VERSION, version);

        //set retult and intent
        setResult(RESULT_OK, returnVersionIntent);

        //finish activity
        finish();
    }
}
