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
    private LocalDateTime begin;
    private LocalDateTime end;
    private LocalTime noticetime;
    private boolean state;// Is finished
    private LocalDateTime frequency;//DateTime
    private String content;
    AbstractsxObject()
    {
        //initial to now
        state=false;
        content="";
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
        return s;
    }
    public LocalDateTime getEndLocalDate()
    {
        return end;
    }
    public void setNoticeTime(String time)//HH:mm:ss
    {
        noticetime=LocalTime.parse(time);
    }
    public void setNoticetime(int day)
    {

    }
    public LocalTime getNoticeLocalDate()
    {
        return noticetime;
    }
    public String getNoticeTime()//HH:mm:ss
    {
        String s=noticetime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return s;
    }
    public void setFrequencyTime(String date)//yyyy-MM-dd HH:mm:ss
    {
        DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        frequency = LocalDateTime.parse(date, sf);
    }
    public String getFrequencyTime()//yyyy-MM-dd HH:mm:ss
    {
        String s= frequency.toLocalDate().toString()+" "+frequency.toLocalTime().toString();
        return s;
    }
    public LocalDateTime getFrequencyLocalDate()
    {
        return frequency;
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

}
