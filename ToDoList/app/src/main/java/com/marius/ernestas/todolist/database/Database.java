package com.marius.ernestas.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static String DATABASE_ENTRY = "entryManager";
    private static int DATABASE_VERSION = 1;
    private String TABLE_ENTRY = "entries";
    private String KEY_ID = "id";
    private String KEY_DATE = "date";
    private String KEY_IMPORTANCE = "importance";
    private String KEY_TITLE = "title";
    private String KEY_DESCRIPTION = "description";


    public Database(Context context) {
        super(context, DATABASE_ENTRY, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE "
                        + TABLE_ENTRY + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE
                        + " DATETIME," + KEY_IMPORTANCE + " INT," + KEY_TITLE
                        + " TEXT," + KEY_DESCRIPTION + " TEXT" + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(
                "DROP TABLE IF EXISTS " + TABLE_ENTRY
        );
        onCreate(sqLiteDatabase);
    }

    public void closeDatabase() {
        // Get access to database
        SQLiteDatabase database = this.getReadableDatabase();

        if (database != null && database.isOpen()) {
            database.close();
        }
    }


    public void addNote(Note note) throws IllegalStateException {
        // Get access to database
        SQLiteDatabase database = this.getWritableDatabase();

        // Gather values
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, note.getDate());
        values.put(KEY_IMPORTANCE, note.getImportance());
        values.put(KEY_TITLE, note.getTitle());
        values.put(KEY_DESCRIPTION, note.getDescription());

        try {
            database.insert(TABLE_ENTRY, null, values);
        } catch (Exception e) {
            Log.e("Database", e.toString());
            throw new IllegalStateException("Database is not opened!");
        }

        database.close();
    }

    public int getNoteCount() throws IllegalStateException {
        // Get access to database
        SQLiteDatabase database = this.getReadableDatabase();

        int count = 0;

        try {
            count = (int) DatabaseUtils.queryNumEntries(database, TABLE_ENTRY);
        } catch (Exception e) {
            Log.e("Database", e.toString());
            throw new IllegalStateException("Database is not opened!");
        }

        database.close();
        return count;
    }

    public Note getNote(int id) {
        // Get access to database
        SQLiteDatabase database = this.getReadableDatabase();

        // Use cursor in order to run query
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_ENTRY + " WHERE " + KEY_ID + " = " + id, null);
        } catch (Exception e) {
            Log.e("Database", e.toString());
            throw new IllegalStateException("Database is not opened!");
        }

        // Select first element in the cursor
        cursor.moveToFirst();

        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                cursor.getInt(cursor.getColumnIndex(KEY_IMPORTANCE)),
                cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
        );

        // Closing database and returning formed entry object
        database.close();
        return note;
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<Note>();

        // Get access to database
        SQLiteDatabase database = this.getReadableDatabase();

        // Use cursor in order to run query
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_ENTRY, null);
        } catch (Exception e) {
            Log.e("Database", e.toString());
            throw new IllegalStateException("Database is not opened!");
        }

        // Loop through all rows and add them to the list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                        cursor.getInt(cursor.getColumnIndex(KEY_IMPORTANCE)),
                        cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                        cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                );

                notes.add(note);
            } while (cursor.moveToNext());
        }

        database.close();
        return notes;
    }


    public void removeNote(int id) {
        // Get access to database
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            database.delete(TABLE_ENTRY, KEY_ID + "=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.e("Database", e.toString());
            throw new IllegalStateException("Database is not opened!");
        }

        database.close();
    }
}
