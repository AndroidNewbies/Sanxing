package io.github.celestialphineas.sanxing.sxObject;

import org.threeten.bp.LocalDateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lin on 2017/11/3.
 *
 * 任务开始时间
 * 结束时间
 * 重要程度
 * 任务类型：仅一次，周任务，自定义间隔
 * 提醒间隔
 *
 */

public class Task extends AbstractsxObject implements Serializable {
//
//    intent.putExtra("task_title",inputTitle.getText().toString());
//            intent.putExtra("importance",selectedImportance);
//            intent.putExtra("create_time",dueCalendar.getTime());
//            intent.putExtra("task_description",descriptionContent.getText().toString());
    private int importance;//1 to 5
    private int type;

    public String getDescription() {
        return description;
    }

    private String description;


    public static final String KEY_ID = "id";
    public static final String TABLE = "Task";//this name can't be changed
    public static final String KEY_TITLE = "title";
    public static final String KEY_IMPORTANCE = "importance";
    public static final String KEY_BEGIN_TIME = "begin_time";
    public static final String KEY_END_TIME = "end_time";
    public static final String KEY_DESCRIPTION = "description";

    public int task_ID;
    // Labels Table Columns names

    private static final long serialVersionUID = 2L;

    public Task()
    {
        super();
        importance =3;
        description="";
        type=1;
    }
    public Task(String content,  String begin,String end,String n_description,int n_importance)
    {
        super();
//        super.setBeginDate("1986-04-08 12:30:20");
//        super.setEndDate("1986-04-08 12:30:20");
        importance =n_importance;
        description = n_description;
        type=1;
        super.setTitle(content);
        super.setState(true);
    }

    public Task(String title)
    {
        super();
        importance =3;
        type=1;
        super.setTitle(title);
    }

    public void setTask(String begindate,String enddate,String noticetime,
                        String frequency,int weight,int type)
    {
        super.setBeginDate(begindate);
        super.setEndDate(enddate);
        super.setNoticeTime(noticetime);
        super.setFrequencyTime(frequency);
        //super.setTitle(content);
        this.importance =weight;
        this.type=type;
    }
    public void setWeight(int weight){this.importance =weight;}
    public int getWeight(){return importance;}
    public void accomplish()
    {
        super.setState(true);
    }
    public void editContent(String text)
    {
        super.setTitle(text);
    }
    public void checkTask()
    {

    }
}
