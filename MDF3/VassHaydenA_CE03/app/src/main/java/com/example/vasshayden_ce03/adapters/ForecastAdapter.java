package com.example.vasshayden_ce03.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vasshayden_ce03.R;
import com.example.vasshayden_ce03.objects.WeatherReport;
import com.example.vasshayden_ce03.widget.WidgetUtils;

import java.util.ArrayList;
import java.util.Objects;

public class ForecastAdapter extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<WeatherReport> reports;
    private static final long BASE_ID = 0x1011;


    public ForecastAdapter(Context mContext, ArrayList<WeatherReport> reports) {
        this.mContext = mContext;
        this.reports = reports;
    }

    @Override
    public int getCount() {
        if(reports != null){
            return reports.size();
        }else{return 0;}
    }

    @Override
    public Object getItem(int position) {
        if (reports != null && position >= 0 || position < Objects.requireNonNull(reports).size()){
            return reports.get(position);
        }else{return null;}
    }

    @Override
    public long getItemId(int position) { return BASE_ID + position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        WeatherReport p = (WeatherReport) getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell, parent,
                    false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);

        }else{
            vh = (ViewHolder)convertView.getTag();

        }

        if (p != null){

            vh.tv_condtions.setText(p.getConditions());
            String temp = mContext.getResources().getString(R.string.low, String.valueOf(p.getTempLow())) +
             mContext.getResources().getString(R.string.high, String.valueOf(p.getTempHigh()));
            vh.tv_temp.setText(temp);
            Bitmap bp = WidgetUtils.setBitmap(p.getIcon(), mContext);
            vh.iv_icon.setImageBitmap(bp);


            //vh.iv_icon.setImageResource(p.getPicture());
        }

        return convertView;
    }




    static class ViewHolder{
        final TextView tv_condtions;
        final TextView tv_temp;
        final ImageView iv_icon;

        ViewHolder(View _layout){
            tv_condtions = _layout.findViewById(R.id.textView_condtions_cell);
            tv_temp = _layout.findViewById(R.id.textView_tempurature_cell);
            iv_icon = _layout.findViewById(R.id.imageViewicon_cell);
        }
    }

}
