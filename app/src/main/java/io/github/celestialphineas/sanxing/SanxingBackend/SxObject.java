package io.github.celestialphineas.sanxing.SanxingBackend;

/**
 * Created by apple on 2017/11/2.
 */

public abstract class SxObject {
    public Long bein;
    public boolean state;
    public abstract boolean isChecked();
    public abstract String getContent();
}
