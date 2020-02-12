package com.natjen.android.shopping;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ItemsDB {
    private static ItemsDB sItemsDB;

    private List<Item> mItemsDB;

    public static ItemsDB get(Context context) {
        if (sItemsDB == null) {
            sItemsDB = new ItemsDB(context);
        }

        return sItemsDB;
    }

    private ItemsDB(Context context) {
        mItemsDB = new ArrayList<>();
    }

    public String listItems() {
        String r = "";
        for (int i = 0; i < mItemsDB.size(); i++) {
            r = r + "\n Buy " + mItemsDB.get(i).toString();
        }
        return r;
    }

    public void addItem(String what, String where) {
        mItemsDB.add(new Item(what, where));
    }

    public void fillItemsDB() {
        mItemsDB.add(new Item("coffee", "Irma"));
        mItemsDB.add(new Item("carrots", "Netto"));
        mItemsDB.add(new Item("milk", "Netto"));
        mItemsDB.add(new Item("bread", "bakery"));
        mItemsDB.add(new Item("butter", "Irma"));
    }

    public void deleteItem(Item item) {
        mItemsDB.remove(item);
    }

    public Item getItem(String what) {
        for (Item item : mItemsDB) {
            if (item.getWhat().equals(what)) {
                return item;
            }
        }
        return null;
    }

    public int dbSize() {
        return mItemsDB.size();
    }
}
