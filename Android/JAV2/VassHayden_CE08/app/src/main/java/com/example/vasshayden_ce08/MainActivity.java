package com.example.vasshayden_ce08;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vasshayden_ce08.fragments.MainListFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final ArrayList<String> mSchemas = new ArrayList<>();
    private static final String LINK_SCHEME = "file:///";
    private static final String LINK_AUHTORITY = "android_asset/";
    public static final String LINK_PATH_DETAILS = "details_asset.html";
    public static final String LINK_PATH_FORM = "form_asset.html";
    public static final String LINK_PATH_LIST = "list_asset.html";
    public static final String URI_BEGINNING = LINK_SCHEME + LINK_AUHTORITY;

    public static final String STUDENT_FILENAME = "StudentsAll";
    static {
        mSchemas.add("file:/");
        mSchemas.add("https:/");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                MainListFragment.newInstance()).commit();

    }

}

