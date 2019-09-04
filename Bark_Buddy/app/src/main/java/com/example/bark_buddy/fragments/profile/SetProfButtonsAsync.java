package com.example.bark_buddy.fragments.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;

import com.example.bark_buddy.utils.DBUtils;

@SuppressLint("StaticFieldLeak")
class SetProfButtonsAsync extends AsyncTask<Void, Void, Void> {

    //warning says this will leak context, but the the task is linked to the on pause of the fragment
    //lifecycles so this shouldnt be an issue.
    private final Button friendsButton;
    private final String uid;
    private final Context context;

    public SetProfButtonsAsync(Button friendsButton, String uid, Context context) {
        this.friendsButton = friendsButton;
        this.uid = uid;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        DBUtils.setFriendsButton(uid, friendsButton, context);
        return null;
    }
}
