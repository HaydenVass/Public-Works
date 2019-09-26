package com.example.vasshayden_ce03;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vasshayden_ce03.fragments.PrefFragment;
import com.example.vasshayden_ce03.services.ForecastIntentService;
import com.example.vasshayden_ce03.services.WeatherIntentService;

public class MainActivity extends AppCompatActivity {
    private int mWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent starter = getIntent();
        mWidgetId = starter.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID
                ,AppWidgetManager.INVALID_APPWIDGET_ID);

        if(mWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish();
            return;
        }

        if(savedInstanceState == null){
            PrefFragment fragment = PrefFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, PrefFragment.TAG)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_save){

            Intent result = new Intent();
            result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId);
            setResult(RESULT_OK, result);

            //intent for current weather
            Intent currentWeatherIntent = new Intent(this,
                    WeatherIntentService.class);
            startService(currentWeatherIntent);

            //intent for forecast
            Intent weeklyForecastIntent = new Intent(this,
                    ForecastIntentService.class);
            startService(weeklyForecastIntent);

            finish();
        }
        return true;
    }
}
