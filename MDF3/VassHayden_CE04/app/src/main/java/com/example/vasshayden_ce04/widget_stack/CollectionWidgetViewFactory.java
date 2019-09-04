package com.example.vasshayden_ce04.widget_stack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.vasshayden_ce04.R;
import com.example.vasshayden_ce04.utils.PhotoUtils;

import java.io.IOException;
import java.util.ArrayList;

class CollectionWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context mContext;
    private ArrayList<Uri> mPhotoUris;


    public CollectionWidgetViewFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        mPhotoUris = PhotoUtils.getImagesPath(mContext);
    }

    @Override
    public void onDataSetChanged() {
        mPhotoUris = PhotoUtils.getImagesPath(mContext);
    }

    @Override
    public void onDestroy() {
        mPhotoUris.clear();
        mPhotoUris = null;
    }

    @Override
    public int getCount() {
        return mPhotoUris.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Uri uri = mPhotoUris.get(position);
        RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.stack_view_item);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            itemView.setImageViewBitmap(R.id.stackWidgetItemPicture, bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // fill intent
        Intent fillInIntent = new Intent();
        fillInIntent.setDataAndType(uri , "image/*");
        itemView.setOnClickFillInIntent(R.id.stackWidgetItemPicture, fillInIntent);

        return itemView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        //?
        return true;
    }
}
