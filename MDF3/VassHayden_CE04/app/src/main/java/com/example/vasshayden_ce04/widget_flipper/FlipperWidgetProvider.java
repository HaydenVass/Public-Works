package com.example.vasshayden_ce04.widget_flipper;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.vasshayden_ce04.R;
import com.example.vasshayden_ce04.fragments.ConfigFragment;

import java.util.Objects;

public class FlipperWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "today";

    public static final String ACTION_NEXT = "ACTION_NEXT";
    public static final String ACTION_PREVIOUS= "ACTION_PREVIOUS";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        FlipperWidgetHelper.updateWidget(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();

        //gets manager and IDs to target remote view
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        ComponentName cn = new ComponentName(context, FlipperWidgetProvider.class);
        int[] appWidgetIds = mgr.getAppWidgetIds(cn);
        RemoteViews remoteViews;
        if(ConfigFragment.getScrollPref(context)){
            remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.flipper_view_widget);
        }else{
            remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.flipper_view_widget_no_scroll);
        }

        //updates view on forward and backward button
        switch (Objects.requireNonNull(action)){
            case ACTION_NEXT:
                Log.i(TAG, "onReceive: action next");
                remoteViews.showNext(R.id.flipperWidgetView);
                mgr.updateAppWidget(appWidgetIds, remoteViews);
                break;
            case ACTION_PREVIOUS:
                Log.i(TAG, "onReceive: action previous");
                remoteViews.showPrevious(R.id.flipperWidgetView);
                mgr.updateAppWidget(appWidgetIds, remoteViews);

                break;
        }
    }
}
