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

public class GridCanvasView extends View {
    int fgColor = ResourcesCompat.getColor(getResources(), R.color.colorTimeLeft, null);
    int bgColor = ResourcesCompat.getColor(getResources(), R.color.light_light_grey, null);
    Rect fgRect = new Rect();
    Rect bgRect = new Rect();
    Paint bgPaint = new Paint();
    Paint fgPaint = new Paint();
    double percentage = 0;
    double marginRatio = 0.2;
    int squaresPerLine = 10;
    int nSquares = 40;

    public GridCanvasView(Context context) {
        this(context, null);
    }

    public GridCanvasView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init();
    }

    public GridCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

    public void setSquaresPerLine(int n) {
        squaresPerLine = n;
    }

    public void setnSquares(int n) {
        if (n <= 0) return;
        nSquares = n;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = canvas.getWidth();
        double dx = w / (squaresPerLine + (squaresPerLine - 1) * marginRatio);
        int x, y;
        int i;

        bgPaint.setColor(bgColor);
        fgPaint.setColor(fgColor);

        for (i = 0; i < (int) (nSquares * percentage); i++) {
            x = i % squaresPerLine;
            y = i / squaresPerLine;
            fgRect.set((int) ((dx * (1 + marginRatio)) * x),
                    (int) ((dx * (1 + marginRatio)) * y),
                    (int) ((dx * (1 + marginRatio)) * x + dx),
                    (int) ((dx * (1 + marginRatio)) * y + dx));
            canvas.drawRect(fgRect, fgPaint);
        }
        for (; i < nSquares; i++) {
            x = i % squaresPerLine;
            y = i / squaresPerLine;
            bgRect.set((int) ((dx * (1 + marginRatio)) * x),
                    (int) ((dx * (1 + marginRatio)) * y),
                    (int) ((dx * (1 + marginRatio)) * x + dx),
                    (int) ((dx * (1 + marginRatio)) * y + dx));
            canvas.drawRect(bgRect, bgPaint);
        }
    }

    private int getDesiredHeight(int width) {
        double x = width / (squaresPerLine + (squaresPerLine - 1) * marginRatio);
        int nLines = nSquares / squaresPerLine + (nSquares % squaresPerLine == 0 ? 0 : 1);
        return (int) (nLines * x + (nLines - 1) * x * marginRatio);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int desiredWidth = widthSize;
        int desiredHeight = getDesiredHeight(widthSize);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }
}
