package io.github.celestialphineas.sanxing.SanxingBackend;

/**
 * Created by apple on 2017/11/2.
 */
import java.io.Serializable;

import io.github.celestialphineas.sanxing.SanxingBackend.SxObject;

public class Task extends SxObject implements Serializable{
    //为了实现数据库操作而定义的常量
    // Labels table name
    public static final String TABLE = "Task";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_content = "content";

    private static final long serialVersionUID = 2L;



    private String content;
    private int weight;
    private int frequency;
    public int task_ID;
    Task(){
        content="null";
        weight=0;
        frequency=0;
        task_ID=0;
    }

    public Task(String content) {
        this.content = content;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public void setState(boolean checked) {
        state = checked;
    }

    @Override
    public boolean isChecked() {
        return state;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setWeight(int parameter) {
        weight = parameter;
    }


}
