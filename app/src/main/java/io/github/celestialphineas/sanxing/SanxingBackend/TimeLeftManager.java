package io.github.celestialphineas.sanxing.SanxingBackend;

import java.util.ArrayList;
import java.util.List;


public class TimeLeftManager implements SxObjectManager {
    private  List<TimeLeft> TimeLeftPool;
    public TimeLeftManager(){
        TimeLeftPool = new ArrayList<TimeLeft>();

    }
    public TimeLeftManager(List<TimeLeft> list){
        TimeLeftPool = list;
    }

    public boolean addObject(Object obj){
        TimeLeft TimeLeft = (TimeLeft) obj;
        TimeLeftPool.add(TimeLeft);
        return true;
    }

    public boolean removeObject(int index){
        if (index < TimeLeftPool.size()){
            TimeLeftPool.remove(index);
            return true;
        }else  return false;
    }

    public boolean checkObjectState(int index){
        return TimeLeftPool.get(index).isChecked();
    }

    public List<TimeLeft> readObjectList(){

        return TimeLeftPool;
    }

}
