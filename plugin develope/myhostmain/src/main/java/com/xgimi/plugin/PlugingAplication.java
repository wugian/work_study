package com.xgimi.plugin;

import android.app.Application;

import java.util.Map;

/**
 * @author 李攀 pan[dot]li@xgimi[dot]com
 * @describe
 * @date 2014/10/17
 */
public abstract class PlugingAplication<K,V> extends Application {
    public abstract Map<K,V> getDesciption();
}
