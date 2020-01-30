package com.natjen.android.shopping;

import java.util.ArrayList;
import java.util.List;

public class ItemsDB {
  private List<Item> ItemsDB;

  public ItemsDB() { ItemsDB= new ArrayList<>(); }

  public String listItems() {
    String r= "";
    for (int i= 0; i<ItemsDB.size(); i++) {
      r= r+"\n Buy "+ItemsDB.get(i).toString();
    }
    return r;
  }

  public void fillItemsDB() {
    ItemsDB.add(new Item("coffee", "Irma"));
    ItemsDB.add(new Item("carrots", "Netto"));
    ItemsDB.add(new Item("milk", "Netto"));
    ItemsDB.add(new Item("bread", "bakery"));
    ItemsDB.add(new Item("butter", "Irma"));
  }
}
