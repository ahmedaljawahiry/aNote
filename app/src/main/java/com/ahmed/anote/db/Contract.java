package com.ahmed.anote.db;

import android.provider.BaseColumns;

public class Contract {

    private Contract() {}

    public static final class Pins implements BaseColumns {
        public static final String TABLE_NAME = "PINS";
        public static final String COLUMN_PIN = "PIN";
        public static final String COLUMN_KEY = "KEY";
        public static final String COLUMN_HINT = "HINT";
        public static final String COLUMN_SECURITY_LEVEL = "SECURITY_LEVEL";
    }

    public static final class Notes implements BaseColumns {
        public static final String TABLE_NAME = "NOTES";
        public static final String COLUMN_TITLE = "TITLE";
        public static final String COLUMN_TEXT = "TEXT";
        public static final String COLUMN_SECURITY_LEVEL = "SECURITY_LEVEL";
    }
}
