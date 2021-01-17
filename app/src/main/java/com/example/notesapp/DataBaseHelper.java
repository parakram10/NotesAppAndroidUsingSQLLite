package com.example.notesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String NOTES_TABLE = "NOTES_TABLE";
    public static final String COLUMN_NOTE_TITLE = "NOTE_TITLE";
    public static final String COLUMN_NOTE_DESC = "NOTE_DESC";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "notes.db", null,  1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNotesDataBase = "CREATE TABLE " + NOTES_TABLE +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOTE_TITLE + " TEXT, "
                + COLUMN_NOTE_DESC + " TEXT)";

        db.execSQL(createNotesDataBase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addNote(NotesModel notesModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NOTE_TITLE, notesModel.getTitle());
        contentValues.put(COLUMN_NOTE_DESC, notesModel.getDescription());

        long insert = db.insert(NOTES_TABLE, null, contentValues);

        if(insert == -1) return false;
        else return true;
    }

    public List<NotesModel> getAllNotes(){
        List<NotesModel> notesModels = new ArrayList<>();

        String queryString = "SELECT * FROM "+ NOTES_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String title = cursor.getString(1);
                String desc = cursor.getString(2);
                int id = cursor.getInt(0);
                NotesModel notesModel = new NotesModel(title, desc, id);

                notesModels.add(notesModel);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return notesModels;
    }

    public boolean deleteNote(NotesModel notesModel){

        SQLiteDatabase db = this.getWritableDatabase();

        String queryString = "DELETE FROM "+ NOTES_TABLE+" WHERE " + COLUMN_ID +" = " + notesModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()) return true;
        else return false;
    }
}
