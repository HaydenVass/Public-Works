package com.example.vasshayden_ce07.Utils;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;


import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationsUtils {

    static public void addNotificationChannel(String _ID, String _name, String _description, int _importance, Activity a) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //id for code - name - what the user sees
            NotificationChannel channel = new NotificationChannel(_ID, _name, _importance);
            channel.setDescription(_description);

            NotificationManager mgr =(NotificationManager) a.getSystemService(NOTIFICATION_SERVICE);
            if (mgr != null){
                mgr.createNotificationChannel(channel);
            }
        }
    }

    static public void clearNotifications(Activity a, int id){
        NotificationManager mgr =(NotificationManager) a.getSystemService(NOTIFICATION_SERVICE);
        if (mgr != null){
            mgr.cancel(id);
        }
    }

}
