package com.myapplicationdev.android.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 15017117 on 25/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASK = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASKNAME = "taskName";
    private static final String COLUMN_DESCRIPTION = "description";

    public DBHelper(Context context){super(context,DATABASE_NAME,null,DATABASE_VERSION);}

    public void onCreate(SQLiteDatabase db){
        String createNoteTableSql = "CREATE TABLE "+TABLE_TASK+"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +COLUMN_TASKNAME+ " TEXT, "
                +COLUMN_DESCRIPTION+ " TEXT )";

        db.execSQL(createNoteTableSql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }
    public long insertTask(String taskName,String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASKNAME,taskName);
        values.put(COLUMN_DESCRIPTION,description);
        long add = db.insert(TABLE_TASK,null,values);
        db.close();
        Log.d("SQL Insert",""+add);
        return add;
    }
    public Cursor getTask(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_TASK + " WHERE id = " + id, null);
        return res;
    }

    public ArrayList<String> getTasks(){
        ArrayList<String> task = new ArrayList<String>();
        String selectQuery = "SELECT * FROM "+TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        // Run the SQL query and get back the Cursor object
        Cursor cursor = db.rawQuery(selectQuery, null);
        // moveToFirst() moves to first row
        if (cursor.moveToFirst()) {
            // Loop while moveToNext() points to next row and returns true;
            // moveToNext() returns false when no more next row to move to
            do {

                task.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();

        return task ;
    }
public ArrayList<Task> getAllTask(){
    ArrayList<Task> tasks = new ArrayList<Task>();


    String selectQuery = "SELECT " + COLUMN_ID + ","
            + COLUMN_TASKNAME + "," + COLUMN_DESCRIPTION +" FROM " + TABLE_TASK;

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    if (cursor.moveToFirst()) {
        do {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(2);

            Task obj = new Task(id,name,desc);
            tasks.add(obj);
        } while (cursor.moveToNext());
    }
    cursor.close();
    db.close();
    return tasks;
}

}
