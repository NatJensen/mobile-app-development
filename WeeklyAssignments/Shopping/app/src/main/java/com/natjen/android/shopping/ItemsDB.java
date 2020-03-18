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

class ItemsDB extends Observable {
    private static ItemsDB sItemsDB;

    private Context mContext;
    private static SQLiteDatabase mDatabase;

    public  static ItemsDB get(Context context) {
        if (sItemsDB == null) {
            mDatabase = new ItemBaseHelper(context.getApplicationContext()).getWritableDatabase();
            sItemsDB = new ItemsDB(context);
        }
        return sItemsDB;
    }

    ItemsDB(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ItemBaseHelper(mContext).getWritableDatabase();
    }

    Item get(int i) {
        return null;
    }

    void addItem(Item item) {
        /*mItemsDB.add(new Item(what, where));
        this.setChanged();
        notifyObservers();*/

        ContentValues values = getContentValues(item);

        mDatabase.insert(ItemTable.NAME, null, values);
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

    /*void fillItemsDB() {
        mItemsDB.add(new Item("coffee", "Irma"));
        mItemsDB.add(new Item("carrots", "Netto"));
        mItemsDB.add(new Item("milk", "Netto"));
        mItemsDB.add(new Item("bread", "bakery"));
        mItemsDB.add(new Item("butter", "Irma"));

        this.setChanged();
        notifyObservers();
    }*/

    void deleteItem(String what) {
        /*for (Item item : mItemsDB) {
            if (item.getWhat().equals(what)) {
                mItemsDB.remove(item);
                break;
            }
        }*/

        this.setChanged();
        notifyObservers();
    }

    Item getItem(String what) {
        /*for (Item item : mItemsDB) {
            if (item.getWhat().equals(what)) {
                return item;
            }
        }*/
        return null;
    }

    int dbSize() {
        return 0;
    }

    public void updateItem(Item item) {
        String what = item.getWhat();
        ContentValues values = getContentValues(item);

        mDatabase.update(ItemTable.NAME, values, ItemTable.Cols.WHAT + " = ?", new String[]{what});
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
        values.put(ItemTable.Cols.WHEREC, item.getWhere());

        return values;
    }
}
