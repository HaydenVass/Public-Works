package com.example.vasshayden_ce03.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.vasshayden_ce03.ForecastActivity;
import com.example.vasshayden_ce03.MainActivity;
import com.example.vasshayden_ce03.R;
import com.example.vasshayden_ce03.fragments.PrefFragment;
import com.example.vasshayden_ce03.objects.WeatherReport;
import com.example.vasshayden_ce03.utils.FileUtils;

public class WidgetUtils {

    private static final String TAG = "today";

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int widgetId){

        Log.i(TAG, "updateWidget: " );
        //get theme:
        String theme = PrefFragment.getDisplayPref(context);
        RemoteViews widgetViews;
        if ("dark".equals(theme)) {
            widgetViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout_dark);
        } else {
            widgetViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout_light);
        }


        String fileName = PrefFragment.getCityPref(context).substring(0,2);
        Log.i(TAG, "updateWidget: " + fileName);

        WeatherReport wr = FileUtils.LoadCurrentSerializable(context, fileName);
        if(wr == null){
            //set no internet / no cached data
            widgetViews.setTextViewText(R.id.textView_conditions, context.getResources().
                    getString(R.string.no_internet));
            widgetViews.setTextViewText(R.id.textView_timeStamp, " ");
            widgetViews.setTextViewText(R.id.textView_temp, context.getResources().
                    getString(R.string.no_saved_data));
            widgetViews.setImageViewBitmap(R.id.imageView_icon, null);

        }else{
            widgetViews.setTextViewText(R.id.textView_conditions, context.getResources().
                    getString(R.string.current_conditions, wr.getConditions()));
            widgetViews.setTextViewText(R.id.textView_timeStamp, wr.getTime());
            widgetViews.setTextViewText(R.id.textView_temp, context.getResources().
                    getString(R.string.current_temp, String.valueOf(wr.getTempurature())));
            Bitmap bitmap = setBitmap(wr.getIcon(), context);
            widgetViews.setImageViewBitmap(R.id.imageView_icon, bitmap);
        }



//        //create pending intent to launch config
        Intent configIntent = new Intent(context, MainActivity.class);
        configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context
                , widgetId
                , configIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);

        widgetViews.setOnClickPendingIntent(R.id.button_conf_prefs, configPendingIntent);


        //pending intent for weekly forecast
        Intent forecastIntent = new Intent(context, ForecastActivity.class);
        configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        PendingIntent forecastPendingIntent = PendingIntent.getActivity(context
                , widgetId
                , forecastIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);
        widgetViews.setOnClickPendingIntent(R.id.imageView_icon, forecastPendingIntent);

        //update the widget with the newly configured remote views.
        appWidgetManager.updateAppWidget(widgetId, widgetViews);

    }


    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId :
                appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }


    public static Bitmap setBitmap(String icon, Context context){
        Bitmap bitmap;
        if(icon.contains("clear") || icon.contains("sunny")){
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.sunrise);
        }else if(icon.contains("cloudy")){
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.cloudy);

        }else if(icon.contains("rain")){
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.rain);
        }else{
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.storm);
        }
        return bitmap;
    }

}
