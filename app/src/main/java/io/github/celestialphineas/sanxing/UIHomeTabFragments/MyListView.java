package io.github.celestialphineas.sanxing.UIHomeTabFragments;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.ListViewCompat;
import android.util.AttributeSet;

/**
 * Created by celestialphineas on 17-12-13.
 */

public class MyListView extends ListViewCompat {

    private android.view.ViewGroup.LayoutParams params;
    private int oldCount = 0;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getCount() != oldCount) {
            int height = getChildAt(0).getHeight() + 1;
            oldCount = getCount();
            params = getLayoutParams();
            params.height = getCount() * height;
            setLayoutParams(params);
        }

        super.onDraw(canvas);
    }

}
