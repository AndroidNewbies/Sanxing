package io.github.celestialphineas.sanxing;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

import io.github.celestialphineas.sanxing.timer.MyService;


/**
 * Created by lin on 2017/11/3.
 * APP创建时，会先运行Application
 * 在此oncreate中初始化一些东西，设定闹钟
 * 在具体任务中使用Java最新的LocalDateTime事件，安卓不自带，所以包含AndroidThreeTen包
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    private final static String PROCESS_NAME="io.github.celestialphineas.sanxing";

    @Override
    public void onCreate()
    {
        super.onCreate();
        AndroidThreeTen.init(this);//ThreeTenABP的使用初始化
        instance = this;
        if (!isServiceRunning(this,"io.github.celestialphineas.Timer.MyService"))
        {
            Log.i("SanxingAPP","I am Application");
            //开启第一次service，设置闹钟
            Intent i = new Intent(this, MyService.class);
            LocalDateTime now= LocalDateTime.now();
            now=LocalDateTime.of(now.getYear(),now.getMonth(),now.getDayOfMonth(),12,0,0);
            i.putExtra("date",now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            i.putExtra("destory","NO");
            i.putExtra("source","Application");
            //Log.i("SanxingAPP","I will start service");
            this.startService(i);
        }

    }
    public static MyApplication getInstance(){
        return instance;
    }

    /**
     * 判断服务是否正在运行
     *
     * @param context
     * @param className 判断的服务名字：包名+类名
     * @return true在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        //获取所有的服务
        List<ActivityManager.RunningServiceInfo> services= activityManager.getRunningServices(Integer.MAX_VALUE);
        if(services!=null&&services.size()>0)
        {
            for(ActivityManager.RunningServiceInfo service : services)
            {
                //Log.i("Service name",service.service.getClassName());
                if(className.equalsIgnoreCase(service.service.getClassName()))
                {
                    isRunning=true;
                    break;
                }
            }
        }
        //Log.i("IsServiceRunning",String.valueOf(isRunning));
        return isRunning;
    }

}
