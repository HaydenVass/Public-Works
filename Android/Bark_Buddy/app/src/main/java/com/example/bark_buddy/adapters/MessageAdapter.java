package com.example.bark_buddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bark_buddy.R;
import com.example.bark_buddy.objects.Message;

import java.util.ArrayList;
import java.util.Objects;

public class MessageAdapter extends BaseAdapter {
    //Baseid
    private static final long BASE_ID = 0x1022;
    //reference to owning screen - context
    private final Context mContext;
    //collection
    private final ArrayList<Message> mMessages;
    private final String currentUser;

    public MessageAdapter(Context mContext, ArrayList<Message> mMessages, String currentUser) {
        this.mContext = mContext;
        this.mMessages = mMessages;
        this.currentUser = currentUser;
    }

    //adapter for the messages
    // takes messges from a chatroom object which gets pulled from the data base as a document.

    @Override
    public int getCount() {
        if (mMessages != null){
            return mMessages.size();
        }else {return 0;}
    }

    @Override
    public Object getItem(int position) {
        if (mMessages != null && position >= 0 || position < Objects.requireNonNull(mMessages).size()){
            return mMessages.get(position);
        }else{return null;}      }

    @Override
    public long getItemId(int position) {
        return BASE_ID + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        Message message = (Message) getItem(position);

        if(convertView == null){
            if(message != null){
                //looks at the UID of the message - if the message the current user the box will be blue
                //else -- if the message is from the receiver it will be white -
                if(currentUser.equals(message.getSenderId())){
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.message_cell, parent,
                            false);
                }else{
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.message_cell_reciever, parent,
                            false);
                }
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            }
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        if (message != null){
            vh.tv_message.setText(message.getMessage());
            vh.tv_timeStamp.setText(message.getDateTime());
        }
        return convertView;    }

    static class ViewHolder{
        final TextView tv_message;
        final TextView tv_timeStamp;

        ViewHolder(View _layout){
            tv_message = _layout.findViewById(R.id.textView_msg);
            tv_timeStamp = _layout.findViewById(R.id.tv_timestamp);
        }
    }
}
