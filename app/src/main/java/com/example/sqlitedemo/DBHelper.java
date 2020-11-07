package com.example.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DBName = "studentdb" ;
    private static final String TBName = "student" ;
    private static final int DBVERSION = 1;

    String place_name = "name";
    String place_usn ="usn" ;


    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DBName, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Table in this function
        String createSQL = "CREATE TABLE "+TBName +"("+place_name+" VARCHAR(10), "+place_usn+" INTEGER PRIMARY KEY);" ;
        db.execSQL(createSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TBName);
        onCreate(db);
    }


    public void insertStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();  // open the database in the writable format
        ContentValues cv = new ContentValues() ; // To prepare the data for insert (Key, value) key - column name, value
        cv.put(place_name,student.getName());
        cv.put(place_usn, student.getUsn());
        db.insert(TBName,null, cv);
        db.close();
    }

    public void deleteStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBName,place_usn +"=?", new String[]{String.valueOf(student.getUsn())}) ;
        db.close();
    }
}
