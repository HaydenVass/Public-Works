//Hayden Vass
//Jav2 - 1905
//time format uils
package com.example.vasshayden_ce02.utils;

import android.content.Context;
import android.widget.TextView;

import com.example.vasshayden_ce02.fragments.MainPreferencesFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeFormatUtils {

    public static void formatDate(Context context, String eHireDate, TextView tv_hireDate) {
        //gets time format from prefrences
        String timeFormat = MainPreferencesFragment.getDatePrefrence(context);
        if (timeFormat != null){
            //sets time format pending user prefrence
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            Date date = null;
            try {
                date = dateFormat.parse(eHireDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date != null) {
                SimpleDateFormat fmtOut = new SimpleDateFormat(timeFormat, Locale.US);
                String outString = fmtOut.format(date);
                //changes date pending preference
                tv_hireDate.setText(outString);
            }
        }
    }
}
