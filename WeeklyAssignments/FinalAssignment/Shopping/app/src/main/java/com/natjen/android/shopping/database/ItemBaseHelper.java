package com.natjen.android.shopping.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.natjen.android.shopping.database.ItemsDbSchema.ItemTable;

public class ItemBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "itemBase.db";

    public ItemBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ItemTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ItemTable.Cols.UUID + ", " +
                ItemTable.Cols.WHAT + ", " +
                ItemTable.Cols.WHERE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Empty for now
    }
}