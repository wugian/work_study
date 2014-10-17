package com.xgimi.myhostmain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgimi.myhostmain.PluginBean;
import com.xgimi.myhostmain.R;

import java.util.ArrayList;

/**
 * @author 李攀 pan[dot]li@xgimi[dot]com
 * @describe
 * @date 2014/10/17
 */
public class AppListAdapter extends BaseAdapter {

    private ArrayList<PluginBean> datas;
    private Context context;
    LayoutInflater layoutInflater;

    public AppListAdapter(ArrayList<PluginBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //charge the data is null
        if (datas == null) {
            return null;
        }
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.li_app, null);
            viewHolder.appImg = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.appName = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //init
        viewHolder.appName.setText(datas.get(position).getLabel());
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            spublic void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction(datas.get(position).getPakageName());
//                context.startActivity(intent);
////                context.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
//            }
//        });
        return convertView;
    }

    static class ViewHolder {
        ImageView appImg;
        TextView appName;
    }
}
