package io.github.celestialphineas.sanxing.SanxingBackend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.github.celestialphineas.sanxing.sxObject.Habit;
import io.github.celestialphineas.sanxing.sxObject.Task;
import io.github.celestialphineas.sanxing.sxObject.TimeLeft;

/**
 * Created by apple on 2017/12/10.
 */

public class HabitRepo {
    private DBHelper dbHelper;

    public HabitRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Habit habit) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //get values
        ContentValues values = new ContentValues();
        values.put(Habit.KEY_TITLE,habit.getTitle());
        values.put(Habit.KEY_BEGIN_TIME,habit.getBeginDate());
        values.put(Habit.KEY_DESCRIPTION,habit.getContent());
        values.put(Habit.KEY_IMPORTANCE,habit.getImportance());
        values.put(Habit.KEY_STATE,habit.getState());
        values.put(Habit.KEY_END_TIME,habit.getEndDate());

        values.put(Habit.KEY_FREQUENCY,habit.getFrequency());
        values.put(Habit.KEY_RECORDNUMBER,habit.getRecordnumber());
        values.put(Habit.KEY_NEEDNUMBER,habit.getNeednumber());

        values.put(Habit.KEY_NEXTDDL,habit.getNextddl());
        values.put(Habit.KEY_NEED_RECORD_ALL,habit.getNeed_record_all());
        values.put(Habit.KEY_HAVE_RECORD_ALL,habit.getHave_record_all());

        values.put(Habit.KEY_RECORD_LIST,habit.getRecordInString()+" ");
        // Inserting Row
        long habit_Id = db.insert(Habit.TABLE, null, values);
        Log.e("baba a ",habit.getRecordInString());
        db.close(); // Closing database connection
        return (int) habit_Id;
    }
    public void addAll(List<Habit> habitList){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i=0;i<habitList.size();i++){

            values.put(Habit.KEY_TITLE,habitList.get(i).getTitle());
            long habit_Id = db.insert(Habit.TABLE, null, values);
        }

        db.close(); // Closing database connection
    }
    //删除表中所有记录
    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Habit.TABLE, null, null);
        db.close(); // Closing database connection
    }
    public void delete(int habit_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Habit.TABLE, Habit.KEY_ID + "= ?", new String[] { String.valueOf(habit_Id) });
        db.close(); // Closing database connection
    }

    public void update(Habit habit) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Habit.KEY_TITLE,habit.getTitle());
        values.put(Habit.KEY_BEGIN_TIME,habit.getBeginDate());
        values.put(Habit.KEY_DESCRIPTION,habit.getContent());
        values.put(Habit.KEY_IMPORTANCE,habit.getImportance());
        values.put(Habit.KEY_STATE,habit.getState());
        values.put(Habit.KEY_END_TIME,habit.getEndDate());

        values.put(Habit.KEY_FREQUENCY,habit.getFrequency());
        values.put(Habit.KEY_RECORDNUMBER,habit.getRecordnumber());
        values.put(Habit.KEY_NEEDNUMBER,habit.getNeednumber());
        values.put(Habit.KEY_NEXTDDL,habit.getNextddl());
        values.put(Habit.KEY_NEED_RECORD_ALL,habit.getNeed_record_all());
        values.put(Habit.KEY_HAVE_RECORD_ALL,habit.getHave_record_all());
        Log.e("update",habit.getRecordInString()+"#");
        values.put(Habit.KEY_RECORD_LIST,habit.getRecordInString()+" ");

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Habit.TABLE, values, Habit.KEY_ID + "= ?", new String[] { String.valueOf(habit.ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<Habit> getHabitList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

//        Log.e("well","get habitlist");
        String selectQuery =  "SELECT  " +
                Habit.KEY_ID + ", " + Habit.KEY_TITLE+ ", " +Habit.KEY_BEGIN_TIME+ ", " +Habit.KEY_END_TIME+ ", " +Habit.KEY_DESCRIPTION+ ", " +Habit.KEY_IMPORTANCE+" , "+
                Habit.KEY_FREQUENCY +" , "+Habit.KEY_RECORDNUMBER +" , "+Habit.KEY_NEEDNUMBER +" , "+Habit.KEY_NEXTDDL +" , "+Habit.KEY_NEED_RECORD_ALL +" , "+Habit.KEY_HAVE_RECORD_ALL +" , "
                + Habit.KEY_RECORD_LIST +
        " FROM " + Habit.TABLE +" WHERE "+ Habit.KEY_STATE +" > 0  ";

        ArrayList<Habit> habitList = new ArrayList<Habit>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(Habit.KEY_ID)));
                String title = cursor.getString(cursor.getColumnIndex(Habit.KEY_TITLE));
                String begin = cursor.getString(cursor.getColumnIndex(Habit.KEY_BEGIN_TIME));

                String end=cursor.getString(cursor.getColumnIndex(Habit.KEY_END_TIME));
                String n_description=cursor.getString(cursor.getColumnIndex(Habit.KEY_DESCRIPTION));
                String n_nextddl=cursor.getString(cursor.getColumnIndex(Habit.KEY_NEXTDDL));

                Log.e("nextddl in habit",n_nextddl);

                int n_importance=cursor.getInt(cursor.getColumnIndex(Habit.KEY_IMPORTANCE));
                int n_frequency=cursor.getInt(cursor.getColumnIndex(Habit.KEY_FREQUENCY));
                int n_recordnumber=cursor.getInt(cursor.getColumnIndex(Habit.KEY_RECORDNUMBER));
                int n_neednumber=cursor.getInt(cursor.getColumnIndex(Habit.KEY_NEEDNUMBER));
                int n_need_all=cursor.getInt(cursor.getColumnIndex(Habit.KEY_NEED_RECORD_ALL));
                int n_have_all=cursor.getInt(cursor.getColumnIndex(Habit.KEY_HAVE_RECORD_ALL));

                String record_list_string = cursor.getString(cursor.getColumnIndex(Habit.KEY_RECORD_LIST));
                Log.e("database read list ",record_list_string+"#");
                String [] record_string = record_list_string.split(" ");
                List<Integer> record_list = new ArrayList<>();
                for (String ret : record_string ){
                    if (!ret.isEmpty()) record_list.add(Integer.valueOf(ret));
                }

                habitList.add(new Habit(id,title, begin, end, n_description, n_importance,n_frequency,n_recordnumber,
                        n_neednumber,n_nextddl,n_need_all,n_have_all,record_list));
                //habitList.add(new Habit("jjj"));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
//        List<Habit> test = new ArrayList<>();
//        test.add(new Habit("ddd"));
//        test.add(new Habit("ppp"));
//        test.add(new Habit("pop"));
        return habitList;

    }


    //获得habit表中的记录数
    public Long getCount(){

        String sql = "select count(*) from "+Habit.TABLE;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();
        return count;

    }
}
