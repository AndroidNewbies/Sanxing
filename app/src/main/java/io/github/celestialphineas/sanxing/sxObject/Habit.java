package io.github.celestialphineas.sanxing.sxObject;

import android.util.Log;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.github.celestialphineas.sanxing.timer.MyDuration;


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

    private List<Integer> record;

    //database tag
    public static final String KEY_ID = "id";
    public static final String TABLE = "Habit";//this name can't be changed
    public static final String KEY_TITLE = "title";
    public static final String KEY_IMPORTANCE = "importance";
    public static final String KEY_BEGIN_TIME = "begin_time";
    public static final String KEY_END_TIME = "end_time";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_STATE = "state";
    public static final String KEY_FREQUENCY = "frequency";
    public static final String KEY_RECORDNUMBER = "record_number";
    public static final String KEY_NEEDNUMBER = "need_number";
    public static final String KEY_NEXTDDL = "next_ddl";
    public static final String KEY_NEED_RECORD_ALL = "need_record_all";
    public static final String KEY_HAVE_RECORD_ALL = "have_record_all";
    public static final String KEY_RECORD_LIST = "record_list";
    private static final long serialVersionUID = 2L;


    public Habit()
    {
        super();
        record = new ArrayList<Integer>();
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


    public List<Integer> getRecord() {
        return record;
    }
    public String getRecordInString() {
        String result="";
        for (int i=0;i<record.size();i++){
            result += String.valueOf(record.get(i));
            result += " ";
        }
        Log.e("Ken result","woc"+result);
        return result;
    }
    //this constructor only used when read data from the database
    public Habit(int id, String title, String begin, String end, String n_description, int n_importance, int n_frequency, int n_recordnumber,
                 int n_neednumber, String n_nextddl, int n_need_all, int n_have_all,int n_state, List<Integer> n_record_list )
    {

        super.create_object(title,begin,end,n_description,n_importance);
        ID  = id;//set the ID in the abstract class
        frequency=n_frequency;
        recordnumber=n_recordnumber;
        neednumber=n_neednumber;
        need_record_all=n_need_all;
        have_record_all=n_have_all;
        setNextddl(n_nextddl);
        setState(n_state);
        record = n_record_list;
//        record.addAll(n_record_list);
        next_day();
        setState(n_state);// attention: setState must be called after create_task. create_task will set state to be default 1!
    }
    public void setNextddl(String date){
        DateTimeFormatter sf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        nextddl= LocalDateTime.parse(date, sf);
    }
    public int getFrequency(){ return frequency;}
    public int getRecordnumber(){ return recordnumber;}
    public int getNeednumber(){ return neednumber;}
    public int getNeed_record_all(){return need_record_all;}
    public int getHave_record_all(){ return have_record_all;}
    public String getNextddl()
    {
        return MyDuration.LocalDateTime_to_String(nextddl);
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
        if (recordnumber == neednumber){
            //todo : list add days from today to begin
            long diff = 0;
            LocalDateTime begindatetime=getBeginLocalDate();
            LocalDateTime begindate=LocalDateTime.of(begindatetime.getYear(),begindatetime.getMonth(),
                    begindatetime.getDayOfMonth(),0,0);
            diff = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)-begindate.toEpochSecond(ZoneOffset.UTC);
            int diff_int = (int)diff;
            Integer day = diff_int/60/60/24;
            Log.e("Ken: dif for habits", String.valueOf(diff)+" "+diff+" "+day);
            record.add(day);
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
            neednumber=get_need_record();
            need_record_all+=neednumber;
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
