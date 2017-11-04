package io.github.celestialphineas.sanxing;

/**
 * Created by apple on 2017/11/2.
 */

public abstract class SxObject {
    Long begin;
    boolean state;
    abstract boolean isChecked();

    abstract String getContent();


}
