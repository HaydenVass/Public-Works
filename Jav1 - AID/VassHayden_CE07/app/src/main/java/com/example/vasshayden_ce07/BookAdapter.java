//Hayden Vass
// JAV1 - 1904
// BookAdapter

package com.example.vasshayden_ce07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.Objects;

class BookAdapter extends BaseAdapter {

    //Baseid
    private static final long BASE_ID = 0x1011;
    //reference to owning screen - context
    private final Context mContext;
    //collection
    private final ArrayList<Books> mBooks;

    public BookAdapter(Context mContext, ArrayList<Books> mBooks) {
        this.mContext = mContext;
        this.mBooks = mBooks;
    }

    @Override
    public int getCount() {
        if (mBooks != null){
            return mBooks.size();
        }else {return 0;}
    }

    @Override
    public Object getItem(int position) {
        if (mBooks != null && position >= 0 || position < Objects.requireNonNull(mBooks).size()){
            return mBooks.get(position);
        }else{return null;}
    }

    @Override
    public long getItemId(int position) { return BASE_ID + position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        Books b = (Books) getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell, parent,
                    false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }
        if (b != null){
            vh.tv_Name.setText(b.getTitle());
            vh.iv_Picture.setImageUrl(b.getBookCover());
        }
        return convertView;
    }
    static class ViewHolder{
        final TextView tv_Name;
        final SmartImageView iv_Picture;

        ViewHolder(View _layout){
            tv_Name = _layout.findViewById(R.id.title_text_view);
            iv_Picture = _layout.findViewById(R.id.my_image);
        }
    }
}
