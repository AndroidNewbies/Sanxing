package io.github.celestialphineas.sanxing.sxObject;

import org.threeten.bp.LocalDateTime;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private static final long serialVersionUID = 2L;
    public int task_ID;
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
        if (this_score<another_score) return -1;
        else if (this_score==another_score) return 0;
        else return 1;
    }

    /* 下面的是优先级表
     * 剩余一天还没完成时，得分都为0，意思是最该做的
     * 分为0到4五个重要级别，也就是五行
     * 每行的五列，分别表示，剩余一天，剩余三天，七天，十天，十五天
     * 意思是说，高一级别的剩余七天和第一级别剩余3天的重要性相同。
     */

    private static int[] priority={ 0,5,6,7,8,
            0,4,5,6,7,
            0,3,4,5,6,
            0,2,3,4,5,
            0,1,2,3,4 };
    private int score()
    {
        int day=0;
        LocalDateTime now=LocalDateTime.now();
        String nowString=now.toString();
        nowString=nowString.replace('T',' ');
        nowString=nowString.substring(0,19);
        String endString=this.getEndDate();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date nowDate=df.parse(nowString);
            Date endDate=df.parse(endString);
            long diff = endDate.getTime() - nowDate.getTime();//这样得到的差值是微秒级别
            day=(diff>0)?(int)(diff/1000/60/60/24):0;
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }

        int i=0;
        i=(day>=15)?4:(day>=10)?3:(day>=7)?2:(day>=3)?1:(day>=1)?1:0;
        return priority[getImportance()*5+i];
    }
}
