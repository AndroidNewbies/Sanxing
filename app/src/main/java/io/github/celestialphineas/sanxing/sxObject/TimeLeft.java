package io.github.celestialphineas.sanxing.sxObject;

import java.io.Serializable;

/**
 * Created by lin on 2017/11/4.
 */

public class TimeLeft extends AbstractsxObject implements Serializable,Comparable<TimeLeft>{
    public TimeLeft()
    {
        super();
    }
    public TimeLeft(String content)
    {
        super();
        super.setContent(content);
    }
    public void create_timeleft(String title,String begindate,String enddate,String content,int important)
    {
        super.create_object(title, begindate, enddate, content, important);
    }
    public void edit_timeleft(String title,String enddate,String content,int important)
    {
        super.edit_object(title, enddate, content, important);
    }
    //余时只按照重要程度排序
    @Override
    public int compareTo(TimeLeft another_left)
    {
        int this_weight=this.getImportantance();
        int another_weight=another_left.getImportantance();
        if (this_weight!=another_weight)
        {
            if (this_weight>another_weight) return -1;
            return 1;
        }
        return 0;
    }
}
