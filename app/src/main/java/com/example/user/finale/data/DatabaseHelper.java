package com.example.user.finale.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.finale.data.Contract.diseaseEntry;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "diseases.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_DISEASES_TABLE = " CREATE TABLE " + diseaseEntry.TABLE_NAME + "("
                + diseaseEntry._ID + "INTEGER PRIMARY KEY,"
                + diseaseEntry.COLUMN_DISEASE_NAME + "TEXT NOT NULL);";

/*                + diseaseEntry.COLUMN_DISEASE_DESC + "TEXT,"
                + diseaseEntry.COLUMN_DISEASE_SYMPTOMS + "TEXT,"
                + diseaseEntry.COLUMN_DISEASE_TREATMENT + " TEXT"*/

        db.execSQL(SQL_CREATE_DISEASES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + diseaseEntry.TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(diseaseEntry.COLUMN_DISEASE_NAME, name);
/*        contentValues.put(diseaseEntry.COLUMN_DISEASE_DESC, desc);
        contentValues.put(diseaseEntry.COLUMN_DISEASE_SYMPTOMS, symptoms);
        contentValues.put(diseaseEntry.COLUMN_DISEASE_TREATMENT, treatment);*/
        long result = database.insert(diseaseEntry.TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else
            return true;
    }

    public Cursor viewLog() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor res = database.rawQuery("SELECT * FROM " + diseaseEntry.TABLE_NAME, null);
        return res;
    }
}
