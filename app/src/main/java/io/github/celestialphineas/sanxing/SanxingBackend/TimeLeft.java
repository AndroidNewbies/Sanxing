package io.github.celestialphineas.sanxing.SanxingBackend;

import io.github.celestialphineas.sanxing.SanxingBackend.SxObject;

/**
 * Created by apple on 2017/11/3.
 */

public class TimeLeft extends SxObject {
    private String content;
    private int time_left;
    private int for_dif;
    TimeLeft(){
        content="null";
        time_left = 0;
    }

    public TimeLeft(String content) {
        this.content = content;
        this.time_left = 91;
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

    public int getTime_left() {
        return time_left;
    }
    @Override
    public String getContent() {
        return content;
    }
}
