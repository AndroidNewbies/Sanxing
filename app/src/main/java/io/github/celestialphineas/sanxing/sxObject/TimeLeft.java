package io.github.celestialphineas.sanxing.sxObject;

/**
 * Created by lin on 2017/11/4.
 */

public class TimeLeft extends AbstractsxObject {
    private int frequency=0;
    private int time_left;
    public TimeLeft()
    {
        super();
        time_left=0;
    }
    public TimeLeft(String content)
    {
        super();
        super.setTitle(content);
    }
    public int getTime_left() {
        return time_left;
    }
}
