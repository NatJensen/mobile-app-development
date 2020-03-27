package com.natjen.android.shopping.database;

public class ItemsDbSchema {
    public static final class ItemTable {
        public static final String NAME = "items";

        public static final class Cols {
            public static final String UUID = "id";
            public static final String WHAT = "what";
            public static final String WHERE = "whereC";
        }
    }
}