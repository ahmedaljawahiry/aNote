package com.ahmed.anote.db;

import android.provider.BaseColumns;

public class Contract {

    private Contract() {}

    public static final class Pins implements BaseColumns {
        public static final String TABLE_NAME = "Pins";
        public static final String COLUMN_PIN = "Pin";
        public static final String COLUMN_KEY = "Key";
        public static final String COLUMN_HINT = "Hint";
    }

    public static final class Notes implements BaseColumns {
        public static final String TABLE_NAME = "Notes";
        public static final String COLUMN_TITLE = "Title";
        public static final String COLUMN_TEXT = "Text";
    }
}
