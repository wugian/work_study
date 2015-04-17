package com.penley.myview.views;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import com.penley.myview.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 李攀 on 2015/4/17.
 */
public class AutoTypeTextView extends TextView {

    private static final int DEFAULT_TIME_DELAY = 80;

    private boolean voiceOn = true;

    private Context mContext;
    private String mText;
    private int mDelay;
    private Timer mTimer;
    private TypeListener typeListener;

    private SoundPool soundPool;
    private int soundId;


    public interface TypeListener {
        void onStart();

        void onFinish();
    }

    public AutoTypeTextView(Context context) {
        super(context);
        init(context);
    }

    public AutoTypeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoTypeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(context, R.raw.type_in, 1);
    }

    public void setVoice(boolean b) {
        voiceOn = b;
    }


    public void setTypeListener(TypeListener typeListener) {
        this.typeListener = typeListener;
    }

    public void setTextAndStart(final String text) {
        setTextAndStart(text, DEFAULT_TIME_DELAY);
    }

    public void setTextAndStart(final String text, final int delay) {
        if (TextUtils.isEmpty(text) || delay < 0) {
            return;
        }
        post(new Runnable() {
            @Override
            public void run() {
                mText = text;
                mDelay = delay;
                setText("");
                startTimerType();
                if (typeListener != null) {
                    typeListener.onStart();
                }
            }
        });
    }

    private void startTimerType() {
        stopTimerType();
        mTimer = new Timer();
        mTimer.schedule(new TimeTypeTask(), mDelay);
    }

    private void stopTimerType() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private class TimeTypeTask extends TimerTask {
        @Override
        public void run() {
            post(new Runnable() {
                @Override
                public void run() {
                    if (getText().toString().length() < mText.length()) {
                        setText(mText.substring(0, getText().toString().length() + 1));
                        playSound();
                        startTimerType();
                    } else {
                        stopTimerType();
                        if (typeListener != null) {
                            typeListener.onFinish();
                        }
                    }
                }
            });

        }
    }

    private void playSound() {
        if (voiceOn) {
            soundPool.play(soundId, 0.6f, 0.6f, 0, 0, 2.0f);
        }
    }
}
