package io.github.celestialphineas.sanxing.UICalendarViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;

import io.github.celestialphineas.sanxing.R;

/**
 * Created by celestialphineas on 17-12-11.
 */

public class BarCanvasView extends View {
    int fgColor = ResourcesCompat.getColor(getResources(), R.color.colorTimeLeft, null);
    int bgColor = ResourcesCompat.getColor(getResources(), R.color.light_light_grey, null);
    Rect fgRect = new Rect();
    Rect bgRect = new Rect();
    Paint bgPaint = new Paint();
    Paint fgPaint = new Paint();
    double percentage = 0;

    public BarCanvasView(Context context) {
        this(context, null);
    }

    public BarCanvasView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init();
    }

    public BarCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
    }

    public void setFgColor(int color) {
        fgColor = color;
        invalidate();
        requestLayout();
    }

    public void setBgColor(int color) {
        bgColor = color;
        invalidate();
        requestLayout();
    }

    public void setPercentage(double val) {
        if (val < 0) percentage = 0.;
        else if (val > 1.) percentage = 1.;
        percentage = val;
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = canvas.getWidth(), h = canvas.getHeight();
        bgPaint.setColor(bgColor);
        fgPaint.setColor(fgColor);
        bgRect.set(0, 0, w, h);
        fgRect.set(0, 0, (int) (percentage * w), h);
        canvas.drawRect(bgRect, bgPaint);
        canvas.drawRect(fgRect, fgPaint);
    }
}
