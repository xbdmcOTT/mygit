package com.example.logintest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String Create_USERDATA = "create table if not exists Users(id integer primary key autoincrement," +
            "name text not null," +
            "password text not null)";
    private Context mContext;

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version) {
        super(context, name, cursorFactory, version);
        mContext = context;

    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Create_USERDATA);
    }

    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        //

    }
}