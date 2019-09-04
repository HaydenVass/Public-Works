//Hayden Vass
//Jav21905
//ArticleAdapter
package com.example.vasshayden_ce07.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vasshayden_ce07.R;
import com.example.vasshayden_ce07.objects.Article;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;
import java.util.Objects;

public class ArticleAdapter extends BaseAdapter {

    private static final long BASE_ID = 0x1011;
    //reference to owning screen - context
    private final Context mContext;
    //collection
    private final ArrayList<Article> mArticles;

    public ArticleAdapter(Context mContext, ArrayList<Article> _mArticles) {
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
            vh.articleAuthor.setText(a.getAuthor());
            vh.articleTitle.setText(a.getArticleName());
            vh.articleImage.setImageUrl(a.getImgURL());
        }
        return convertView;
    }
    static class ViewHolder{
        final TextView articleTitle;
        final TextView articleAuthor;
        final SmartImageView articleImage;

        ViewHolder(View _layout){
            articleTitle = _layout.findViewById(R.id.textViewArticleTitle);
            articleAuthor = _layout.findViewById(R.id.textViewArticleAuthor);
            articleImage = _layout.findViewById(R.id.smartImageViewarticleImage);

        }

    }

}
