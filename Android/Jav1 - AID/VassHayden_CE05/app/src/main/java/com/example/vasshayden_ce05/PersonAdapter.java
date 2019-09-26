package com.example.vasshayden_ce05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

class PersonAdapter extends BaseAdapter {

    //baseID
    private static final long BASE_ID = 0x1022;
    //context
    private final Context mContext;
    //collection
    private final ArrayList<Person> mPeople;

    public PersonAdapter(Context _context, ArrayList<Person> people){
        mPeople = people;
        mContext = _context;
    }

    @Override
    public int getCount() {
        if(mPeople != null){
            return mPeople.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (mPeople != null && position >= 0 || position < Objects.requireNonNull(mPeople).size()){
            return mPeople.get(position);
        }else{return null;}    }

    @Override
    public long getItemId(int position) { return BASE_ID + position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        Person p = (Person)getItem(position);

        //attches cell xml to list view
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell, parent,
                    false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }
        // sets values of the view holder
        if (p != null){
            vh.tvName.setText(p.toString());
            vh.tvId.setText(String.valueOf(p.getID()));
        }

        return convertView;
    }

    static class ViewHolder{
        final TextView tvName;
        final TextView tvId;

        ViewHolder(View _layout){
            tvName = _layout.findViewById(R.id.tv_name);
            tvId = _layout.findViewById(R.id.tv_id);
        }
    }
}
