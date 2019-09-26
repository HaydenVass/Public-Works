package com.example.aid_final_day.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.aid_final_day.FormActivity;
import com.example.aid_final_day.R;
import com.example.aid_final_day.fragments.MainFragment;
import com.example.aid_final_day.objects.AndroidVersion;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  MainFragment.VersionClickedListener{

    private static final int ANROID_VERSION_REQUEST = 1;

    private ArrayList<AndroidVersion> versions;
    private Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        versions = new ArrayList<>();
        c = this;

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainFragmentContainer, MainFragment.newInstance(versions)).commit();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formIntent = new Intent(c, FormActivity.class);
                startActivityForResult(formIntent, ANROID_VERSION_REQUEST);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ANROID_VERSION_REQUEST){
            if(resultCode == RESULT_OK){
                AndroidVersion version = (AndroidVersion) data.getSerializableExtra(FormActivity.EXTRA_VERSION);

                versions.add(version);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainFragmentContainer, MainFragment.newInstance(versions)).commit();
            }
        }
    }

    @Override
    public void toastVersion(View v, int position) {
        AndroidVersion version = versions.get(position);

        Snackbar.make(v, version.toString(), Snackbar.LENGTH_SHORT).show();
    }
}
