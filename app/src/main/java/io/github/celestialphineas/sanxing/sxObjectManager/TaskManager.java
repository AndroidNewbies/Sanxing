package io.github.celestialphineas.sanxing.sxObjectManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.celestialphineas.sanxing.sxObject.Task;

/* Lin
 * 新版增加排序order函数，规则在Task类中
 */
//对Task的list的封装
public class TaskManager implements SxObjectManager {
    private List<Task> TaskPool;
    private int nTasks = 0;
    private int nFinishedTasks = 0;

    public TaskManager() {
        TaskPool = new ArrayList<Task>();
        nTasks = 0;
        nFinishedTasks = 0;
    }

    public TaskManager(List<Task> list) {
        TaskPool = list;
    }

    public int getNumberOfTasks() {
        return nTasks;
    }

    public int getNumberOfFinishedTasks() {
        return nFinishedTasks;
    }

    public boolean addObject(Object obj) {
        Task task = (Task) obj;
        TaskPool.add(task);
        return true;
    }

    public boolean addAll(List<Task> list) {
        TaskPool.addAll(list);
        resetNumbers();
        return true;
    }

    public boolean removeObject(int index) {
        if (index < TaskPool.size()) {
            TaskPool.remove(index);
            return true;
        } else return false;
    }

    public int checkObjectState(int index) {
        return TaskPool.get(index).checkState();
    }

    public List<Task> getObjectList() {

        return TaskPool;
    }

    //排序函数
    public void order() {
        Collections.sort(TaskPool);
    }

    public void resetNumbers() {
        nTasks = 0;
        nFinishedTasks = 0;
        for (Task task : TaskPool) {
            int state = task.getState();
            if (state == 1) nTasks++;
            else if (state == 2) nFinishedTasks++;
        }
    }
}
