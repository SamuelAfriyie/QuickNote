package com.example.quicknote.core.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dao extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_NOTES = "notes";

    // Users Table Columns
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_IS_LOGIN = "is_login";

    // Notes Table Columns
    public static final String COLUMN_NOTE_ID = "note_id";
    public static final String COLUMN_NOTE_USER_ID = "user_id";
    public static final String COLUMN_NOTE_TITLE = "title";
    public static final String COLUMN_NOTE_DESC = "description";
    public static final String COLUMN_NOTE_DATE = "date";

    // SQL statements to create the tables
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_NAME + " TEXT, " +
                    COLUMN_USER_EMAIL + " TEXT, " +
                    COLUMN_USER_PASSWORD + " TEXT, " +
                    COLUMN_USER_IS_LOGIN + " INTEGER);";

    private static final String CREATE_TABLE_NOTES =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOTE_USER_ID + " INTEGER," +
                    COLUMN_NOTE_TITLE + " TEXT, " +
                    COLUMN_NOTE_DESC + " TEXT, " +
                    COLUMN_NOTE_DATE + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_NOTE_USER_ID + ") REFERENCES " +
                    TABLE_USERS + "(" + COLUMN_USER_ID + "));";

    public Dao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create both tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        // Create new tables
        onCreate(db);
    }

}
