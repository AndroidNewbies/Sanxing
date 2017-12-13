package io.github.celestialphineas.sanxing.timer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;

import java.util.Date;

/**
 * Created by lin on 2017/12/11.
 * rebulid setting
 */

public class Setting {
    public LocalDateTime callTime;//提醒时间
    public boolean ifnotify;
    public String Ringtone;
    public boolean ifvibrate;
    public Setting()
    {
        LocalDateTime now=LocalDateTime.now();
        callTime=LocalDateTime.of(now.getYear(),now.getMonth(),now.getDayOfMonth(),12,0);
        ifnotify=true;
        Ringtone="";
        ifvibrate=true;
    }
    /* 设置文件的读取
     * 修改为读取sharedpreference.xml
     */
    public void readSetting(Context context)
    {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        ifnotify=sharedPreferences.getBoolean("notifications_enabled",false);
        ifvibrate=sharedPreferences.getBoolean("notifications_vibrate_enabled",false);
        Ringtone=sharedPreferences.getString("notifications_ringtone","");
        long time=sharedPreferences.getLong("notifications_time", new Date().getTime());
        ZoneOffset zoneOffset = OffsetDateTime.now (ZoneId.systemDefault()).getOffset();//获取当前时区
        callTime=LocalDateTime.ofEpochSecond(time/1000,0, zoneOffset);
        Log.w("Time2",callTime.toString());
        LocalDateTime now=LocalDateTime.now();
        callTime=LocalDateTime.of(now.getYear(),now.getMonth(),now.getDayOfMonth(),
                callTime.getHour(),callTime.getMinute());
    }
}
