package io.github.celestialphineas.sanxing.sxObject;

import android.content.Loader;

import org.threeten.bp.LocalDateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by lin on 2017/11/3.
 */

public class Habit extends AbstractsxObject implements Serializable,Comparable<Habit>{
    // private ArrayList<Integer> record;
    private int frequency;//打卡频率分好多档
    private int recordnumber;//当前打卡
    private int neednumber;//当前需要
    private LocalDateTime nextddl;
    private int need_record_all;
    private int have_record_all;
    public Habit()
    {
        super();
        frequency=2;
        recordnumber=0;
        neednumber=1;
        need_record_all=0;
        have_record_all=0;
        nextddl=LocalDateTime.now();
    }
    public Habit(String title)
    {
        super();
        frequency=2;
        recordnumber=0;
        super.setTitle(title);
    }
    public int getFrequency(){ return frequency;}
    public int getRecordnumber(){ return recordnumber;}
    public int getNeednumber(){ return neednumber;}
    public int getNeed_record_all(){return need_record_all;}
    public int getHave_record_all(){ return have_record_all;}
    public String getNextddl() {
        String s = nextddl.toString();
        s = s.replace('T', ' ');
        return s;
    }
    public LocalDateTime getLocalNextddl(){return nextddl;}


    //create
    public void create_habit(String title,String begindate,String enddate,String content,
                             int important, int frequency)
    {
        super.create_object(title, begindate, enddate, content, important);
        this.frequency=frequency;
        nextddl=LocalDateTime.of(getBeginLocalDate().getYear(),getBeginLocalDate().getMonth(),
                getBeginLocalDate().getDayOfMonth(),0,0,0);
        need_record_all=0;
        have_record_all=0;
        recordnumber=0;
        next_day();//设置第一次的record
    }
    //edit
    public void edit_habit(String title,String enddate,String content,
                           int important, int frequency)
    {
        super.edit_object(title, enddate, content, important);
        this.frequency=frequency;
        next_day();
    }

    //打卡
    public void addRecord()
    {
        if (recordnumber<neednumber)
        {
            recordnumber++;
            have_record_all++;
        }
    }

    //消除
    public void deleteRecord()
    {
        if (recordnumber>0)
        {
            recordnumber--;
            have_record_all--;
        }
    }

    //到了ddl，就保存当前阶段打卡，进入下一次打卡
    private void next_day()
    {
        LocalDateTime now=LocalDateTime.now();
        while (!now.isBefore(nextddl))//更新总需要
        {
            nextddl=nextddl.plusDays(get_duration());
            need_record_all+=neednumber;
            neednumber=get_need_record();
            recordnumber=0;
        }
    }
    //获取当前frequency下，本阶段需要打几次卡
    private int get_need_record()
    {
        switch (frequency)
        {
            case 0: return 3;
            case 1: return 2;
            default: return 1;
        }
    }
    //获取当前frequency下，每个阶段的时间
    private int get_duration()
    {
        switch (frequency)
        {
            case 0:
            case 1:
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 7;
            case 6:
                return 15;
            case 7:
                return 30;
            default:
                return 1;
        }
    }

    //对习惯的排序，优先级高的放上边，然后优先级一样则排频率，频率越高（frequency越低)
    //则越往前
    @Override
    public int compareTo(Habit another_habit)
    {
        int this_weight=this.getImportance();
        int another_weight=another_habit.getImportance();
        if (this_weight!=another_weight)
        {
            if (this_weight>another_weight) return -1;
            return 1;
        }
        else
        {
            if (this.frequency<another_habit.getFrequency()) return -1;
            else if (this.frequency>another_habit.getFrequency()) return 1;
        }
        return 0;
    }
}
