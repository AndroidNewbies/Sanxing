package io.github.celestialphineas.sanxing.SanxingBackend;

import java.util.List;

/**
 * Created by apple on 2017/11/2.
 */

public interface SxObjectManager
{
    //add a new item
    boolean addObject(Object obj);

    boolean removeObject(int index);

    boolean checkObjectState(int index);


}
