package com.example.bark_buddy.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bark_buddy.R;
import com.example.bark_buddy.objects.Event;

import java.util.ArrayList;
import java.util.Objects;

//base adapter over firebase adapter to avoid the UI from jumping around every time an event gets added
//adapter will refresh every time the events page is navigated to


public class EventAdapter extends BaseAdapter {
    //Baseid
    private static final long BASE_ID = 0x1011;
    //reference to owning screen - context
    private final Context mContext;
    //collection
    private final ArrayList<Event> mEvents;


    //adapter that takes evennts and places them into the grid on the home page.

    public EventAdapter(Context mContext, ArrayList<Event> mEvents) {
        this.mContext = mContext;
        this.mEvents = mEvents;
    }

    @Override
    public int getCount() {
        if (mEvents != null){
            return mEvents.size();
        }else {return 0;}
    }

    @Override
    public Object getItem(int position) {
        if (mEvents != null && position >= 0 || position < Objects.requireNonNull(mEvents).size()){
            return mEvents.get(position);
        }else{return null;}
    }

    @Override
    public long getItemId(int position) { return BASE_ID + position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        Event event = (Event) getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.event_cell, parent,
                    false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }
        if (event != null){
            vh.tv_host.setText(event.getHost());
            vh.tv_local.setText(event.getLocation());
            Bitmap bmp = BitmapFactory.decodeByteArray(event.getImgBytes(), 0, event.getImgBytes().length);
            vh.iv_Picture.setImageBitmap(bmp);
        }
        return convertView;
    }
    static class ViewHolder{
        final TextView tv_local;
        final TextView tv_host;
        final ImageView iv_Picture;

        ViewHolder(View _layout){
            tv_local = _layout.findViewById(R.id.textView_main_event_cell_location);
            tv_host = _layout.findViewById(R.id.textView_main_event_cell_host);
            iv_Picture = _layout.findViewById(R.id.imageView_main_event_cell_picture);
        }
    }
}
