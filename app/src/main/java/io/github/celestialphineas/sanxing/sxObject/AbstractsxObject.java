package io.github.celestialphineas.sanxing.sxObject;


import android.util.Log;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import io.github.celestialphineas.sanxing.timer.MyDuration;

/**
 * Created by lin on 17-11-1.
 * Date format:yyyy-MM-dd
 * Time format: 12:02
 *              12:02:00
 *              12:02:00.213
 */

public abstract class AbstractsxObject {
    private String title;
    private LocalDateTime begin;
    private LocalDateTime end;
    private int state;// Is finished,0,delete,1,unfinished,2,finished
    private String content;
    private int importance;
    public int ID;
    AbstractsxObject()
    {
        //initial to now
        ID=0;
        title="object";
        state=1;
        content="";
        importance=0;
        begin=LocalDateTime.now();
        end=LocalDateTime.now().plusDays(10);
    }
    public void create_object(String title,String begindate,String enddate,String content,int importance)
    {
        setState(1);//first create, set it to be active
        setTitle(title);
        setBeginDate(begindate);
        setEndDate(enddate);
        setContent(content);
        setImportance(importance);
    }
    public void edit_object(String title,String enddate,String content,int importance)
    {
        setTitle(title);
        setEndDate(enddate);
        setContent(content);
        setImportance(importance);
    }
    public void setTitle(String title)
    {
        this.title=title;
    }
    public String getTitle()
    {
        return this.title;
    }
    public void setBeginDate(String date)
    {
        if (date.length()==16) date=date.concat(":00");
        if (date.length()>19) date=date.substring(0,19);
        DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        begin= LocalDateTime.parse(date, sf);
        //Log.d("begindate",begin.toString());
    }
    public void setBeginDate(int year,int month,int day,int hour,int minute,int second)
    {
        begin=LocalDateTime.of(year,month,day,hour,minute,second);
    }
    public String getBeginDate()
    {
        return MyDuration.LocalDateTime_to_String(begin);
    }
    public LocalDateTime getBeginLocalDate()
    {
        return begin;
    }
    public void setEndDate(String date)
    {
        if (date.length()==16) date=date.concat(":00");
        if (date.length()>19) date=date.substring(0,19);
        DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        end=LocalDateTime.parse(date, sf);
        //Log.d("enddate",end.toString());
    }
    public void setEndDate(LocalDateTime date)
    {
        end=date;
        //Log.d("enddate",end.toString());
    }
    public void setEndDate(int year,int month,int day,int hour,int minute,int second)
    {
        end = LocalDateTime.of(year, month, day, hour, minute, second);
    }
    public String getEndDate()
    {
        return MyDuration.LocalDateTime_to_String(end);
    }
    public LocalDateTime getEndLocalDate()
    {
        return end;
    }
    public void setContent(String text)
    {
        content=text;
    }
    public String getContent()
    {
        return content;
    }
    public void setState(int newstate)
    {
        state= newstate;
    }
    public int getState()
    {
        return state;
    }
    public int checkState()
    {
        return state;
    }
    public void setImportance(int i){importance=i;}
    public int getImportance(){return importance;}


}
