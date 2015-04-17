package com.penley.myview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 李攀 on 2015/4/16.
 */
public class FirstView extends View implements Runnable {
    private String str = "天空长度ss";

    private Paint paint;
    private Paint textPaint;
    private Paint.FontMetrics fontMetrics;
    private int radius = 140;

    private int width;
    private int height;

    public FirstView(Context context) {
        super(context);
    }

    public FirstView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        //搞锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(32);
        textPaint.setStrokeWidth(6);
        textPaint.setColor(Color.RED);


        width = getMeasuredWidth();
        height = getMeasuredHeight();
        fontMetrics = textPaint.getFontMetrics();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = widthMeasureSpec;
        this.height = heightMeasureSpec;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.DKGRAY);
        canvas.drawRect(10, 10, 400, 400, paint);

        paint.setColor(Color.BLUE);
        canvas.drawCircle(200, 200, radius, paint);
        canvas.drawText(str, 100, 190, textPaint);
    }

    boolean up = true;

    @Override
    public void run() {
        while (true) {
            try {
                if (up) {
                    radius += 10;
                } else {
                    radius -= 10;
                }
                postInvalidate();

                if (radius > 170) {
                    up = false;
                }
                if (radius < 40) {
                    up = true;
                }
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
