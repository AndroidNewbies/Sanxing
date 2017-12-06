package io.github.celestialphineas.sanxing.sxObject;

import java.io.Serializable;

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
    private int weight;//1 to 5
    private int type;
    public static final String KEY_ID = "id";
    public static final String TABLE = "Task";
    public static final String KEY_content = "content";
    public int task_ID;
    // Labels Table Columns names

    private static final long serialVersionUID = 2L;

    public Task()
    {
        super();
        weight=3;
        type=1;
    }
    public Task(String content)
    {
        super();
        weight=3;
        type=1;
        super.setContent(content);
    }
    public void setTask(String begindate,String enddate,String noticetime,
                        String frequency,int weight,int type)
    {
        super.setBeginDate(begindate);
        super.setEndDate(enddate);
        super.setNoticeTime(noticetime);
        super.setFrequencyTime(frequency);
        //super.setContent(content);
        this.weight=weight;
        this.type=type;
    }
    public void setWeight(int weight){this.weight=weight;}
    public int getWeight(){return weight;}
    public void accomplish()
    {
        super.setState(true);
    }
    public void editContent(String text)
    {
        super.setContent(text);
    }
    public void checkTask()
    {

    }
}
