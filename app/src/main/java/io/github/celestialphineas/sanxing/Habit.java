package io.github.celestialphineas.sanxing;

/**
 * Created by apple on 2017/11/3.
 */

public class Habit extends SxObject {
    private String content;

    private int frequency;
    private int for_dif;
    Habit(){
        content="null";
        frequency=0;
    }

    public Habit(String content) {
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
}
