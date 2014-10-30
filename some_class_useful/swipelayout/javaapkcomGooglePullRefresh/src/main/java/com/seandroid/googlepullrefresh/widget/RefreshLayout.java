package com.seandroid.googlepullrefresh.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * @author 李攀 pan[dot]li@xgimi[dot]com
 * @describe
 * @date 2014/10/27
 */
public class RefreshLayout extends SwipeRefreshLayout{
    private OnRefreshUpListener mUpListener;

    public RefreshLayout(Context context) {
        super(context);
    }

    public void setmUpListener(OnRefreshUpListener mUpListener) {
        this.mUpListener = mUpListener;
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public interface OnRefreshUpListener {
        public void onRefreshUp();
    }
}
