package com.natjen.android.shopping.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.natjen.android.shopping.Item;
import com.natjen.android.shopping.database.ItemsDbSchema.ItemTable;

import java.util.UUID;

public class ItemCursorWrapper extends CursorWrapper {
    public ItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Item getItem() {
        String id = getString(getColumnIndex(ItemTable.Cols.UUID));
        String what = getString(getColumnIndex(ItemTable.Cols.WHAT));
        String where = getString(getColumnIndex(ItemTable.Cols.WHERE));

        Item item = new Item(UUID.fromString(id));
        item.setWhat(what);
        item.setWhere(where);

        return item;
    }
}