package com.ilp.tcs.sitesafety.utils;

/**
 * Created by Azim Ansari on 5/26/2016.
 * Basic Sqlite datatypes
 */
public enum SQLiteDataTypes {
    TYPE_TEXT(" TEXT "),
    TYPE_INTEGER(" INTEGER "),
    TYPE_NUMERIC(" NUMERIC "),
    TYPE_REAL(" REAL "),
    TYPE_BOOLEAN(" BOOLEAN "),
    TYPE_BLOB(" BLOB "),
    TYPE_DATE(" DATE "),
    TYPE_DATE_TIME(" DATETIME ");

    /**
     * Data type
     */
    private final String type;

    SQLiteDataTypes(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
