package com.xgimi.myhostmain;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xgimi.myhostmain.adapters.AppListAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    ListView mPluginLv;
    ArrayList<PluginBean> datas;

    private void assignViews() {
        mPluginLv = (ListView) findViewById(R.id.plugin_lv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        datas = findPlugins();
        mPluginLv.setAdapter(new AppListAdapter(datas, this));
        mPluginLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction(datas.get(position).getPakageName());
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
    }

    /**
     * 查找插件
     *
     * @return
     */
    private ArrayList<PluginBean> findPlugins() {
        ArrayList<PluginBean> plugins = new ArrayList<PluginBean>();
        //遍历包名，来获取插件
        PackageManager pm = getPackageManager();
        List<PackageInfo> pkgs = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo pkg : pkgs) {
            //包名
            String packageName = pkg.packageName;
            String sharedUserId = pkg.sharedUserId;
            //sharedUserId是开发时约定好的，这样判断是否为自己人
            if (!"com.xgimi".equals(sharedUserId) || "com.xgimi.myhostmain".equals(packageName))
                continue;
            //进程名
            String prcessName = pkg.applicationInfo.processName;

//            Drawable c = pm.getApplicationIcon(pkg.applicationInfo);
            //label，也就是appName了
            String label = pm.getApplicationLabel(pkg.applicationInfo).toString();

            PluginBean plug = new PluginBean();
            plug.setLabel(label);
            plug.setPakageName(packageName);

            plugins.add(plug);
        }
        return plugins;

    }
}
