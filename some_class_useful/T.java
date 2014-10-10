package com.xgimi.dressingshow.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * source by http://www.oschina.net/code/snippet_1183407_32629
 * optimized by Penley (pan[dot]li[at]xgimi[dot]com)
 */
public class T {
    private static Toast toast;
    private static Handler handler = new Handler();

    private static Runnable run = new Runnable() {
        public void run() {
            toast.cancel();
        }
    };

    private static void toast(Context ctx, CharSequence msg) {
        handler.removeCallbacks(run);
        // handler的duration不能直接对应Toast的常量时长，在此针对Toast的常量相应定义时长
        if (null != toast) {
            toast.setText(msg);
        } else {
            toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
        }
        handler.postDelayed(run, 1000);
        toast.show();
    }
    private static void toastLong(Context ctx, CharSequence msg) {
        handler.removeCallbacks(run);
        // handler的duration不能直接对应Toast的常量时长，在此针对Toast的常量相应定义时长
        if (null != toast) {
            toast.setText(msg);
        } else {
            toast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);
        }
        handler.postDelayed(run, 3000);
        toast.show();
    }

    /**
     * 弹出Toast
     *
     * @param ctx 弹出Toast的上下文
     * @param msg 弹出Toast的内容
     *
     */
    public static void showLong(Context ctx, CharSequence msg)
            throws NullPointerException {
        if (null == ctx) {
            throw new NullPointerException("The context is null!");
        }
        toastLong(ctx, msg);
    }

    /**
     * 弹出Toast
     *
     * @param ctx 弹出Toast的上下文
     * @param resId 弹出Toast的内容的资源ID
     *
     */
    public static void showLong(Context ctx, int resId)
            throws NullPointerException {
        if (null == ctx) {
            throw new NullPointerException("The ctx is null!");
        }
        toastLong(ctx, ctx.getResources().getString(resId));
    }

    /**
     * 弹出Toast
     *
     * @param ctx 弹出Toast的上下文
     * @param msg 弹出Toast的内容
     *
     */
    public static void show(Context ctx, CharSequence msg)
            throws NullPointerException {
        if (null == ctx) {
            throw new NullPointerException("The context is null!");
        }
        toast(ctx, msg);
    }

    /**
     * 弹出Toast
     *
     * @param ctx 弹出Toast的上下文
     * @param resId 弹出Toast的内容的资源ID
     *
     */
    public static void show(Context ctx, int resId)
            throws NullPointerException {
        if (null == ctx) {
            throw new NullPointerException("The ctx is null!");
        }
        toast(ctx, ctx.getResources().getString(resId));
    }

    public static void cancel(){
        if (toast!=null){
            handler.postDelayed(run, 20);
        }
    }
}