package com.example.vasshayden_ce04;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vasshayden_ce04.fragments.ConfigFragment;
import com.example.vasshayden_ce04.widget_flipper.FlipperWidgetHelper;
import com.example.vasshayden_ce04.widget_flipper.FlipperWidgetProvider;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        if(savedInstanceState == null){
            ConfigFragment fragment = ConfigFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.config_frag_container, fragment, ConfigFragment.TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.config_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_save){

            //switches on / off automatic scrolling
            AppWidgetManager mgr = AppWidgetManager.getInstance(getApplicationContext());
            ComponentName cn = new ComponentName(getApplicationContext(), FlipperWidgetProvider.class);
            int[] appWidgetIds = mgr.getAppWidgetIds(cn);

            FlipperWidgetHelper.updateWidget(this, mgr, appWidgetIds);
            finish();
        }
        return true;
    }
}
