//Hayden Vass
//JAV2 - 1905
//ArticlesAdapter
package com.example.vasshaydewn_ce01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vasshaydewn_ce01.Objects.Article;

import java.util.ArrayList;
import java.util.Objects;

public class ArticlesAdapter extends BaseAdapter {

    private static final long BASE_ID = 0x1011;
    //reference to owning screen - context
    private final Context mContext;
    //collection
    private final ArrayList<Article> mArticles;

    public ArticlesAdapter(Context mContext, ArrayList<Article> _mArticles) {
        this.mContext = mContext;
        this.mArticles = _mArticles;
    }

    @Override
    public int getCount() {
        if(mArticles != null){
            return mArticles.size();
        }else{return 0;}
    }

    @Override
    public Object getItem(int position) {
        if (mArticles != null && position >= 0 || position < Objects.requireNonNull(mArticles).size()){
            return mArticles.get(position);
        }else {return null;}
    }

    @Override
    public long getItemId(int position) { return BASE_ID + position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        Article a = (Article)getItem(position);
        //creates listview with custom cell
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }
        // assigns ui elements
        if (a != null){
            vh.postNum.setText(a.getNumPost());
            vh.articleAuthor.setText(a.getArticleAuthor());
            vh.articleTitle.setText(a.getArticleName());
        }
        return convertView;
    }

    //view holder for custom list view
    static class ViewHolder{
        final TextView articleTitle;
        final TextView articleAuthor;
        final TextView postNum;

        ViewHolder(View _layout){
            articleTitle = _layout.findViewById(R.id.textView_article);
            articleAuthor = _layout.findViewById(R.id.textView_articleAuthor);
            postNum = _layout.findViewById(R.id.textview_post);
        }
    }
}
