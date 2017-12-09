package io.github.celestialphineas.sanxing.timer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import io.github.celestialphineas.sanxing.R;


/**
 * Created by lin on 2017/11/4.
 * 需要使用Service的人
 * 开启service的destroy选项是NO，则不摧毁闹钟，否则摧毁
 * intent中的date字符串是按照格式的，设定下一次唤醒的日期
 * Activity中intent发过来时务必包含date，destory以及source=activity
 *
 */

public class MyService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //Log.d("Service", "onStartCommand: I wake up");
        //Log.d("Service", intent.getStringExtra("date"));
        String ifdestory=intent.getStringExtra("destory");
        if (ifdestory.isEmpty()==false && ifdestory.equals("NO"))
        {
            LocalDateTime date=LocalDateTime.parse(intent.getStringExtra("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (LocalDateTime.now().isAfter(date)== true ) date=date.plusDays(1);
            Alarmfunction.killalarm(this,AlarmReceiver.class);
            Alarmfunction.startRTCWakeUpAlarm(this,AlarmReceiver.class,date);
        }
        else
        {
            Alarmfunction.killalarm(this,AlarmReceiver.class);
        }
        //打开APP不用通知栏提醒
        //Log.i("Service", intent.getStringExtra("source"));
        if (intent.getStringExtra("source").equals("Application"))
        {
            stopSelf();
            return super.onStartCommand(intent,flags,startId);
        }
        /*
        * do what you wang to do
        * catch Task pool data and judge which task needs to be noticed
        *
        */

        /*
        * 通知菜单栏demo
        * */
        //创建大图标的Bitmap
        //Bitmap LargeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //1.从系统服务中获得通知管理器

        NotificationManager myNotiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder myNotiBuilder = new Notification.Builder(this);
        myNotiBuilder.setContentTitle("三省：任务提醒")
                .setContentText(intent.getStringExtra("source"))
                .setSubText("任务截止日期")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)//点击后取消
                .setWhen(System.currentTimeMillis())//设置通知时间
                .setPriority(Notification.PRIORITY_HIGH)//高优先级
                .setVisibility(Notification.VISIBILITY_PRIVATE);
        Notification myNotification=myNotiBuilder.build();
        myNotiManager.notify(1,myNotification);


        //活干完了，就结束自己的生命，等待下一次
        stopSelf();
        return super.onStartCommand(intent,flags,startId);
    }


}
