package com.example.vasshayden_ce04.widget_stack;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;

public class CollectionWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "today";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        Log.i(TAG, "stack provider");
        CollectionWidgetHelper.updateWidget(context, appWidgetManager, appWidgetIds);
    }
}
