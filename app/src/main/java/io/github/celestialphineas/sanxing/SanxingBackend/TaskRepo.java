package io.github.celestialphineas.sanxing.SanxingBackend;

/**
 * Created by apple on 2017/11/3.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import java.util.List;

import io.github.celestialphineas.sanxing.SanxingBackend.DBHelper;
import io.github.celestialphineas.sanxing.sxObject.Task;


//封装了对sqlite数据库的增删改查操作 目前只是针对task表 后续应考虑扩充
public class TaskRepo {
    private DBHelper dbHelper;

    public TaskRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Task task) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Task.KEY_content,task.getContent());

        // Inserting Row
        long task_Id = db.insert(Task.TABLE, null, values);
       // db.notify();
        db.close(); // Closing database connection
        return (int) task_Id;
    }
    public void addAll(List<Task> taskList){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i=0;i<taskList.size();i++){

            values.put(Task.KEY_content,taskList.get(i).getContent());
            long task_Id = db.insert(Task.TABLE, null, values);
        }

        db.close(); // Closing database connection
    }
    //删除表中所有记录
    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Task.TABLE, null, null);
        db.close(); // Closing database connection
    }
    public void delete(int task_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Task.TABLE, Task.KEY_ID + "= ?", new String[] { String.valueOf(task_Id) });
        db.close(); // Closing database connection
    }

    public void update(Task task) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Task.KEY_content, task.getContent());


        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Task.TABLE, values, Task.KEY_ID + "= ?", new String[] { String.valueOf(task.task_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<Task> getTaskList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Task.KEY_ID + "," +
                Task.KEY_content +
                " FROM " + Task.TABLE;

        //Student student = new Student();
        ArrayList<Task> taskList = new ArrayList<Task>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                //HashMap<String, Task> task = new HashMap<String, Task>();
                //task.put("id", new Task(cursor.getString(cursor.getColumnIndex(Task.KEY_ID))));
                //task.put("content", new Task(cursor.getString(cursor.getColumnIndex(Task.KEY_content))));
                taskList.add(new Task(cursor.getString(cursor.getColumnIndex(Task.KEY_content))));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;

    }

    public Task getTaskById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Task.KEY_ID + "," +
                Task.KEY_content +
                " FROM " + Task.TABLE
                + " WHERE " +
                Task.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Task task = new Task();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                task.task_ID =cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                task.setContent(cursor.getString(cursor.getColumnIndex(Task.KEY_content)));


            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return task;
    }

    //获得task表中的记录数
    public Long getCount(){

            String sql = "select count(*) from "+Task.TABLE;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            long count = cursor.getLong(0);
            cursor.close();
            return count;

    }

}