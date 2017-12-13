package io.github.celestialphineas.sanxing;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindString;
import io.github.celestialphineas.sanxing.SanxingBackend.HabitRepo;
import io.github.celestialphineas.sanxing.SanxingBackend.TaskRepo;
import io.github.celestialphineas.sanxing.SanxingBackend.TimeLeftRepo;
import io.github.celestialphineas.sanxing.sxObject.Habit;
import io.github.celestialphineas.sanxing.sxObject.TimeLeft;
import io.github.celestialphineas.sanxing.sxObjectManager.HabitManager;
import io.github.celestialphineas.sanxing.sxObjectManager.TaskManager;
import io.github.celestialphineas.sanxing.sxObjectManager.TimeLeftManager;
import io.github.celestialphineas.sanxing.timer.MyService;
import io.github.celestialphineas.sanxing.timer.Setting;


/**
 * Created by lin on 2017/11/3.
 * APP创建时，会先运行Application
 * 在此oncreate中初始化一些东西，设定闹钟
 * 在具体任务中使用Java最新的LocalDateTime事件，安卓不自带，所以包含AndroidThreeTen包
 */

public class MyApplication extends Application {
    private MyApplication instance;
    private final static String PROCESS_NAME = "io.github.celestialphineas.sanxing";
    private Setting mysetting;//全局设置
    private TaskManager _task_manager;//全局的任务管理器
    private HabitManager _habit_manager;
    private TimeLeftManager _time_left_manager;
    private TaskRepo taskRepo;//用于task的数据库操作
    private HabitRepo habitRepo;//用于habit的数据库操作
    private TimeLeftRepo timeLeftRepo;//用于timeleft的数据库操作

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);//ThreeTenABP的使用初始化
        install();//用于第一次安装，建立设置表
        instance = this;
        //读入设置
        _task_manager = new TaskManager();
        _habit_manager = new HabitManager();
        _time_left_manager = new TimeLeftManager();
        taskRepo = new TaskRepo(this);
        habitRepo = new HabitRepo(this);
        timeLeftRepo = new TimeLeftRepo(this);
        mysetting = new Setting();

        _task_manager.addAll(taskRepo.getTaskList());
        _time_left_manager.addAll(timeLeftRepo.getTimeLeftList());
        _habit_manager.addAll(habitRepo.getHabitList());
        _task_manager.order();
        _habit_manager.order();
        _time_left_manager.order();
        mysetting.readSetting(this);
        Log.d("ringtone",mysetting.Ringtone);
        Log.d("CallTime",mysetting.callTime.toString());
        if (!isServiceRunning(this, "io.github.celestialphineas.imer.MyService"))
        {
            //开启第一次service，设置闹钟
            Intent i = new Intent(this, MyService.class);
            i.putExtra("source", "Application");
            this.startService(i);
        }

    }

    public MyApplication getInstance() {
        return instance;
    }

    public Setting getMysetting() {
        return mysetting;
    }

    public TaskManager get_task_manager() {
        return _task_manager;
    }

    public HabitManager get_habit_manager() {
        return _habit_manager;
    }

    public TimeLeftManager get_time_left_manager() {
        return _time_left_manager;
    }

    public TaskRepo getTaskRepo() {
        return taskRepo;
    }

    public HabitRepo getHabitRepo() {
        return habitRepo;
    }

    public TimeLeftRepo getTimeLeftRepo() {
        return timeLeftRepo;
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
        List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (services != null && services.size() > 0) {
            for (ActivityManager.RunningServiceInfo service : services) {
                //Log.i("Service name",service.service.getClassName());
                if (className.equalsIgnoreCase(service.service.getClassName())) {
                    isRunning = true;
                    break;
                }
            }
        }
        //Log.i("IsServiceRunning",String.valueOf(isRunning));
        return isRunning;
    }
    //安装时，就建立设置表，并设置默认值
    private void install()
    {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        boolean ifinstall=sharedPreferences.getBoolean("ifinstall",false);
        if (!ifinstall)
        {
            //实例化Editor对象
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //存入数据
            editor.putBoolean("ifinstall",true);//已安装
            //默认值
            editor.putBoolean("notifications_enabled",true);
            editor.putBoolean("notifications_vibrate_enabled",true);
            LocalDateTime now=LocalDateTime.now();
            now=LocalDateTime.of(now.getYear(),now.getMonth(),now.getDayOfMonth(),12,0);
            ZoneOffset zoneOffset = OffsetDateTime.now(ZoneId.systemDefault()).getOffset ();
            editor.putLong("notifications_time",now.toEpochSecond(zoneOffset)*1000);//当地12点的格林尼治时间戳
            //Log.w("Time1",String.valueOf(now.toEpochSecond(zoneOffset)*1000));
            //Log.w("Time1",String.valueOf(now.toEpochSecond(ZoneOffset.UTC)*1000));
            editor.putString("notifications_ringtone","content://settings/system/notification_sound");
            //提交修改
            editor.commit();
        }
    }
}

//    //先别删，万一用到了文件呢
//    private void readSetting()
//    {
//        File settingfile=new File(SETTING_FILE_NAME);
//        //文件是否存在
//        if(!settingfile.exists())
//        {
//            return;
//            /*try
//            {
//                //文件不存在，就创建一个新文件
//                settingfile.createNewFile();
//                Log.i("SanxingSettingFile","create");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }*/
//        }
//        else
//        {
//            try
//            {
//                FileReader filereader=new FileReader(settingfile);
//                BufferedReader myreader = new BufferedReader(filereader);
//                String hour=myreader.readLine();
//                String minutes=myreader.readLine();
//                mysetting.callTime=LocalTime.of(Integer.valueOf(hour),Integer.valueOf(minutes));
//                mysetting.ifnotify=Boolean.valueOf(myreader.readLine());
//                mysetting.whichRingtone=Integer.valueOf(myreader.readLine());
//                mysetting.ifvibratel=Boolean.valueOf((myreader.readLine()));
//                myreader.close();
//                filereader.close();
//            }
//            catch (FileNotFoundException e) {e.printStackTrace();}
//            catch (IOException e) {e.printStackTrace();}
//        }
//    }
//    public void setSetting()
//    {
//        File settingfile=new File(SETTING_FILE_NAME);
//        //文件是否存在
//        if(!settingfile.exists())
//        {
//            try
//            {
//                //文件不存在，就创建一个新文件
//                settingfile.createNewFile();
//                Log.i("SanxingSettingFile","create");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        else
//        {
//            try
//            {
//                FileWriter fileWriter=new FileWriter(settingfile,false);//覆盖
//                BufferedWriter mywriter = new BufferedWriter(fileWriter);
//                mywriter.write(String.valueOf(mysetting.callTime.getHour())+"\n");
//                mywriter.write(String.valueOf(mysetting.callTime.getMinute()+"\n"));
//                mywriter.write(String.valueOf(mysetting.ifnotify));
//                mywriter.write(String.valueOf(mysetting.whichRingtone));
//                mywriter.write(String.valueOf(mysetting.ifvibratel));
//                mywriter.close();
//                fileWriter.close();
//            }
//            catch (FileNotFoundException e) {e.printStackTrace();}
//            catch (IOException e) {e.printStackTrace();}
//        }
//    }

