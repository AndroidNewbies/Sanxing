package io.github.celestialphineas.sanxing.SanxingBackend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.github.celestialphineas.sanxing.sxObject.TimeLeft;

/**
 * Created by apple on 2017/12/10.
 */

public class TimeLeftRepo {

    private DBHelper dbHelper;

    public TimeLeftRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(TimeLeft timeLeft) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TimeLeft.KEY_TITLE, timeLeft.getTitle());
        values.put(TimeLeft.KEY_BEGIN_TIME, timeLeft.getBeginDate());
        values.put(TimeLeft.KEY_DESCRIPTION, timeLeft.getContent());
        values.put(TimeLeft.KEY_IMPORTANCE, timeLeft.getImportance());
        values.put(TimeLeft.KEY_STATE, timeLeft.getState());
        values.put(TimeLeft.KEY_END_TIME, timeLeft.getEndDate());

        // Inserting Row
        long timeLeft_Id = db.insert(TimeLeft.TABLE, null, values);
        timeLeft.ID = (int) timeLeft_Id;
        Log.e("timeLeft id : ", String.valueOf(timeLeft_Id));
        db.close(); // Closing database connection
        return (int) timeLeft_Id;
    }

    public void addAll(List<TimeLeft> timeLeftList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < timeLeftList.size(); i++) {

            values.put(TimeLeft.KEY_TITLE, timeLeftList.get(i).getTitle());
            long timeLeft_Id = db.insert(TimeLeft.TABLE, null, values);
        }

        db.close(); // Closing database connection
    }

    //删除表中所有记录
    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TimeLeft.TABLE, null, null);
        db.close(); // Closing database connection
    }

    public void delete(int timeLeft_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(TimeLeft.TABLE, TimeLeft.KEY_ID + "= ?", new String[]{String.valueOf(timeLeft_Id)});
        db.close(); // Closing database connection
    }

    public void update(TimeLeft timeLeft) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TimeLeft.KEY_TITLE, timeLeft.getTitle());
        values.put(TimeLeft.KEY_BEGIN_TIME, timeLeft.getBeginDate());
        values.put(TimeLeft.KEY_DESCRIPTION, timeLeft.getContent());
        values.put(TimeLeft.KEY_IMPORTANCE, timeLeft.getImportance());
        values.put(TimeLeft.KEY_STATE, timeLeft.getState());
        values.put(TimeLeft.KEY_END_TIME, timeLeft.getEndDate());


        // It's a good practice to use parameter ?, instead of concatenate string

        db.update(TimeLeft.TABLE, values, TimeLeft.KEY_ID + " = ?", new String[]{String.valueOf(timeLeft.ID)});
        Log.e("timeLeft sta " + timeLeft.getState(), "has changed in db");
        db.close(); // Closing database connection
    }

    public ArrayList<TimeLeft> getTimeLeftList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  " +
                TimeLeft.KEY_ID + ", " +
                TimeLeft.KEY_TITLE + ", " + TimeLeft.KEY_BEGIN_TIME + ", " + TimeLeft.KEY_END_TIME + ", " + TimeLeft.KEY_DESCRIPTION + ", " + TimeLeft.KEY_IMPORTANCE + ", " + TimeLeft.KEY_STATE +
                " FROM " + TimeLeft.TABLE + " WHERE " + TimeLeft.KEY_STATE + " > 0  ";

        ArrayList<TimeLeft> timeLeftList = new ArrayList<TimeLeft>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(TimeLeft.KEY_ID)));
                String title = cursor.getString(cursor.getColumnIndex(TimeLeft.KEY_TITLE));
                String begin = cursor.getString(cursor.getColumnIndex(TimeLeft.KEY_BEGIN_TIME));
                String end = cursor.getString(cursor.getColumnIndex(TimeLeft.KEY_END_TIME));
                String n_description = cursor.getString(cursor.getColumnIndex(TimeLeft.KEY_DESCRIPTION));
                int n_importance = cursor.getInt(cursor.getColumnIndex(TimeLeft.KEY_IMPORTANCE));
                int n_state = cursor.getInt(cursor.getColumnIndex(TimeLeft.KEY_STATE));
                timeLeftList.add(new TimeLeft(id, title, begin, end, n_description, n_importance, n_state));
                //timeLeftList.add(new TimeLeft(content));


            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return timeLeftList;

    }

    public TimeLeft getTimeLeftById(int Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                TimeLeft.KEY_ID + "," +
                TimeLeft.KEY_TITLE +
                " FROM " + TimeLeft.TABLE
                + " WHERE " +
                TimeLeft.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        TimeLeft timeLeft = new TimeLeft();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                timeLeft.ID = cursor.getInt(cursor.getColumnIndex(TimeLeft.KEY_ID));
                timeLeft.setTitle(cursor.getString(cursor.getColumnIndex(TimeLeft.KEY_TITLE)));


            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return timeLeft;
    }

    //获得timeLeft表中的记录数
    public Long getCount() {

        String sql = "select count(*) from " + TimeLeft.TABLE;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;

    }
}
