//Hayden Vass
// Jav2 1905
//Book Adapter
package com.example.vasshayden_ce05.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vasshayden_ce05.R;
import com.loopj.android.image.SmartImageView;

public class BookAdapter extends CursorAdapter {
    private final LayoutInflater cursorInflater;

    public BookAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.cursorInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //assings id from column to the tag
        view.setTag(cursor.getString(cursor.getColumnIndex("_id")));
        //ui elements
        TextView bookTitle = view.findViewById(R.id.textView_booktitle);
        SmartImageView bookImage = view.findViewById(R.id.smartImageView_bookimg);
        //variables
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String imgurl = cursor.getString(cursor.getColumnIndex("thumbnail"));

        bookTitle.setText(title);
        bookImage.setImageUrl(imgurl);
    }

    //inflates custom cell
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return cursorInflater.inflate(R.layout.cell, viewGroup, false);
    }
}
