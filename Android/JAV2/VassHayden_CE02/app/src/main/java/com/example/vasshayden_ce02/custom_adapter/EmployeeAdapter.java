//Hayden Vass
//Jav2 - 1905
//Employee Adapter

package com.example.vasshayden_ce02.custom_adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.vasshayden_ce02.utils.DatabaseHelper;


public class EmployeeAdapter extends ResourceCursorAdapter {


    public EmployeeAdapter(Context context, Cursor c) {
        super(context, android.R.layout.simple_list_item_2, c, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        view.setTag(cursor.getString(cursor.getColumnIndex("_id")));


        String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRSTNAME));
        String lastName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LASTNAME));
        TextView tvFname = view.findViewById(android.R.id.text1);
        String fullName = firstName + " " + lastName;
        tvFname.setText(fullName);

        String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEENUMBER));
        TextView tvDescription = view.findViewById(android.R.id.text2);
        tvDescription.setText(description);
    }
}
