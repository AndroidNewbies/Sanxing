package io.github.celestialphineas.sanxing.UIHomeTabFragments;

/**
 * Created by apple on 2017/11/3.
 */
import java.util.ArrayList;
import java.util.List;

import io.github.celestialphineas.sanxing.Habit;
import io.github.celestialphineas.sanxing.SxObjectManager;

public class HabitManager implements SxObjectManager {
    private  List<Habit> HabitPool;
    HabitManager(){
        HabitPool = new ArrayList<Habit>();

    }
    HabitManager(List<Habit> list){
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
        return HabitPool.get(index).isChecked();
    }

    public List<Habit> readObjectList(){

        return HabitPool;
    }

}

