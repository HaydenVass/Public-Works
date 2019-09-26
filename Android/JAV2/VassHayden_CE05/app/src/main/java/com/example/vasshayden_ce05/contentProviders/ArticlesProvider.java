//Hayden Vass
// Jav2 1905
//ArticlesProvider
package com.example.vasshayden_ce05.contentProviders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.vasshayden_ce05.DataContracts;
import com.example.vasshayden_ce05.utils.DatabaseHelper;

public class ArticlesProvider extends ContentProvider {

    private DatabaseHelper dbh = null;
    private UriMatcher matcher = null;
    private static final int TABLE_MATCH = 10;

    public ArticlesProvider() {
    }
    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbh = DatabaseHelper.getInstance(getContext());
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(DataContracts.URI_AUTHORITY, DataContracts.DATA_TABLE, TABLE_MATCH);
        return false;
    }

    @Override
    public String getType( Uri uri) {
        // at the given URI.
        int resultCode = matcher.match(uri);
        if(resultCode == TABLE_MATCH){
            return "vnd.android.cursor.dir/vnd." + DataContracts.URI_AUTHORITY +
                    "." + DataContracts.DATA_TABLE;
        }
        throw new IllegalArgumentException("URI did not match. Check your authority or table name.");
    }

    @Override
    public int delete( Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("This is not allowed");
    }

    @Override
    public Uri insert( Uri uri, ContentValues values) {
        //Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("This is not allowed");
    }

    @Override
    public Cursor query( Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        //Implement this to handle query requests from clients.
        //validate Uri exist
        int resultCode = matcher.match(uri);
        if(resultCode == TABLE_MATCH){
            return dbh.query(projection, selection, selectionArgs, sortOrder);
        }
        throw new IllegalArgumentException("Invalid URI... check your authority or table name.");
    }

    @Override
    public int update( Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        //Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("This is not allowed");
    }
}
