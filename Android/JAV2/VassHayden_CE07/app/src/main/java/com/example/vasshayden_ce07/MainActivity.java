//Hayden Vass
//Jav21905
//main
package com.example.vasshayden_ce07;

import android.app.NotificationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vasshayden_ce07.Utils.NotificationsUtils;
import com.example.vasshayden_ce07.fragments.MainListFragment;


public class MainActivity extends AppCompatActivity{

    public static final int STANDARD_NOTIFICATION = 0x10000000;
    public static final String CHANNEL_ID = "Reddit_ID";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        NotificationsUtils.addNotificationChannel(CHANNEL_ID, "Reddit puppy",
                "New post", NotificationManager.IMPORTANCE_DEFAULT, this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                MainListFragment.newInstance()).commit();

    }
}
