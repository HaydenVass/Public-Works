package com.example.vasshaydenaid_ce04;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class PostAdapter extends BaseAdapter {
    //baseID
    private static final long BASE_ID = 0x1022;
    //context
    private final Context mContext;
    //collection
    private final ArrayList<Post> mPost;

    public PostAdapter(Context _context, ArrayList<Post> post){
        mPost = post;
        mContext = _context;
    }

    @Override
    public int getCount() {
        if(mPost != null){
            return mPost.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (mPost != null && position >= 0 || position < Objects.requireNonNull(mPost).size()){
            return mPost.get(position);
        }else{return null;}    }

    @Override
    public long getItemId(int position) { return BASE_ID + position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        Post p = (Post)getItem(position);

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
            vh.userName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            vh.userName.setTextSize(20);
            vh.userName.setText(p.user);
            vh.date.setText(p.date);
            vh.postTitle.setText(p.postTitle);
            vh.postContent.setText(p.postContent);
            vh.thumbsup.setImageResource(p.thumbsupImg);
            vh.userPicture.setImageResource(p.userPicture);
            vh.moreBtn.setImageResource(p.moreImg);
            vh.starRatings.setImageResource(p.ratingImg);
        }

        return convertView;
    }



    static class ViewHolder{
        final TextView userName;
        final TextView date;
        final TextView postTitle;
        final TextView postContent;
        final ImageView userPicture;
        final ImageView starRatings;
        final ImageView thumbsup;
        final ImageView moreBtn;

        ViewHolder(View _layout){
            userName = _layout.findViewById(R.id.userName);
            date = _layout.findViewById(R.id.dateTextField);
            postTitle = _layout.findViewById(R.id.post_title);
            postContent = _layout.findViewById(R.id.post_content);
            userPicture  = _layout.findViewById(R.id.userImage);
            starRatings  = _layout.findViewById(R.id.starRating);
            thumbsup = _layout.findViewById(R.id.thumbsUpImg);
            moreBtn = _layout.findViewById(R.id.moreImg);
        }
    }
}
