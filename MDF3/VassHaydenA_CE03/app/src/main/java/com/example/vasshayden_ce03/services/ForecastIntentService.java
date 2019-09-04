package com.example.vasshayden_ce03.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.vasshayden_ce03.fragments.PrefFragment;
import com.example.vasshayden_ce03.objects.WeatherReport;
import com.example.vasshayden_ce03.utils.FileUtils;
import com.example.vasshayden_ce03.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ForecastIntentService extends IntentService {

    private static final String TAG = "today";

    public ForecastIntentService() {
        super("ForecastIntentService");
    }


    @Override
    protected void onHandleIntent( Intent intent) {
        String urlBeginning = "https://api.darksky.net/forecast/f2387538b308b18c63ed5e201ebe1dfa/";
        String urlTargetCityCoordinates = PrefFragment.getCityPref(getApplicationContext());

        String data = null;
        try{
            // pulls inital data from API
            data = NetworkUtils.getNetworkData(urlBeginning + urlTargetCityCoordinates);
        }catch(IOException e){
            e.printStackTrace();
        }

        ArrayList<WeatherReport> weeklyReports = new ArrayList<>();
        try{
            JSONObject response = new JSONObject(data);
            JSONObject daily = response.getJSONObject("daily");
            //get array
            JSONArray days = daily.getJSONArray("data");
            Log.i(TAG, "onHandleWork: " + days.length());

            for (int i = 0; i < days.length(); i++) {
                JSONObject innerObj = days.getJSONObject(i);
                String summary = innerObj.getString("summary");
                String icon = innerObj.getString("icon");
                int temp = innerObj.getInt("temperatureHigh");
                int tempLow = innerObj.getInt("temperatureLow");

                weeklyReports.add(new WeatherReport(icon, summary, temp, temp, tempLow,
                        NetworkUtils.getCurrentDateAndTime()));
                Log.i(TAG, "onHandleIntent: 1");
            }

            String name = urlTargetCityCoordinates.substring(0,2);
            String fileName = name + "forecast";
            Log.i(TAG, "onHandleWork: forecast file name : " + fileName);


            FileUtils.SaveWeeklyForecastSerializable(getApplicationContext(), fileName, weeklyReports);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
