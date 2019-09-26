//Hayden Vass
// Jav2 1905
//Notification Utils
package com.example.vasshayden_ce05.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class NotificationsUtils {

    //presents a dialog to the user
    static public void presentDialog(Activity a, String title, String message, String positive){
        AlertDialog.Builder builder = new AlertDialog.Builder(a);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton(positive, null);
        builder.show();
    }

    //presents toast
    static public void presentToast(Context c, String message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }
}
