package com.penley.myview.views;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 李攀 on 2015/4/17.
 */
public class AwardView extends View {
    private Paint paint;
    Path path;
    Canvas canvas;
    Bitmap bitmap;
    float lastX, lastY;

    private Paint backPaint;
    private Rect rect;
    private String text = "awards";
    private boolean isComplete = false;

    public AwardView(Context context) {
        super(context);
    }

    public AwardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AwardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setText(String text) {
        this.text = text;
    }



    void init() {
        paint = new Paint();
        path = new Path();
        paint.setStyle(Paint.Style.STROKE);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(40);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        intBackPaint();
    }

    private void intBackPaint() {
        backPaint = new Paint();
        rect = new Rect();
        backPaint.setStyle(Paint.Style.FILL);
        backPaint.setTextScaleX(2f);
        backPaint.setColor(Color.DKGRAY);
        backPaint.setTextSize(40);
        backPaint.getTextBounds(text, 0, text.length(), rect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        init();
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.parseColor("#ff0000"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text, getWidth() / 2 - rect.width() / 2,
                getHeight() / 2 + rect.height() / 2, backPaint);
//        canvas.drawBitmap(backBitmap, 0, 0, null);
        if (!isComplete) {
            drawPath();
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }

    private void drawPath() {
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                path.moveTo(lastX, lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(lastX - x);
                float dy = Math.abs(lastY - y);
                if (dx > 3 || dy > 3) {
                    path.lineTo(x, y);
                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                new Thread(mRunnable).start();
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 统计擦除区域任务
     */
    private Runnable mRunnable = new Runnable() {
        private int[] mPixels;

        @Override
        public void run() {

            int w = getWidth();
            int h = getHeight();

            float wipeArea = 0;
            float totalArea = w * h;

            Bitmap mBitmap = bitmap;

            mPixels = new int[w * h];

            /**
             * 拿到所有的像素信息
             */
            mBitmap.getPixels(mPixels, 0, w, 0, 0, w, h);

            /**
             * 遍历统计擦除的区域
             */
            for (int i = 0 ; i < w ; i++) {
                for (int j = 0 ; j < h ; j++) {
                    int index = i + j * w;
                    if (mPixels[index] == 0) {
                        wipeArea++;
                    }
                }
            }

            /**
             * 根据所占百分比，进行一些操作
             */
            if (wipeArea > 0 && totalArea > 0) {
                int percent = (int) (wipeArea * 100 / totalArea);
                Log.d("TAG", percent + "");
                if (percent > 60) {
                    isComplete = true;
                    postInvalidate();
                }
            }
        }
    };
}
