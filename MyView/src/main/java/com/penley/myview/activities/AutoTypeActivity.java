package com.penley.myview.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.penley.myview.R;
import com.penley.myview.views.AutoTypeTextView;
import com.penley.myview.views.TypeTextView;

public class AutoTypeActivity extends ActionBarActivity {
    String text = "公开报道显示，2010年《人民公安报》曾就“金盾工程”对时任公安部科技信息化局总工程师马晓东进行专访。作为“金盾工程”骨干成员之一，马晓东在本次对话中介绍了彼时“金盾工程”的进展情况以及亟待改善的问题。\n 2011年5月16日，《人民公安报》再次刊发有关马晓东的报道。在一篇名为《开创公安无线通信数字化新天地—访中国警用数字集群标准研发工作负责人马晓东总工程师》文章中，称马晓东是“倡导、引导、指导这一创新技术标准体制（警用数字集群 —Police Digital Trunking，简称PDT）的具体负责人，从最初的可行性研究，到制订各项标准，再到设计实用产品，始终就像妈妈照料婴儿一般，悉心呵护着PDT的每一步成长”。“实际上，PDT就相当于公安内部搞的3G标准。”该文援引工业和信息化部的一位专家的评价称。";
    private AutoTypeTextView autoTypeTextView;
    private TypeTextView typeTv;

    private void assignViews() {
        autoTypeTextView = (AutoTypeTextView) findViewById(R.id.autoTypeTextView);
        typeTv = (TypeTextView) findViewById(R.id.typeTv);

        autoTypeTextView.setTypeListener(new AutoTypeTextView.TypeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
//                typeTv.start(text);
            }
        });
        autoTypeTextView.setTextAndStart(text, 240);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_type);
        assignViews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_auto_type, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
