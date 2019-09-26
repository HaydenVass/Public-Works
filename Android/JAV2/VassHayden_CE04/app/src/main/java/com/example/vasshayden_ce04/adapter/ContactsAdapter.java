//Hayden Vass
//Jav 2 - 1905
//contacts adapter
package com.example.vasshayden_ce04.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vasshayden_ce04.R;
import com.example.vasshayden_ce04.objects.People;

import java.util.ArrayList;
import java.util.Objects;

public class ContactsAdapter extends BaseAdapter {
    private static final long BASE_ID = 0x1011000;
    //reference to owning screen - context
    private final Context mContext;
    private final ArrayList<People> contactsList;

    //for list fragment
    public ContactsAdapter(Context mContext, ArrayList<People> contactsList) {
        this.mContext = mContext;
        this.contactsList = contactsList;
    }

    @Override
    public int getCount() {
        if(contactsList != null){
            return contactsList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (contactsList != null && position >= 0 || position < Objects.requireNonNull(contactsList).size()){
            return contactsList.get(position);
        }else{return null;}
    }

    @Override
    public long getItemId(int position) { return BASE_ID + position;}


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        People person = (People)getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cell,
                    parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }

        //loads people objects into the custom list view
        if (person != null){
            if(person.getPrimaryPhoneNum() != null){
                vh.primaryPhoneNum.setText(person.getPrimaryPhoneNum());
            }else{
                vh.primaryPhoneNum.setText(person.getAllPhoneNums().get(0));
            }
            String fullName = person.getFirstName() + " " + person.getLastName();
            vh.firstLastName.setText(fullName);
            if(person.getThumbNail() != null){
                vh.contactImg.setImageURI(Uri.parse(person.getThumbNail()));
            }else{
                vh.contactImg.setImageResource(R.drawable.ic_person);
            }
        }
        return convertView;
    }

    static class ViewHolder{
        final ImageView contactImg;
        final TextView firstLastName;
        final TextView primaryPhoneNum;

        ViewHolder(View _layout){
            contactImg = _layout.findViewById(R.id.list_user_image);
            firstLastName = _layout.findViewById(R.id.list_user_name);
            primaryPhoneNum = _layout.findViewById(R.id.list_user_phone_num);
        }
    }
}
