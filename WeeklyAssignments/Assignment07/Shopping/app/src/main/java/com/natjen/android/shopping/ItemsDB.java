package com.natjen.android.shopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.natjen.android.shopping.database.ItemBaseHelper;
import com.natjen.android.shopping.database.ItemCursorWrapper;
import com.natjen.android.shopping.database.ItemsDbSchema.ItemTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

class ItemsDB extends Observable {
    private static ItemsDB sItemsDB;

    private static SQLiteDatabase mDatabase;

    public static ItemsDB get(Context context) {
        if (sItemsDB == null) {
            sItemsDB = new ItemsDB(context);
        }
        return sItemsDB;
    }

    private ItemsDB(Context context) {
        mDatabase = new ItemBaseHelper(context.getApplicationContext()).getWritableDatabase();
    }

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();

        ItemCursorWrapper cursor = queryItems(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                items.add(cursor.getItem());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return items;
    }

    public void addItem(String what, String where) {
        Item newItem = new Item(what, where);
        ContentValues values = getContentValues(newItem);
        mDatabase.insert(ItemTable.NAME, null, values);
        this.setChanged();
        notifyObservers();
    }

    public void deleteItem(String what) {
        mDatabase.delete(ItemTable.NAME, ItemTable.Cols.WHAT + "=?", new String[]{what});
        this.setChanged();
        notifyObservers();
    }

    private ItemCursorWrapper queryItems(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ItemTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return new ItemCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Item item) {
        ContentValues values = new ContentValues();
        values.put(ItemTable.Cols.WHAT, item.getWhat());
        values.put(ItemTable.Cols.WHERE, item.getWhere());

        return values;
    }
}
