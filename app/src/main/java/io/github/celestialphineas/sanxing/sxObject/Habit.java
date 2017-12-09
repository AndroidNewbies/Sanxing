package io.github.celestialphineas.sanxing.sxObject;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;

/**
 * Created by lin on 2017/11/3.
 */

public class Habit extends AbstractsxObject {
    private ArrayList<Integer> record;
    private int interval;//days
    private int recordnumber;//which can be operated
    private LocalDateTime nextddl;
    public Habit()
    {
        super();
        record=new ArrayList<Integer>();
        interval=1;
        recordnumber=1;
    }
    public Habit(String content)
    {
        super();
        record=new ArrayList<Integer>();
        interval=1;
        recordnumber=1;
        super.setTitle(content);
    }
    public void addRecord(int number)
    {
        record.add(Integer.valueOf(number));
    }
    public boolean CheckNumber(int number)
    {
        return record.contains(Integer.valueOf(number));
    }
    public void removeRecord(int number)
    {
        record.remove(record.indexOf(Integer.valueOf(number)));
    }
    public ArrayList<Integer> loadrecord()
    {
        return record;
    }
}
