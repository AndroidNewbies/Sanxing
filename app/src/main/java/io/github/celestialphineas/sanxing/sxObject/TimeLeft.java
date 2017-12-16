package io.github.celestialphineas.sanxing.sxObject;

import java.io.Serializable;

import io.github.celestialphineas.sanxing.timer.MyDuration;

/**
 * Created by lin on 2017/11/4.
 */

public class TimeLeft extends AbstractsxObject implements Serializable,Comparable<TimeLeft>{

    //database tag
    public static final String KEY_ID = "id";
    public static final String TABLE = "TimeLeft";//this name can't be changed
    public static final String KEY_TITLE = "title";
    public static final String KEY_IMPORTANCE = "importance";
    public static final String KEY_BEGIN_TIME = "begin_time";
    public static final String KEY_END_TIME = "end_time";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_STATE = "state";

    private static final long serialVersionUID = 2L;

    public TimeLeft()
    {
        super();
    }
    public TimeLeft(String title)
    {
        super();
        super.setTitle(title);
    }
    //this constructor only used when read data from the database
    public TimeLeft(int id,String title,String begindate,String enddate,String content,int important,int state)
    {
        ID  = id;//set the ID in the abstract class
        create_timeleft(title, begindate, enddate, content, important);
        setState(state);
    }
    public void create_timeleft(String title,String begindate,String enddate,String content,int important)
    {
        super.create_object(title, begindate, enddate, content, important);
    }
    public void edit_timeleft(String title,String enddate,String content,int important)
    {
        super.edit_object(title, enddate, content, important);
    }
    //余时只按照重要程度排序
    @Override
    public int compareTo(TimeLeft another_left)
    {
        int this_weight=this.getImportance();
        int another_weight=another_left.getImportance();
        if (this_weight!=another_weight)
        {
            if (this_weight>another_weight) return -1;
            return 1;
        }
        else
        {
            long left1= MyDuration.durationFromNowtoB(this.getEndDate());
            long left2= MyDuration.durationFromNowtoB(another_left.getEndDate());
            if (left1<left2) return -1;
            else return 1;
        }
    }
}
