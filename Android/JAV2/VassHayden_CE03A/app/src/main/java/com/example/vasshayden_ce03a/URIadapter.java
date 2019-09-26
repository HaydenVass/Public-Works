//Hayden Vass
//JAV2 1905
//URIAdapater
package com.example.vasshayden_ce03a;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.Objects;

public class URIadapter extends BaseAdapter {
    private static final long BASE_ID = 0x1011000;
    //reference to owning screen - context
    private final Context mContext;
    //collection
    private final ArrayList<Uri> imageRefsList;

    public URIadapter(Context mContext, ArrayList<Uri> imageRefs) {
        this.mContext = mContext;
        this.imageRefsList = imageRefs;
    }

    @Override
    public int getCount() {
        if(imageRefsList != null){
            return imageRefsList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (imageRefsList != null && position >= 0 || position < Objects.requireNonNull(imageRefsList).size()){
            return imageRefsList.get(position);
        }else{return null;}    }

    @Override
    public long getItemId(int position) { return BASE_ID + position;}

    @Override
    //convert URI to image and place into grid
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        Uri imgUris= (Uri)getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell,
                    parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }

        if (imgUris != null){
            //USING URI  can be converted in image view
            vh.cameraImages.setImageURI(imgUris);
        }
        return convertView;
    }


    static class ViewHolder{
        final ImageView cameraImages;
        ViewHolder(View _layout){
            cameraImages = _layout.findViewById(R.id.iv_cameraShot);
        }
    }
}
