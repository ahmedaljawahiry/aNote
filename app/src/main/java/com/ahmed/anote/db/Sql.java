package com.ahmed.anote.db;

public class Sql {

    private Sql(){}

    public static final String CREATE_PINS_TABLE =
            "CREATE TABLE " + Contract.Pins.TABLE_NAME + " (" +
             Contract.Pins._ID + " INTEGER PRIMARY KEY," +
             Contract.Pins.COLUMN_PIN + " INTEGER," +
             Contract.Pins.COLUMN_KEY + " TEXT, " +
             Contract.Pins.COLUMN_HINT + " TEXT)";

    public static final String DELETE_PINS_TABLE =
            "DROP TABLE IF EXISTS " + Contract.Pins.TABLE_NAME;
}
