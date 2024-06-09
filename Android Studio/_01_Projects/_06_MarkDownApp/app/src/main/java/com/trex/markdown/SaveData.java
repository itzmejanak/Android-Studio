package com.trex.markdown;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SaveData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MarkdownFiles.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Files";
    private static final String COLUMN_FILE_PATH = "FilePath";

    public SaveData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_FILE_PATH + " TEXT PRIMARY KEY)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void createSession(String filePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FILE_PATH, filePath);
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    public ArrayList<String> getAllPreferences() {
        ArrayList<String> filePaths = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_FILE_PATH}, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String filePath = cursor.getString(cursor.getColumnIndex(COLUMN_FILE_PATH));
                filePaths.add(filePath);
            }
            cursor.close();
        }
        db.close();
        return filePaths;
    }
}
