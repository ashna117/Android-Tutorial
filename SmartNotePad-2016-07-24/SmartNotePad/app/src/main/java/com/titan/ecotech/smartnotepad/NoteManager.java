package com.titan.ecotech.smartnotepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by User on 7/19/2016.
 */
public class NoteManager {

    private DataBaseHelper helper;
    private SQLiteDatabase database;
    private Context context;

    public NoteManager(Context context) {
        this.context = context;
        helper = new DataBaseHelper(context);

    }


    private void open() {

        database = helper.getWritableDatabase();
    }

    private void close() {
        helper.close();
    }

    public boolean addNote(Notepad notes) {

        this.open();

        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COL_TITLE, notes.getTitle());
        cv.put(DataBaseHelper.COL_DETAILS, notes.getDetails());
        cv.put(DataBaseHelper.COL_PRIORITY, notes.getPriority());
        cv.put(DataBaseHelper.COL_TIME, notes.getTime());
        cv.put(DataBaseHelper.COL_TASKDONE, notes.getTaskdone());

        long inserted = database.insert(DataBaseHelper.TABLE_NOTE, null, cv);
        this.close();

        database.close();

        if (inserted > 0) {
            return true;
        } else return false;

    }

    public boolean updateNote(int id, Notepad notes) {
        this.open();

        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COL_TITLE, notes.getTitle());
        cv.put(DataBaseHelper.COL_DETAILS, notes.getDetails());
        cv.put(DataBaseHelper.COL_PRIORITY, notes.getPriority());
        cv.put(DataBaseHelper.COL_TIME, notes.getTime());
        cv.put(DataBaseHelper.COL_TASKDONE, notes.getTaskdone());

        int updated = database.update(DataBaseHelper.TABLE_NOTE, cv, DataBaseHelper.COL_ID + " = " + id, null);
        this.close();
        if (updated > 0) {
            return true;
        } else
            return false;
    }

    public boolean deleteNote(int id) {
        this.open();
        int deleted = database.delete(DataBaseHelper.TABLE_NOTE, DataBaseHelper.COL_ID + " = " + id, null);
        this.close();
        if (deleted > 0) {
            return true;
        } else return false;


    }

    public ArrayList<Notepad> getAllNotes() {

        this.open();

        ArrayList<Notepad> noteList = new ArrayList<>();

        Cursor cursor = database.query(DataBaseHelper.TABLE_NOTE, null, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_ID));
                String title = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_TITLE));
                String detail = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_DETAILS));
                String priority = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_PRIORITY));
                String time = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_TIME));
                String taskdone = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_TASKDONE));

                Notepad mynote = new Notepad(id,title, detail,priority,time,taskdone );
                noteList.add(mynote);
                cursor.moveToNext();
            }
            this.close();

        }
        return noteList;
    }

    public Notepad getNote(int id) {

        this.open();

        Cursor cursor = database.query(DataBaseHelper.TABLE_NOTE, new String[]{DataBaseHelper.COL_ID, DataBaseHelper.COL_TITLE, DataBaseHelper.COL_DETAILS,
                        DataBaseHelper.COL_PRIORITY, DataBaseHelper.COL_TIME, DataBaseHelper.COL_TASKDONE },
                DataBaseHelper.COL_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();

        int mId = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_ID));
        String title = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_TITLE));
        String detail = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_DETAILS));
        String priority = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_PRIORITY));
        String time = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_TIME));
        String taskdone = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_TASKDONE));

        Notepad mynote = new Notepad(id,title, detail,priority,time,taskdone );

        this.close();
        return mynote;
    }

}
