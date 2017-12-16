package io.github.celestialphineas.sanxing.sxObject;

import android.util.Log;

import org.threeten.bp.LocalDateTime;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.celestialphineas.sanxing.timer.MyDuration;

/**
 * Created by lin on 2017/11/3.
 *
 * 任务开始时间
 * 结束时间
 * 重要程度
 * 提醒间隔
 *
 */

public class Task extends AbstractsxObject implements Serializable,Comparable<Task>
{
    public static final String KEY_ID = "id";
    public static final String TABLE = "Task";//this name can't be changed
    public static final String KEY_TITLE = "title";
    public static final String KEY_IMPORTANCE = "importance";
    public static final String KEY_BEGIN_TIME = "begin_time";
    public static final String KEY_END_TIME = "end_time";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_STATE = "state";

    private static final long serialVersionUID = 2L;
    // Labels Table Columns names

    public Task()
    {
        super();
    }
    public Task(String title)
    {
        super();
        super.setTitle(title);
    }
    public Task(String title,String begindate,String enddate,String content,int important)
    {
        create_task(title, begindate, enddate, content, important);
    }
    //this constructor only used when read data from the database
    public Task(int id,String title,String begindate,String enddate,String content,int important,int state)
    {
        ID  = id;//set the ID in the abstract class
        create_task(title, begindate, enddate, content, important);
        setState(state);// attention: setState must be called after create_task. create_task will set state to be default 1!
    }
    //创建时使用
    public void create_task(String title,String begindate,String enddate,String content,int important)
    {
        super.create_object(title, begindate, enddate, content, important);
    }
    //编辑使用
    public void edit_task(String title,String enddate,String content,int important)
    {
        super.edit_object(title, enddate, content, important);
    }
    //判断超时
    public boolean istimeout()
    {
        LocalDateTime now=LocalDateTime.now();
        return !now.isBefore(super.getEndLocalDate());
    }
    //点击完成
    public void accomplish()
    {
        super.setState(2);
    }
    @Override
    public int compareTo(Task another_task)
    {
        int this_score=this.score();
        int another_score=another_task.score();
        Log.d("score",String.valueOf(this_score)+" "+String.valueOf(another_score));
        if (this_score<another_score) return -1;
        else if (this_score==another_score)
        {
            long left1=MyDuration.durationFromNowtoB(this.getEndDate());
            long left2=MyDuration.durationFromNowtoB(another_task.getEndDate());
            if (left1 < left2) return -1;
            else return 1;
        }
        else return 1;
    }

    /* 下面的是优先级表
     * 剩余一天还没完成时，得分都为0，意思是最该做的
     * 分为0到4五个重要级别，也就是五行
     * 每行的五列，分别表示，剩余一天，剩余三天，七天，十天，十五天
     * 意思是说，高一级别的剩余七天和低一级别剩余3天的重要性相同。
     */

    private static int[] priority={
            0,5,7,9,11,
            0,4,6,8,10,
            0,3,5,7,9,
            0,2,4,6,8,
            0,1,3,5,7 };
    public int score()
    {
        int day=0;
        long diff= MyDuration.durationFromNowtoB(this.getEndDate());
        day=(diff>0)?(int)(diff/1000/60/60/24):0;
        int i=0;
        i=(day>=15)?4:(day>=10)?3:(day>=7)?2:(day>=3)?1:(day>=1)?1:0;
        return priority[getImportance()*5+i];
    }
}
