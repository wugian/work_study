package com.penley.myview.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.penley.myview.R;

import java.util.ArrayList;

public class StartViewActivity extends Activity {
    private ListView demoLv;
    private Context context;

    private void assignViews() {
        context = this;
        demoLv = (ListView) findViewById(R.id.demo_lv);
        demoLv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getList()));
        demoLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(context, DrawCircleActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(context, HideSomeActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(context, AutoTypeActivity.class));
                    case 3:
                        startActivity(new Intent(context, AwardActivity.class));
                        break;
                }
            }
        });
    }

    private ArrayList<String> getList() {
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("自动画圆");
        strings.add("遮罩效果");
        strings.add("自动打字");
        strings.add("奖票效果");
        return strings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_view);
        assignViews();
    }
}
