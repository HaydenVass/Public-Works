//Hayden Vass
//Jav2 1905
//DB helper
package com.example.vasshayden_ce02.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "today";

    private static final String DATABASE_FILE = "database.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "employees";
    private static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRSTNAME = "firstName";
    public static final String COLUMN_LASTNAME = "lastName";
    public static final String COLUMN_EMPLOYEENUMBER = "employeeNumber";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_HIREDATE = "hireDate";


    private static final String CREATE_TABLE_CMD = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FIRSTNAME + " TEXT," +
            COLUMN_LASTNAME + " TEXT," +
            COLUMN_EMPLOYEENUMBER + " INTEGER," +
            COLUMN_STATUS + " TEXT," +
            COLUMN_HIREDATE + " DATETIME"
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

    public Cursor getAllData() {
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getAllDataFilterNum(String direction) {
        return db.query(TABLE_NAME, null, null, null, null, null, COLUMN_EMPLOYEENUMBER + " " + direction);
    }

    public Cursor getAllDataFilterStatus(String direction) {
        return db.query(TABLE_NAME, null, null, null, null, null, COLUMN_STATUS + " " + direction);
    }

    public void insertEmployee(ContentValues cv) {
        db.insert(TABLE_NAME, null, cv);
    }

    public void deleteEmployee(Integer eNum) {
        db.delete(TABLE_NAME, COLUMN_EMPLOYEENUMBER + "=" + eNum, null);
    }

    public void updateEmployee(Integer id, ContentValues cv) {

        db.update(TABLE_NAME, cv, "_id=" + id, null);
    }

    public void deleteAll() {
         db.execSQL("delete from " + TABLE_NAME);
    }

    public boolean checkENumExist(Integer eNum) {
        String[] columns = { COLUMN_EMPLOYEENUMBER };
        String selection = COLUMN_EMPLOYEENUMBER + " =?";
        String[] selectionArgs = { String.valueOf(eNum) };
        String limit = "1";
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);

        boolean exists = (cursor.getCount() > 0);
        Log.i(TAG, "checkENumExist: " + exists);
        return exists;

        //        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_EMPLOYEENUMBER}, selection,
        //                new String[]{String.valueOf(eNum)}, null, null, null, "1");
    }

}
