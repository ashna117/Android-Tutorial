package com.titan.ecotech.smartnotepad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 7/19/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NotebookDb";

    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_DETAILS = "details";
    public static final String COL_PRIORITY = "priority";
    public static final String COL_TIME = "time";
    public static final String COL_TASKDONE = "taskdone";
    public static final String TABLE_NOTE = "note";

    private static final String CREATE_NOTE_TABLE = " CREATE TABLE " + TABLE_NOTE +
            "( " + COL_ID + " INTEGER PRIMARY KEY," +
            COL_TITLE + " TEXT, "
            + COL_DETAILS + " TEXT, "
            + COL_PRIORITY + " INTEGER, "
            + COL_TIME + " TEXT, "
            + COL_TASKDONE + " TEXT )";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
