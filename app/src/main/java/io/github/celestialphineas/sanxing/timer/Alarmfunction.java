package io.github.celestialphineas.sanxing.timer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Calendar;

/**
 * Created by lin on 2017/11/4.
 * 封装了设定闹钟和销毁闹钟的两个函数
 */

public class Alarmfunction {
    public static Calendar getCalendar(LocalDateTime date) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, date.getYear());
        c.set(Calendar.MONTH, date.getMonthValue() - 1);//也可以填数字，0-11,一月为0
        c.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
        c.set(Calendar.HOUR_OF_DAY, date.getHour());
        c.set(Calendar.MINUTE, date.getMinute());
        c.set(Calendar.SECOND, date.getSecond());
        return c;
    }

    public static void startRTCWakeUpAlarm(Context context, Class<?> cls, LocalDateTime date) {
        // 获取AlarmManager系统服务
        Calendar c = getCalendar(date);
        Intent intent = new Intent(context, cls);
        intent.putExtra("date", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));//向receiver发送还是用intent
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //设置一个PendingIntent对象，发送广播
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
    }

    public static void killalarm(Context context, Class<?> cls) {
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (pi != null)//闹钟存在
        {
            alarm.cancel(pi);
            Log.i("ALARM", "I cancel the alarm");
        }

    }
}
