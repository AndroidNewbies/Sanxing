package io.github.celestialphineas.sanxing.sxObjectManager;

import java.util.ArrayList;
import java.util.List;

import io.github.celestialphineas.sanxing.sxObject.Task;


//对Task的list的封装
public class TaskManager implements SxObjectManager {
    private  List<Task> TaskPool;
    public TaskManager(){
        TaskPool = new ArrayList<Task>();

    }
    public TaskManager(List<Task> list){
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
        return TaskPool.get(index).checkState();
    }

    public List<Task> getObjectList(){

        return TaskPool;
    }

    public void updateTaskManager(List<Task> list){
        TaskPool.clear();
        TaskPool = list;
    }
}
