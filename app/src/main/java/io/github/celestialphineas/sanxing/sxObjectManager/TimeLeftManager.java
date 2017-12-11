package io.github.celestialphineas.sanxing.sxObjectManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.celestialphineas.sanxing.sxObject.TimeLeft;

/* Lin
 * 新版增加排序order函数，规则在TimeLeft类中
 */
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
    public boolean addAll(List<TimeLeft> list){

        TimeLeftPool.addAll(list);
        return true;
    }
    public boolean removeObject(int index){
        if (index < TimeLeftPool.size()){
            TimeLeftPool.remove(index);
            return true;
        }else  return false;
    }

    public int checkObjectState(int index){
        return TimeLeftPool.get(index).checkState();
    }

    public List<TimeLeft> getObjectList(){

        return TimeLeftPool;
    }
    //排序函数
    public void order(){
        Collections.sort(TimeLeftPool);}
}
