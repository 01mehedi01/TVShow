package com.mehedi.user.tvshow.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 1/31/2018.
 */

public class SearchTvResultDataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "searchresult";
    public static final int DATABASE_VERSION =1;

    public static final String SUCCESS_RESULT_TABLE = "Result";
    public static final String EVENT_ID = "_ID";
    public static final String EVENT_NAME = "E_NAME";


    public static final String CREATE_EVENT_TABLE = "create table " + SUCCESS_RESULT_TABLE +" ("+
            EVENT_ID + " integer primary key ,"+
            EVENT_NAME + " text );";



    public SearchTvResultDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_EVENT_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
          sqLiteDatabase.execSQL("drop table if exists " + SUCCESS_RESULT_TABLE);
          onCreate(sqLiteDatabase);
    }
}
