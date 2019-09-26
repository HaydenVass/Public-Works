package com.example.vasshayden_ce03.utils;

import android.content.Context;
import android.util.Log;

import com.example.vasshayden_ce03.objects.WeatherReport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FileUtils {

    private static final String TAG = "today";

    public static void SaveCurrentSerializable(Context context, String fileName, WeatherReport _weatherReports){
        try{
            FileOutputStream fos = context.openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(_weatherReports);
            oos.close();
            Log.i(TAG, "SaveCurrentSerializable: successful" );
        }catch (IOException e){
            e.printStackTrace();
            Log.i(TAG, "SaveCurrentSerializable: failure");
        }
    }

    //loads list from internal memory
    public static WeatherReport LoadCurrentSerializable(Context context, String fileName){
        WeatherReport reportToSend = null;
        try{
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            reportToSend = (WeatherReport)ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return reportToSend;
    }


    //
    //Forecast
    // saves list to inernal memory
    public static void SaveWeeklyForecastSerializable(Context context, String fileName, ArrayList<WeatherReport> _weatherReports){
        try{
            FileOutputStream fos = context.openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(_weatherReports);
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //loads list from internal memory
    public static ArrayList<WeatherReport> LoadWeeklyForecastSerializable(Context context, String fileName){
        ArrayList<WeatherReport> reportTosend = new ArrayList<>();
        try{
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            reportTosend = (ArrayList<WeatherReport>)ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return reportTosend;
    }
}
