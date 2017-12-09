package io.github.celestialphineas.sanxing.timer;

/**
 * Created by apple on 2017/12/9.
 */

import org.threeten.bp.LocalDateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lin on 2017/12/9.
 */

public class MyDuration {
    public static long durationFromAtoB(LocalDateTime A,LocalDateTime B)
    {
        String aString=A.toString();
        aString=aString.replace('T',' ');
        aString=aString.substring(0,19);

        String bString=B.toString();
        bString=bString.replace('T',' ');
        bString=bString.substring(0,19);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diff=0;
        try
        {
            Date aDate=df.parse(aString);
            Date bDate=df.parse(aString);
            diff = bDate.getTime() - aDate.getTime();//这样得到的差值是微秒级别
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return diff;
    }
    public static long durationFromAtoB(String A,String B)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diff=0;
        try
        {
            Date aDate=df.parse(A);
            Date bDate=df.parse(B);
            diff = bDate.getTime() - aDate.getTime();//这样得到的差值是微秒级别
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return diff;
    }
    public static long durationFromNowtoB(LocalDateTime B)
    {
        String endString=B.toString();
        endString=endString.replace('T',' ');
        endString=endString.substring(0,19);
        long diff=0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date nowDate=new Date();
            Date endDate=df.parse(endString);
            diff = endDate.getTime() - nowDate.getTime();//这样得到的差值是微秒级别
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return diff;
    }
    public static long durationFromNowtoB(String B)
    {
        //need format of B is yyyy-MM-dd HH:mm:ss
        long diff=0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date nowDate=new Date();
            Date endDate=df.parse(B);
            diff = endDate.getTime() - nowDate.getTime();//这样得到的差值是微秒级别
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return diff;
    }
}
