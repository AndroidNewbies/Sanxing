package io.github.celestialphineas.sanxing.sxObjectManager;

/**
 * Created by apple on 2017/11/3.
 * 新版增加排序order函数，规则在Habit类中
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.celestialphineas.sanxing.sxObject.Habit;


public class HabitManager implements SxObjectManager {
    private  List<Habit> HabitPool;
    private int nHabits=0;
    private int nFinishedHabits=0;
    public HabitManager(){
        HabitPool = new ArrayList<Habit>();
        nHabits=0;
        nFinishedHabits=0;
    }
    public HabitManager(List<Habit> list){
        HabitPool = list;
    }
    public int getNumberOfHabits(){return nHabits;}
    public int getNumberOfFinishedHabits(){return nFinishedHabits;}
    public boolean addObject(Object obj){
        Habit habit = (Habit) obj;
        return true;
    }
    public boolean addAll(List<Habit> list){

        HabitPool.addAll(list);
        resetNumbers();
        return true;
    }
    public boolean removeObject(int index){
        if (index < HabitPool.size()){
            HabitPool.remove(index);
            return true;
        }else  return false;
    }

    public int checkObjectState(int index){
        return HabitPool.get(index).checkState();
    }

    public List<Habit> getObjectList(){

        return HabitPool;
    }
    public void order(){ Collections.sort(HabitPool);}
    public void resetNumbers()
    {
        nHabits=0;
        nFinishedHabits=0;
        for (Habit habit:HabitPool)
        {
            int state=habit.getState();
            if (state==1) nHabits++;
            else if (state==2) nFinishedHabits++;
        }
    }
}

