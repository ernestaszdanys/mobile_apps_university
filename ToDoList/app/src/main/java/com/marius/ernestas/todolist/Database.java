package com.marius.ernestas.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static String DATABASE_ENTRY = "entryManager";
    private static int DATABASE_VERSION = 1;
    private String TABLE_ENTRY = "entries";
    private String KEY_ID = "id";
    private String KEY_DATE = "date";
    private String KEY_TITLE = "title";
    private String KEY_DESCRIPTION = "description";
    private String DATE_FORMAT = "yyyy-mm-dd";

    public Database(Context context) {
        super(context, DATABASE_ENTRY, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE "
                        + TABLE_ENTRY + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE
                        + " DATETIME," + KEY_TITLE + " TEXT," + KEY_DESCRIPTION
                        + " TEXT" + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(
                "DROP TABLE IF EXISTS " + TABLE_ENTRY
        );
        onCreate(sqLiteDatabase);
    }
}
