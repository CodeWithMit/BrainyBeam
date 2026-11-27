package com.example.brainybeam;

public class ConstantName {
    public static String DATABASE_NAME = "BrainyBeam.db";
    public static String TABLE_NAME = "users";
    public static String COLUMN_ID = "id";
    public static String COLUMN_NAME = "name";
    public static String COLUMN_EMAIL = "email";
    public static String COLUMN_CONTACT = "contact";
    public static String COLUMN_PASSWORD = "password";
    public static String CREATE_TABLE_QUERY = " CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_CONTACT + " TEXT, " +
            COLUMN_PASSWORD + " TEXT)";


    public static String SHARED_PREF_NAME = "login";
}
