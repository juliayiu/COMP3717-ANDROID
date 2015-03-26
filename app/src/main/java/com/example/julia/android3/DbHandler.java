package com.example.julia.android3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "photoDatabase";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME    = "PHOTOTABLE";
    public static final String KEY_ID        = "_id";
    public static final String TITLE         = "title";
    public static final String IMAGE         = "image";
    public static final String DESC          = "description";
    public Context context;
    private static final String CREATE_TABLE  = " CREATE TABLE " + TABLE_NAME +  " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TITLE + " VARCHAR(100),"
            + IMAGE + " BLOB,"
            + DESC + " VARCHAR(255));";
    private static final String DROP_TABLE    = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("DbHandler", "Constructor Called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

        Log.d("DbHandler", "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);

        Log.d("DbHandler", "onUpdate");
    }

}