//Hayden Vass
//JAV2 - 1905
//Contact Utils

package com.example.vasshayden_ce04.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.example.vasshayden_ce04.objects.People;

import java.util.ArrayList;
import java.util.Objects;

public class ContactUtils {


    static public ArrayList<String> getAllIds(Context c){
        // checks common data kinds.phone for a list of all unique IDs for the contacts in the phone.
        ArrayList<String> contactIDs = new ArrayList<>();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID };

        Cursor idCursor = c.getContentResolver().query(uri, projection, null, null, null);
        int indexID = Objects.requireNonNull(idCursor).getColumnIndex(ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID);
       // names.moveToFirst();
        if(idCursor.moveToFirst()){
            do {
                String id = idCursor.getString(indexID);
                //adds to array if IDS arent duplicated
                if(!contactIDs.contains(id)){
                    contactIDs.add(id);

                }
            } while (idCursor.moveToNext());
        }
        return contactIDs;
    }


    static public void getNames(Context c, People p){
        String id = p.getiD();

        Uri uri = ContactsContract.Data.CONTENT_URI;
        String[] nameProjection = new String[] {
                ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
                ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME,
                ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME };

        Cursor nameCursor = c.getContentResolver().query(
                uri, nameProjection, ContactsContract.Data.MIMETYPE + " = '" +
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE + "' AND " +
                        ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID
                        + " = ?", new String[] { id }, null);

        String firstName; String middleName; String lastName;

        if(Objects.requireNonNull(nameCursor).moveToNext()) {
            firstName = nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.CommonDataKinds.
                    StructuredName.GIVEN_NAME));
            middleName = nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.CommonDataKinds
                    .StructuredName.MIDDLE_NAME));
            lastName = nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.CommonDataKinds.
                    StructuredName.FAMILY_NAME));
            //sets passed persons first, middle, and last name pending their ID.
            p.setFirstName(firstName);
            p.setMiddleName(middleName);
            p.setLastName(lastName);
        }
    }


    //gets phone number by selected ID
    static public void getPhoneNumbers(Context c, People p){
        //sets URI
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //defines coloumns to search
        String[] numberProjection = new String[] {
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.IS_PRIMARY};
        //constructs cursor
        Cursor numberCursor = c.getContentResolver().query(uri, numberProjection,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + p.getiD(),
                null, null);

        String primaryNumber = "";
        ArrayList<String>allPhoneNums = new ArrayList<>();

        //traverse through cursor
        while (Objects.requireNonNull(numberCursor).moveToNext()) {
            String phone = numberCursor.getString(numberCursor.getColumnIndex(ContactsContract.
                    CommonDataKinds.Phone.NUMBER));
            String primary = numberCursor.getString(numberCursor.getColumnIndex(ContactsContract.
                    CommonDataKinds.Phone.IS_PRIMARY));

            // sets primary number pending "is_primary" result
            if(!primary.equals("0")){
                primaryNumber = phone;
                allPhoneNums.add(phone);

            }else{
                allPhoneNums.add(phone);
            }
        }

        //if a primary phone isnt selected then the first one in the array of all numbers gets set
        // as primary to be seen in list fragment
        if(Objects.equals(primaryNumber, "")){
            primaryNumber = allPhoneNums.get(0);
        }
        p.setPrimaryPhoneNum(primaryNumber);
        p.setAllPhoneNums(allPhoneNums);
    }


    //gets thumbnail and profile picture from contacts
    static public void getPhoto(Context c, People p){
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
                ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI};
        Cursor cur = c.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, projection,
                ContactsContract.CommonDataKinds.Phone._ID + " = ?",
                new String[]{p.getiD()}, null);
            String imageUri = null;
            String thumbnailUri = null;
            while (Objects.requireNonNull(cur).moveToNext()) {
                imageUri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                thumbnailUri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
            }

            p.setProfileImage(imageUri);
            p.setThumbNail(thumbnailUri);
        }
    }

