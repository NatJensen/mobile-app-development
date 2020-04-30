package com.natjen.android.shopping;

import java.util.UUID;

public class Item {
    private UUID mId;
    private String mWhat;
    private String mWhere;
    private int mSyncStatus;

    public Item(String what, String where) {
        this(UUID.randomUUID());
        this.setSyncStatus(mSyncStatus);
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

    public int getSyncStatus() {
        return mSyncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        mSyncStatus = syncStatus;
    }

    public String oneLine(String pre, String post) {
        return pre + mWhat + post + mWhere;
    }
}