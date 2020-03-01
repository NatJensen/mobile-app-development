package com.natjen.android.shopping;

import android.content.Context;
import android.content.Intent;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ShoppingUnitTest {

    ItemsDB itemsDB;
    Item t;
    Context context = null;

    @Test
    public void ItemsDBTest() {

        // Testing Item
        t = new Item("aa", "bb");
        assertEquals(t.getWhat(), "aa");
        assertEquals(t.getWhere(), "bb");
        t.setWhat("aaa");
        t.setWhere("bbb");
        assertEquals(t.getWhat(), "aaa");
        assertEquals(t.getWhere(), "bbb");
        assertEquals(t.toString(), "aaa in: bbb");

        // Testing ItemsDB
        itemsDB = new ItemsDB(context);

        // Empty database
        assertEquals(itemsDB.dbSize(), 0);

        // Testing listItems
        assertEquals(itemsDB.listItems(),
                "The shopping list is empty");

        // Database with a single Item
        itemsDB.addItem("x", "y");
        assertEquals(itemsDB.dbSize(), 1);
        assertEquals(itemsDB.get(0).getWhat(), "x");
        assertEquals(itemsDB.get(0).getWhere(), "y");
        assertEquals(itemsDB.listItems(), "Buy x in: y\n");

        itemsDB.fillItemsDB();
        assertEquals(itemsDB.dbSize(), 6);
        assertEquals(itemsDB.get(2).getWhat(), "carrots");
        assertEquals(itemsDB.get(2).getWhere(), "Netto");
        assertEquals(itemsDB.getItem("milk").toString(), "milk in: Netto");
        assertNull(itemsDB.getItem("chocolate"));

        // Delete first item
        itemsDB.deleteItem("coffee");
        assertEquals(itemsDB.listItems(),
                "Buy x in: y\n" +
                        "Buy carrots in: Netto\n" +
                        "Buy milk in: Netto\n" +
                        "Buy bread in: bakery\n" +
                        "Buy butter in: Irma\n");

        // Delete middle item
        itemsDB.deleteItem("bread");
        assertEquals(itemsDB.listItems(),
                "Buy x in: y\n" +
                        "Buy carrots in: Netto\n" +
                        "Buy milk in: Netto\n" +
                        "Buy butter in: Irma\n");

        // Delete last item
        itemsDB.deleteItem("x");
        assertEquals(itemsDB.listItems(),
                "Buy carrots in: Netto\n" +
                        "Buy milk in: Netto\n" +
                        "Buy butter in: Irma\n");

        // Delete absent item
        itemsDB.deleteItem("a");
        assertEquals(itemsDB.listItems(),
                "Buy carrots in: Netto\n" +
                        "Buy milk in: Netto\n" +
                        "Buy butter in: Irma\n");
    }
}