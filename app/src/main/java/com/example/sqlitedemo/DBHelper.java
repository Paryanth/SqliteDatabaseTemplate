package com.example.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    // Update
    public int updateStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase() ; // Open the database in writable format
        ContentValues cv = new ContentValues() ; // prepare the data as set of key-value pairs
        cv.put(place_name,student.getName());
        cv.put(place_usn, student.getUsn());
        return db.update(TBName,cv,place_usn+="?",new String[]{String.valueOf(student.getUsn())});
    }

    Student getStudent(int usn){
        SQLiteDatabase db = this.getReadableDatabase() ;

        Cursor cursor = db.query(
                TBName, new String[]{place_name,place_usn},place_usn+="?",new String[]{String.valueOf(usn)},null,
                null,null);

        Student student = new Student(cursor.getString(0), cursor.getInt(1));
        return student;
    }

    public int getStudentCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TBName, null);
        cursor.close();
        return cursor.getCount();
    }

    public List<Student> getAllStudent(){

        List<Student> studentlist = new ArrayList<Student>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TBName, null);
        if(cursor.moveToFirst()){
            do{
                Student student = new Student();
                student.setName(cursor.getString(0));
                student.setUsn(cursor.getInt(1));

                studentlist.add(student);
            }while (cursor.moveToNext());
        }
        return  studentlist;
    }

}
