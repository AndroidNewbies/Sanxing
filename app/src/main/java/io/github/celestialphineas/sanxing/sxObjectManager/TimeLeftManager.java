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
    private int nTimeLefts=0;
    private int nFinishedTimeLefts=0;
    public TimeLeftManager(){
        TimeLeftPool = new ArrayList<TimeLeft>();
        nTimeLefts=0;
        nFinishedTimeLefts=0;
    }
    public TimeLeftManager(List<TimeLeft> list){
        TimeLeftPool = list;
        resetNumbers();
    }
    public int getNumberOfTimeLefts(){return nTimeLefts;}
    public int getNumberOfFinishedTimeLefts(){return nFinishedTimeLefts;}
    public boolean addObject(Object obj){
        TimeLeft timeLeft = (TimeLeft) obj;
        if (timeLeft.getState()==1) nTimeLefts++;
        else if (timeLeft.getState()==2) nFinishedTimeLefts++;
        TimeLeftPool.add(timeLeft);
        return true;
    }
    public boolean addAll(List<TimeLeft> list){

        TimeLeftPool.addAll(list);
        resetNumbers();
        return true;
    }
    public boolean removeObject(int index){
        if (index < TimeLeftPool.size()){
            TimeLeft timeLeft=TimeLeftPool.get(index);
            int state=timeLeft.getState();
            if (state==1) nTimeLefts--;
            else if (state==2) nFinishedTimeLefts--;
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
    public void order(){ Collections.sort(TimeLeftPool);}
    private void resetNumbers()
    {
        nTimeLefts=0;
        nFinishedTimeLefts=0;
        for (TimeLeft timeLeft:TimeLeftPool)
        {
            int state=timeLeft.getState();
            if (state==1) nTimeLefts++;
            else if (state==2) nFinishedTimeLefts++;
        }
    }
}
