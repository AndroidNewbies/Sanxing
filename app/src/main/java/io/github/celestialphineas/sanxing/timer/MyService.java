package io.github.celestialphineas.sanxing.timer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

import butterknife.BindString;
import io.github.celestialphineas.sanxing.HomeActivity;
import io.github.celestialphineas.sanxing.MyApplication;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.sxObject.Task;
import io.github.celestialphineas.sanxing.sxObjectManager.TaskManager;


/**
 * Created by lin on 2017/11/4.
 * 需要使用Service的人
 * 开启service的destroy选项是NO，则不摧毁闹钟，否则摧毁
 * intent中的date字符串是按照格式的，设定下一次唤醒的日期
 * Activity中intent发过来时务必包含date，destory以及source=activity
 *
 */

public class MyService extends Service {
    private MyApplication myApplication;
    private Setting mysetting;
    private TaskManager taskManager;
    @BindString(R.string.noti_title) String noti_title;
    @BindString(R.string.noti_task_name) String noti_task_name;
    @BindString(R.string.noti_ddl) String noti_ddl;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        myApplication=(MyApplication)getApplication();
        mysetting=myApplication.getMysetting();
        mysetting.readSetting(myApplication.getApplicationContext());
        if (mysetting.ifnotify)//启用提醒
        {
            //如果当前时间在设定时间之后，就将设定时间调到明天
            if (LocalDateTime.now().isAfter(mysetting.callTime)) mysetting.callTime=mysetting.callTime.plusDays(1);
            Alarmfunction.killalarm(this,AlarmReceiver.class);
            Alarmfunction.startRTCWakeUpAlarm(this,AlarmReceiver.class,mysetting.callTime);
        }
        else
        {
            Alarmfunction.killalarm(this,AlarmReceiver.class);
        }
        //打开APP不用通知栏提醒
        if (!intent.getStringExtra("source").equals("receiver")||!mysetting.ifnotify)
        {
            stopSelf();
            return super.onStartCommand(intent,flags,startId);
        }
        /*
        * do what you wang to do
        * catch Task pool data and judge which task needs to be noticed
        */
        taskManager=myApplication.get_task_manager();
        /*
        * 通知菜单栏demo
        * */
        //创建大图标的Bitmap
        //Bitmap LargeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //1.从系统服务中获得通知管理器
        Intent mainIntent= new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,mainIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager myNotiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);//设置通知格式
        Notification.Builder myNotiBuilder = new Notification.Builder(this);
        myNotiBuilder.setContentTitle(noti_title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)//点击后取消
                .setWhen(System.currentTimeMillis())//设置通知时间
                .setPriority(Notification.PRIORITY_HIGH)//高优先级
                .setVisibility(Notification.VISIBILITY_PRIVATE)
                .setContentIntent(pendingIntent);
        //.setContentText(intent.getStringExtra("source"))
        //.setSubText("任务截止日期")
        if (mysetting.ifvibrate) myNotiBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
        if (!mysetting.Ringtone.equals("")) myNotiBuilder.setSound(Uri.parse(mysetting.Ringtone));

        List<Task> taskList=taskManager.getObjectList();
        Task task;
        for (int i=0;i<taskList.size();i++)
        {
            task=taskList.get(i);
            if (task.score()>3 && i>0) break;//如果有,至少显示一个，大于3分的不显示
            myNotiBuilder.setContentText(noti_task_name+task.getTitle())
                    .setSubText(noti_ddl+task.getEndDate());
            Notification myNotification=myNotiBuilder.build();
            myNotiManager.notify(i,myNotification);
        }

        //活干完了，就结束自己的生命，等待下一次
        stopSelf();
        return super.onStartCommand(intent,flags,startId);
    }


}
