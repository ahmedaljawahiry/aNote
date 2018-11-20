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
}
