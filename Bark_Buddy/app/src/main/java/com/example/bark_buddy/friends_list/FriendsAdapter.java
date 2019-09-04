package com.example.bark_buddy.friends_list;

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
import com.example.bark_buddy.objects.User;

import java.util.ArrayList;
import java.util.Objects;

public class FriendsAdapter extends BaseAdapter {
    //Baseid
    private static final long BASE_ID = 0x1021;
    //reference to owning screen - context
    private final Context mContext;
    //collection
    private final ArrayList<User> mFriends;


    //adapter for friends list
    //takes friends object and places them into a list view in a fragment
    public FriendsAdapter(Context mContext, ArrayList<User> mFriends) {
        this.mContext = mContext;
        this.mFriends = mFriends;
    }

    @Override
    public int getCount() {
        if (mFriends != null){
            return mFriends.size();
        }else {return 0;}    }

    @Override
    public Object getItem(int position) {
        if (mFriends != null && position >= 0 || position < Objects.requireNonNull(mFriends).size()){
            return mFriends.get(position);
        }else{return null;}    }

    @Override
    public long getItemId(int position) { return BASE_ID + position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        User user = (User) getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.friend_card_layout, parent,
                    false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }
        if (user != null){
            vh.tv_local.setText(user.getDogName());
            Bitmap bmp = BitmapFactory.decodeByteArray(user.getProfilePicture(), 0, user.getProfilePicture().length);
            vh.iv_Picture.setImageBitmap(bmp);
        }
        return convertView;
    }

    static class ViewHolder{
        final TextView tv_local;
        final ImageView iv_Picture;

        ViewHolder(View _layout){
            tv_local = _layout.findViewById(R.id.textView_friendcard_name);
            iv_Picture = _layout.findViewById(R.id.imageView_friendcard_img);
        }
    }
}
