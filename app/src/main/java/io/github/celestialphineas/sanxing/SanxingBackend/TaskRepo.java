package io.github.celestialphineas.sanxing.SanxingBackend;

/**
 * Created by apple on 2017/11/3.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import java.util.List;

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

        values.put(Task.KEY_TITLE,task.getTitle());
        values.put(Task.KEY_BEGIN_TIME,task.getBeginDate());
        values.put(Task.KEY_DESCRIPTION,task.getContent());
        values.put(Task.KEY_IMPORTANCE,task.getImportance());
        values.put(Task.KEY_STATE,task.getState());
        values.put(Task.KEY_END_TIME,task.getEndDate());

        // Inserting Row
        long task_Id = db.insert(Task.TABLE, null, values);
        task.ID= (int) task_Id;
        Log.e("task id : " , String.valueOf(task_Id));
        db.close(); // Closing database connection
        return (int) task_Id;
    }
    public void addAll(List<Task> taskList){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i=0;i<taskList.size();i++){

            values.put(Task.KEY_TITLE,taskList.get(i).getTitle());
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

        values.put(Task.KEY_TITLE,task.getTitle());
        values.put(Task.KEY_BEGIN_TIME,task.getBeginDate());
        values.put(Task.KEY_DESCRIPTION,task.getContent());
        values.put(Task.KEY_IMPORTANCE,task.getImportance());
        values.put(Task.KEY_STATE,task.getState());
        values.put(Task.KEY_END_TIME,task.getEndDate());


        // It's a good practice to use parameter ?, instead of concatenate string

        db.update(Task.TABLE, values, Task.KEY_ID + " = ?", new String[] { String.valueOf(task.ID) });
        Log.e("task sta "+task.getState(),"has changed in db");
        db.close(); // Closing database connection
    }

    public ArrayList<Task> getTaskList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                Task.KEY_ID + ", " +
                Task.KEY_TITLE+ ", " +Task.KEY_BEGIN_TIME+ ", " +Task.KEY_END_TIME+ ", " +Task.KEY_DESCRIPTION+ ", " +Task.KEY_IMPORTANCE+
                " FROM " + Task.TABLE  +" WHERE "+Task.KEY_STATE +" >0  ";

        ArrayList<Task> taskList = new ArrayList<Task>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(Task.KEY_ID)));
                String title = cursor.getString(cursor.getColumnIndex(Task.KEY_TITLE));
                String begin = cursor.getString(cursor.getColumnIndex(Task.KEY_BEGIN_TIME));
                String end=cursor.getString(cursor.getColumnIndex(Task.KEY_END_TIME));
                String n_description=cursor.getString(cursor.getColumnIndex(Task.KEY_DESCRIPTION));
                int n_importance=cursor.getInt(cursor.getColumnIndex(Task.KEY_IMPORTANCE));

                taskList.add(new Task(id,title,begin,end,n_description,n_importance));
                //taskList.add(new Task(content));


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
                Task.KEY_TITLE +
                " FROM " + Task.TABLE
                + " WHERE " +
                Task.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Task task = new Task();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                task.ID =cursor.getInt(cursor.getColumnIndex(Task.KEY_ID));
                task.setTitle(cursor.getString(cursor.getColumnIndex(Task.KEY_TITLE)));


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
