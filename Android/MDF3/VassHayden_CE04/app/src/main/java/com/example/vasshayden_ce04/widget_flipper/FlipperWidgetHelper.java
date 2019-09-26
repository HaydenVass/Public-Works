package com.example.vasshayden_ce04.widget_flipper;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.vasshayden_ce04.ConfigActivity;
import com.example.vasshayden_ce04.R;
import com.example.vasshayden_ce04.fragments.ConfigFragment;

public class FlipperWidgetHelper {
    private static final String TAG = "today";
    private static void updateWidget(Context _context, AppWidgetManager _appWidgetManager,
                                     int _widgetId) {

        Log.i(TAG, "flipper helper");


        //create the remote view to represent the widget layout
        //change between scroll or no scroll
        RemoteViews widgetView;
        if(ConfigFragment.getScrollPref(_context)){
            Log.i(TAG, "updateWidget: is flipping");
            widgetView = new RemoteViews(_context.getPackageName(),
                    R.layout.flipper_view_widget);
        }else{
            Log.i(TAG, "updateWidget: not flipping");
            widgetView = new RemoteViews(_context.getPackageName(),
                    R.layout.flipper_view_widget_no_scroll);
        }

        Intent flipperIntent = new Intent(_context, FlipperWidgetService.class);
        widgetView.setRemoteAdapter(R.id.flipperWidgetView, flipperIntent);
        widgetView.setEmptyView(R.id.flipperWidgetView, R.id.flipperWidgetEmptyView);


        //create pending intent template
        Intent viewIntent = new Intent();
        viewIntent.setAction(Intent.ACTION_VIEW);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(_context,
                1, viewIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        widgetView.setPendingIntentTemplate(R.id.flipperWidgetView, viewPendingIntent);


        //next intent
        Intent nextIntent = new Intent(_context, FlipperWidgetProvider.class);
        nextIntent.setAction(FlipperWidgetProvider.ACTION_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(_context,
                2,
                nextIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        widgetView.setOnClickPendingIntent(R.id.button_next, nextPendingIntent);

        //previous intent
        Intent previousIntent = new Intent(_context, FlipperWidgetProvider.class);
        previousIntent.setAction(FlipperWidgetProvider.ACTION_PREVIOUS);
        PendingIntent previousPendingIntent = PendingIntent.getBroadcast(_context,
                3,
                previousIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        widgetView.setOnClickPendingIntent(R.id.button_previous, previousPendingIntent);

        //settings intent
        Intent configIntent = new Intent(_context, ConfigActivity.class);
        configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, _widgetId);
        //pending intent for config activity
        PendingIntent configPendingIntent = PendingIntent.getActivity(_context
                , _widgetId
                , configIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);

        widgetView.setOnClickPendingIntent(R.id.button_settings, configPendingIntent);


        //update widget
        _appWidgetManager.updateAppWidget(_widgetId, widgetView);

    }

    public static void updateWidget(Context _context, AppWidgetManager _appWidgetManager,
                                    int[] _appWidgetIds) {
        for (int appWidgetId : _appWidgetIds) {
            updateWidget(_context, _appWidgetManager, appWidgetId);
        }
    }

    public static void notifyDataChanged(Context _context){
        AppWidgetManager mgr = AppWidgetManager.getInstance(_context);
        ComponentName widgetName = new ComponentName(_context, FlipperWidgetProvider.class);
        int[] widgetIds = mgr.getAppWidgetIds(widgetName);
        mgr.notifyAppWidgetViewDataChanged(widgetIds, R.id.flipperWidgetView);
    }

}
