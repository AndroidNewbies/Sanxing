package io.github.celestialphineas.sanxing.sxObjectManager;

/**
 * Created by apple on 2017/11/2.
 */

public interface SxObjectManager
{
    //add a new item
    boolean addObject(Object obj);

    boolean removeObject(int index);

    int checkObjectState(int index);


}
