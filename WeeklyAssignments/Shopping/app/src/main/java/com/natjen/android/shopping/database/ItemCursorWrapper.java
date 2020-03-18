package com.natjen.android.shopping.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.natjen.android.shopping.Item;
import com.natjen.android.shopping.database.ItemsDbSchema.ItemTable;

public class ItemCursorWrapper extends CursorWrapper {
    public ItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Item getItem() {

        String what = getString(getColumnIndex(ItemTable.Cols.WHAT));
        String whereC = getString(getColumnIndex(ItemTable.Cols.WHEREC));

        return null;

    }

}
