package com.penley.myview.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.*;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.penley.myview.R;

/**
 * Created by 李攀 on 2015/4/16.
 */
public class SecondView extends View {
    private String str = "天空长度ss";
    String bbs = "天空之友左天空之友左天空之友左天空之友左天空之友左天空之友左天空之友左天空之友左天空之友左天空之友左天空之友左天空之友左天空之友左天空之友左天空之友左天空之友左天空之友左天空之友左完";

    private float x = 150;
    private float y = 150;

    private Paint paint, mFillPaint, textPaint;
    private BitmapShader mBitmapShader;
    private Path mPath;


    public SecondView(Context context) {
        super(context);
        initPaint();
    }

    public SecondView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public SecondView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SecondView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    private void initPaint() {
        //搞锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFFffffff);
        paint.setTextSize(15);
        paint.setStrokeWidth(3);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(0xFFffffff);
        textPaint.setTextSize(25);
        textPaint.setStrokeWidth(1);

        mFillPaint = new Paint();
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tile1);
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mFillPaint.setShader(mBitmapShader);

        mPath = new Path();
        mPath.quadTo(200, 200, 136, 248);
        mPath.lineTo(440, 440);
        mPath.rLineTo(300, 300);
        mPath.rLineTo(200, 200);
//        RectF rectF = new RectF(200, 200, 400, 600);
//        mPath.addOval(rectF, Path.Direction.CCW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.DKGRAY);
        canvas.drawCircle(x, y, 150, mFillPaint);
        canvas.drawCircle(x, y, 150, paint);
        canvas.drawTextOnPath(bbs, mPath, 20, 20, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            x = event.getX();
            y = event.getY();
            if ((x < 150)) {
                x = 150;
            }
            if ((y < 150)) {
                y = 150;
            }
            if (x > getMeasuredWidth() - 150) {
                x = getMeasuredWidth() - 150;
            }
            if (y > getMeasuredHeight() - 150) {
                y = getMeasuredHeight() - 150;
            }
            invalidate();
        }
        return true;
    }


}
