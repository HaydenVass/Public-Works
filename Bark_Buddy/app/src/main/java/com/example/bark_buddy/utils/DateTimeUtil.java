package com.example.bark_buddy.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

    //time picker
    public static void setTimePickerOnViewClick(final Context context, View clickedV, final TextView setData){
        clickedV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                TimePickerDialog picker = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String next = sHour + ":" + sMinute;
                                if(sMinute < 10){
                                    next = sHour + ":0"+sMinute;
                                }
                                setData.setText(next);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
    }

    //date time picker
    public static void setDatePickerOnViewClick(final Context context, View clickedView, final TextView pickedDate){
        clickedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                final int day = c.get(Calendar.DAY_OF_MONTH);
                final int month = c.get(Calendar.MONTH);
                final int year = c.get(Calendar.YEAR);

                //constructs date time picker and formats
                final DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String yearStr = String.valueOf(year);
                                String monthStr = String.valueOf(month);
                                if(monthStr.toCharArray().length == 1){
                                    monthStr = "0"+monthStr;
                                }
                                String dayStr = String.valueOf(dayOfMonth);
                                if(dayStr.toCharArray().length == 1){
                                    dayStr = "0"+dayStr;
                                }
                                String timeToAdd = dayStr+"-"+monthStr +"-"+yearStr;

                                pickedDate.setText(timeToAdd);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

    }

    //get current date
    public static String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }
}
