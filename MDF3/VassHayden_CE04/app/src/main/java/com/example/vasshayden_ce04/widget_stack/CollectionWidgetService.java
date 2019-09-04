package com.example.vasshayden_ce04.widget_stack;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class CollectionWidgetService extends RemoteViewsService{
    private static final String TAG = "today";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.i(TAG, "stack widget service");
        return new CollectionWidgetViewFactory(getApplicationContext());
    }
}
