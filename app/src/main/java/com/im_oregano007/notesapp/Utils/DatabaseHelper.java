package com.im_oregano007.notesapp.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.im_oregano007.notesapp.Model.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    public static final String DATABASE_NAME="NOTES_DATABASE";
    public static final String TABLE_NAME="NOTES_TABLE";
    public static final String COL_1="ID";
    public static final String COL_2="TITLE";
    public static final String COL_3="CONTENT";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT , TITLE TEXT , CONTENT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertNote(NoteModel model){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2,model.getTitle());
        values.put(COL_3,model.getContent());
        db.insert(TABLE_NAME,null,values);
    }

    public void updateNote(int id, String title, String content){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2,title);
        values.put(COL_3,content);
        db.update(TABLE_NAME , values, "ID=?" , new String[]{String.valueOf(id)});
    }

    public void deleteNote(int id){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID=?" , new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<NoteModel> getAllNotes(){
        db = this.getWritableDatabase();
        List<NoteModel> modelList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try{
            cursor = db.query(TABLE_NAME,null,null,null,null,null,null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        NoteModel note = new NoteModel();
                        note.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        note.setTitle(cursor.getString(cursor.getColumnIndex(COL_2)));
                        note.setContent(cursor.getString(cursor.getColumnIndex(COL_3)));
                        modelList.add(note);

                    } while (cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            cursor.close();

        }

        return modelList;
    }

}
