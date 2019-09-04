package com.example.vasshayden_ce03.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import com.example.vasshayden_ce03.fragments.PrefFragment;
import com.example.vasshayden_ce03.objects.WeatherReport;
import com.example.vasshayden_ce03.utils.FileUtils;
import com.example.vasshayden_ce03.utils.NetworkUtils;
import com.example.vasshayden_ce03.widget.WidgetProvider;
import com.example.vasshayden_ce03.widget.WidgetUtils;

import org.json.JSONObject;

import java.io.IOException;


public class WeatherIntentService extends IntentService {
    private static final String TAG = "today";


    public WeatherIntentService() {
        super("WeatherIntentService");
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        Log.i(TAG, "onHandleIntent: service start");
        String urlBeginning = "https://api.darksky.net/forecast/f2387538b308b18c63ed5e201ebe1dfa/";
        String urlTargetCityCoordinates = PrefFragment.getCityPref(getApplicationContext());


        String data = null;
        try{
            data = NetworkUtils.getNetworkData(urlBeginning + urlTargetCityCoordinates);

        }catch(IOException e){
            e.printStackTrace();
        }

        try{

            JSONObject response = new JSONObject(data);
            JSONObject current = response.getJSONObject("currently");
            String summary = current.getString("summary");
            String icon = current.getString("icon");
            int temp = current.getInt("temperature");
            WeatherReport wr = new WeatherReport(icon, summary, temp, NetworkUtils.getCurrentDateAndTime());

            String fileName = urlTargetCityCoordinates.substring(0,2);
            Log.i(TAG, "onHandleWork: file name " + fileName);

            FileUtils.SaveCurrentSerializable(getApplicationContext(),
                    fileName,
                    wr);

        }catch (Exception e){
            e.printStackTrace();
        }


        AppWidgetManager mgr = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName cn = new ComponentName(getApplicationContext(), WidgetProvider.class);
        int[] appWidgetIds = mgr.getAppWidgetIds(cn);

        WidgetUtils.updateWidget(getApplicationContext(), mgr, appWidgetIds[0]);
    }

}
