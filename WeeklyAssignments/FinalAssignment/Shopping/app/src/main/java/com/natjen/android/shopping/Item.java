package com.natjen.android.shopping;

import java.util.UUID;

public class Item {
    private UUID mId;
    private String mWhat;
    private String mWhere;

    public Item(String what, String where) {
        this(UUID.randomUUID());
        mWhat = what;
        mWhere = where;
    }

    public Item(UUID id) {
        mId = id;
    }

    @Override
    public String toString() {
        return oneLine("", " in: ");
    }

    public UUID getId() {
        return mId;
    }

    public String getWhat() {
        return mWhat;
    }

    public void setWhat(String what) {
        mWhat = what;
    }

    public String getWhere() {
        return mWhere;
    }

    public void setWhere(String where) {
        mWhere = where;
    }

    public String oneLine(String pre, String post) {
        return pre + mWhat + post + mWhere;
    }
}