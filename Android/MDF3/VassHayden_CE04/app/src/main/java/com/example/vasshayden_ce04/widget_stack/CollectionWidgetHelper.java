package com.example.vasshayden_ce04.widget_stack;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.vasshayden_ce04.R;

public class CollectionWidgetHelper {
    private static final String TAG = "today";
    private static void updateWidget(Context _context, AppWidgetManager _appWidgetManager,
                                     int _widgetId) {

        Log.i(TAG, "stack helper");

        //create the remote view to represent the widget layout
        RemoteViews widgetView = new RemoteViews(_context.getPackageName(),
                R.layout.stack_view_widget);

        Intent stackIntent = new Intent(_context, CollectionWidgetService.class);
        widgetView.setRemoteAdapter(R.id.stackWidgetView, stackIntent);
        widgetView.setEmptyView(R.id.stackWidgetView, R.id.stackWidgetEmptyView);

        //create pending intent template
        Intent viewIntent = new Intent();
        viewIntent.setAction(Intent.ACTION_VIEW);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(_context,
                0, viewIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        widgetView.setPendingIntentTemplate(R.id.stackWidgetView, viewPendingIntent);

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
        ComponentName widgetName = new ComponentName(_context, CollectionWidgetProvider.class);
        int[] widgetIds = mgr.getAppWidgetIds(widgetName);
        mgr.notifyAppWidgetViewDataChanged(widgetIds, R.id.stackWidgetView);
    }


}
