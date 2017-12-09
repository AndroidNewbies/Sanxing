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
    public HabitManager(){
        HabitPool = new ArrayList<Habit>();

    }
    public HabitManager(List<Habit> list){
        HabitPool = list;

    }
    public boolean addObject(Object obj){
        Habit Habit = (Habit) obj;
        HabitPool.add(Habit);
        return true;
    }

    public boolean removeObject(int index){
        if (index < HabitPool.size()){
            HabitPool.remove(index);
            return true;
        }else  return false;
    }

    public boolean checkObjectState(int index){
        return HabitPool.get(index).checkState();
    }

    public List<Habit> getObjectList(){

        return HabitPool;
    }
    public void order(){ Collections.sort(HabitPool);}
}

