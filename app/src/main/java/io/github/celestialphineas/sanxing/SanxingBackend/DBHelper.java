package io.github.celestialphineas.sanxing.SanxingBackend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.github.celestialphineas.sanxing.sxObject.Habit;
import io.github.celestialphineas.sanxing.sxObject.Task;
import io.github.celestialphineas.sanxing.sxObject.TimeLeft;

/**
 * Created by apple on 2017/11/3.
 */

public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "sanxing.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create necessary tables: Task / Habit / TimeLeft

        String CREATE_TABLE_TASK = "CREATE TABLE " + Task.TABLE  + "("
                + Task.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Task.KEY_TITLE +" TEXT, "+ Task.KEY_BEGIN_TIME +" TEXT, "+Task.KEY_END_TIME +" TEXT, "+Task.KEY_DESCRIPTION +" TEXT,  "+Task.KEY_STATE +" INTEGER, "+Task.KEY_IMPORTANCE+" INTEGER )";

        String CREATE_TABLE_HABIT = "CREATE TABLE " + Habit.TABLE  + "("
                + Habit.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Habit.KEY_TITLE +" TEXT, "+ Habit.KEY_BEGIN_TIME +" TEXT, "+Habit.KEY_END_TIME +" TEXT, "+Habit.KEY_DESCRIPTION +" TEXT,  "+Habit.KEY_STATE +" INTEGER, "+Habit.KEY_IMPORTANCE+" INTEGER , "
                +Habit.KEY_FREQUENCY +" INTEGER, "+Habit.KEY_RECORDNUMBER +" INTEGER, "+Habit.KEY_NEEDNUMBER +" INTEGER, "+Habit.KEY_NEXTDDL +" TEXT, "+Habit.KEY_NEED_RECORD_ALL +" INTEGER, "+Habit.KEY_HAVE_RECORD_ALL +" INTEGER , "+Habit.KEY_RECORD_LIST +" TEXT ) ";

        String CREATE_TABLE_TIMELEFT = "CREATE TABLE " + TimeLeft.TABLE  + "("
                + TimeLeft.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + TimeLeft.KEY_TITLE +" TEXT, "+ TimeLeft.KEY_BEGIN_TIME +" TEXT, "+TimeLeft.KEY_END_TIME +" TEXT, "+TimeLeft.KEY_DESCRIPTION +" TEXT,  "+TimeLeft.KEY_STATE +" INTEGER, "+TimeLeft.KEY_IMPORTANCE+" INTEGER )";

        //exec the sql order
        db.execSQL(CREATE_TABLE_TASK);
        db.execSQL(CREATE_TABLE_HABIT);
        db.execSQL(CREATE_TABLE_TIMELEFT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Task.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Habit.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TimeLeft.TABLE);

        // Create tables again
        onCreate(db);

    }

}
