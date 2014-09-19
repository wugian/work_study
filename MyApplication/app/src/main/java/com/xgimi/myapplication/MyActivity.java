package com.xgimi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;


@EActivity(R.layout.activity_my)
public class MyActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        show();
    }

    @UiThread(delay = 3000)
    void show() {
        Toast.makeText(this, "lovely shutdown", Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
//        startActivity(intent);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        pm.reboot(null);
    }

    /**
     * 发送广播.
     *
     * @param view
     */

    public void onReboot(View view) {
        Intent reboot = new Intent(Intent.ACTION_REBOOT);
        reboot.putExtra("nowait", 1);
        reboot.putExtra("interval", 1);
        reboot.putExtra("window", 0);
        sendBroadcast(reboot);

    }

    /**
     * 启动 Activity.
     *
     * @param view
     */

    public void onShutdown(View view) {
        Intent shutdown = new Intent(Intent.ACTION_SHUTDOWN);
//        shutdown.putExtra(Intent.EXTRA_KEY_CONFIRM, showShutdownDialog);
        shutdown.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(shutdown);
    }
}
