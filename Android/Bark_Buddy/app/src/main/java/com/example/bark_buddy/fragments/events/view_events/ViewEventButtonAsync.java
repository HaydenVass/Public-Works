package com.example.bark_buddy.fragments.events.view_events;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;

import androidx.fragment.app.FragmentTransaction;

import com.example.bark_buddy.utils.DBUtils;

@SuppressLint("StaticFieldLeak")
class ViewEventButtonAsync extends AsyncTask<Void, Void, Void> {
    //warning says this will leak context, but the the task is linked to the on pause of the fragment
    //lifecycles so this shouldnt be an issue.

    private final String eventId;
    private final Button button;
    private final Context context;
    private final FragmentTransaction ft;

    public ViewEventButtonAsync(String eventId, Button button, Context context, FragmentTransaction ft) {
        this.eventId = eventId;
        this.button = button;
        this.context = context;
        this.ft = ft;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //sets the functionaly of the attend button
        //if the user hasnt created the event - then the button will switch between attend and not attend
        //each version of the button acts accordingly
        //if the user created the event - then the button becomes a delete button which the user can then
        //remove the event from the app
        DBUtils.setAttendButton(eventId, button, context, ft);
        return null;
    }
}
