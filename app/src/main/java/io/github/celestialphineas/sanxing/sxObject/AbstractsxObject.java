package io.github.celestialphineas.sanxing.sxObject;


import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

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
    private boolean state;// Is finished
    private String content;
    private int important;
    AbstractsxObject()
    {
        //initial to now
        title="object";
        state=false;
        content="";
        important=0;
    }
    public void create_object(String title,String begindate,String enddate,String content,int important)
    {
        setTitle(title);
        setBeginDate(begindate);
        setEndDate(enddate);
        setContent(content);
        setImportant(important);
    }
    public void edit_object(String title,String enddate,String content,int important)
    {
        setTitle(title);
        setEndDate(enddate);
        setContent(content);
        setImportant(important);
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
        DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        begin= LocalDateTime.parse(date, sf);
    }
    public void setBeginDate(int year,int month,int day,int hour,int minute,int second)
    {
        begin=LocalDateTime.of(year,month,day,hour,minute,second);
    }
    public String getBeginDate()
    {
        String s=begin.toString();
        s=s.replace('T',' ');
        s=s.substring(0,19);
        return s;
    }
    public LocalDateTime getBeginLocalDate()
    {
        return begin;
    }
    public void setEndDate(String date)
    {
        DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        end=LocalDateTime.parse(date, sf);
    }
    public void setEndDate(int year,int month,int day,int hour,int minute,int second)
    {
        end = LocalDateTime.of(year, month, day, hour, minute, second);
    }
    public String getEndDate()
    {
        String s=end.toString();
        s=s.replace('T',' ');
        s=s.substring(0,19);
        return s;
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
    public void setState(boolean newstate)
    {
        state= newstate;
    }
    public boolean checkState()
    {
        return state;
    }
    public void setImportant(int i){important=i;}
    public int getImportant(){return important;}

}
