package com.example.vasshaydence04;
// Hayden Vass
// JAV1 - 1094
// CE04
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Objects;

class PersonAdapter extends BaseAdapter {
    //Baseid
    private static final long BASE_ID = 0x1011;
    //reference to owning screen - context
    private final Context mContext;
    //collection
    private final ArrayList<Person> mPeople;

    //constructor
    public PersonAdapter (Context _context, ArrayList<Person> _people){
        mPeople = _people;
        mContext = _context;
    }

    //count
    @Override
    public int getCount() {
        if (mPeople != null){
            return mPeople.size();
        }else {return 0;}
    }

    //Item
    @Override
    public Object getItem(int position) {
        if (mPeople != null && position >= 0 || position < Objects.requireNonNull(mPeople).size()){
            return mPeople.get(position);
        }else{return null;}
    }

    //Item ID
    @Override
    public long getItemId(int position) { return BASE_ID + position; }

    // get inflated child / line - item view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        Person p = (Person)getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell, parent,
                    false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }

        if (p != null){
            vh.tv_Name.setText(p.toString());
            vh.tv_Birthday.setText(p.getBirthday());
            vh.iv_Picture.setImageResource(p.getPicture());
        }

        return convertView;
    }

    static class ViewHolder{
        final TextView tv_Name;
        final TextView tv_Birthday;
        final ImageView iv_Picture;

        ViewHolder(View _layout){
            tv_Name = _layout.findViewById(R.id.tv_firstName);
            tv_Birthday = _layout.findViewById(R.id.tv_lastName);
            iv_Picture = _layout.findViewById(R.id.imageID);
            //TODO: need image
        }
    }
}
