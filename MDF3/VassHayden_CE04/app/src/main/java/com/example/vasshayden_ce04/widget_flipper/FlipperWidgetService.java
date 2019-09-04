package com.example.vasshayden_ce04.widget_flipper;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class FlipperWidgetService extends RemoteViewsService {
    private static final String TAG = "today";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.i(TAG, "flipper service");
        return new FlipperWidgetViewFactory(getApplicationContext());
    }
}
