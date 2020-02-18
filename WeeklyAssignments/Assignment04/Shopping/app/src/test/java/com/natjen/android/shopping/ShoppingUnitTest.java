package com.natjen.android.shopping;

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

    @Test
    public void ItemsDBtest() {

        //Testing Item
        t = new Item("aa", "bb");
        assertEquals(t.getWhat(), "aa");
        assertEquals(t.getWhere(), "bb");
        t.setWhat("aaa");
        t.setWhere("bbb");
        assertEquals(t.getWhat(), "aaa");
        assertEquals(t.getWhere(), "bbb");
        assertEquals(t.toString(), "aaa in: bbb");

        //Testing ItemsDB
        itemsDB = new ItemsDB();

        //Empty database
        assertEquals(itemsDB.dbSize(), 0);

        //Database with a single Item
        itemsDB.addItem("x", "y");
        assertEquals(itemsDB.dbSize(), 1);
        assertEquals(itemsDB.get(0).getWhat(), "x");
        assertEquals(itemsDB.get(0).getWhere(), "y");
        assertEquals(itemsDB.listItems(), "\n Buy x in: y");

        itemsDB.fillItemsDB();
        assertEquals(itemsDB.dbSize(), 6);
        assertEquals(itemsDB.get(2).getWhat(), "carrots");
        assertEquals(itemsDB.get(2).getWhere(), "Netto");
    }
}