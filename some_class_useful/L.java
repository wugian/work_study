package com.xgimi.dressingshow.utils;

import android.util.Log;

/**
 * Log Msg
 *
 * @author 李攀 (pan[dot]li[at]xgimi[dot]com)
 */
public class L {
    private static final boolean debug = true;

    public static void d(String msg) {
        if (debug)
            Log.d("DEBUG MSG", msg);
    }

    public static void d(String tag, String msg) {
        if (debug)
            Log.d(tag, msg);
    }
    public static void w(String msg) {
        if (debug)
            Log.d("DEBUG MSG", msg);
    }

    public static void w(String tag, String msg) {
        if (debug)
            Log.d(tag, msg);
    }
    public static void e(String msg) {
        if (debug)
            Log.d("DEBUG MSG", msg);
    }

    public static void e(String tag, String msg) {
        if (debug)
            Log.d(tag, msg);
    }
    public static void v(String msg) {
        if (debug)
            Log.d("DEBUG MSG", msg);
    }

    public static void v(String tag, String msg) {
        if (debug)
            Log.d(tag, msg);
    }
}
