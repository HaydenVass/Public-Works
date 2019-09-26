package com.example.vasshayden_ce04.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.vasshayden_ce04.R;

import java.util.ArrayList;
import java.util.Objects;

public class PhotoAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<Uri> allUris;
    private static final long BASE_ID = 0x1011;

    public PhotoAdapter(Context mContext, ArrayList<Uri> allUris) {
        this.mContext = mContext;
        this.allUris = allUris;
    }

    @Override
    public int getCount() {
        if(allUris != null){
            return allUris.size();
        }else{return 0;}
    }

    @Override
    public Object getItem(int position) {
        if (allUris != null && position >= 0 || position < Objects.requireNonNull(allUris).size()){
            return allUris.get(position);
        }else{return null;}
    }

    @Override
    public long getItemId(int position) { return BASE_ID + position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        Uri uri = (Uri) getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell, parent,
                    false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);

        }else{
            vh = (ViewHolder)convertView.getTag();
        }
        if (uri != null){

            vh.iv.setImageURI(uri);
        }

        return convertView;
    }

    static class ViewHolder{
        final ImageView iv;


        ViewHolder(View _layout){
            iv = _layout.findViewById(R.id.imageView_photo);
        }
    }
}
