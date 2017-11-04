package io.github.celestialphineas.sanxing;

import java.util.ArrayList;
import java.util.List;

//对Task的list的封装
public class TaskManager implements SxObjectManager {
    private  List<Task> TaskPool;
    TaskManager(){
        TaskPool = new ArrayList<Task>();

    }
    TaskManager(List<Task> list){
        TaskPool = list;
    }

    public boolean addObject(Object obj){
        Task task = (Task) obj;
        TaskPool.add(task);
        return true;
    }
    public boolean addAll(List<Task> list){

        TaskPool.addAll(list);
        return true;
    }
    public boolean removeObject(int index){
        if (index < TaskPool.size()){
            TaskPool.remove(index);
            return true;
        }else  return false;
    }

    public boolean checkObjectState(int index){
        return TaskPool.get(index).isChecked();
    }

    public List<Task> readObjectList(){

        return TaskPool;
    }

}
