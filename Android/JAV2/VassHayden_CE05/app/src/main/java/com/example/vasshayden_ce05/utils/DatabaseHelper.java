package com.example.vasshayden_ce05.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_FILE = "articlesdatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "articles";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_THUMBNAIL = "thumbnail";
    private static final String COLUMN_BODY = "body";

    private static final String CREATE_TABLE_CMD = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT," +
            COLUMN_THUMBNAIL + " TEXT," +
            COLUMN_BODY + " TEXT"
            + ")";


    private static DatabaseHelper instance = null;
    //database ref
    private final SQLiteDatabase db;

    public static DatabaseHelper getInstance(Context _context) {
        if (instance == null) {
            instance = new DatabaseHelper(_context);
        }
        return instance;
    }


    private DatabaseHelper(Context _context) {
        super(_context, DATABASE_FILE, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertArticle(String _title, String _body, String _urlThmbString){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, _title);
        cv.put(COLUMN_BODY, _body);
        cv.put(COLUMN_THUMBNAIL, _urlThmbString);

        db.insert(TABLE_NAME, null, cv);
    }

    public Cursor getAllData(){
        return db.query(TABLE_NAME, null,null, null,
                 null, null, null);
    }

    public Cursor query(String[] projection, String selection,
                        String[] selectionArgs, String sortOrder){

        return db.query(TABLE_NAME, projection,selection,selectionArgs,
                null,null,sortOrder);
    }
}
