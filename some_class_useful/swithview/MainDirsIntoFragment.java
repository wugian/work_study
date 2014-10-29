package com.xgimi.yunphotos.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.xgimi.yunphotos.R;
import com.xgimi.yunphotos.widgets.SwitchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2014 极米科技有限公司, Inc
 * <p/>
 * 主目录(切换 云相册目录，本地目录)
 *
 * @author hailongqiu <356752238@qq.com>
 * @Maintainer hailongqiu <356752238@qq.com>
 */

@EFragment(R.layout.f_main_dirs_into)
public class MainDirsIntoFragment extends BaseFragment {

    @ViewById
    ViewPager dirs_vpage;
    @ViewById
    SwitchView switch_btn;

    private List<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    public void initAllDatas() {

    }

    @Override
    void initWidgets() {
        setTitleBar(null, null, "", "+");
        if (fragments.size() == 0) {
            fragments.add(NetWorkImageListFragment_
                    .newNetWorkImageList(getFragmentManager()));
            fragments
                    .add(ImageListFragment_.newImageList(getFragmentManager()));
        }
        dirs_vpage.setAdapter(new MyFragmentAdapter(getChildFragmentManager(),
                fragments));
        dirs_vpage.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int index) {
                if (index == 0) {
                    setTitleBar(null, View.VISIBLE, "", "+");
                    switch_btn.setChecked(true);
                  //  switch_btn.switching();
                } else if (index == 1) {
                    switch_btn.setChecked(false);
                    setTitleBar(null, View.GONE, "", "+");
                 //   switch_btn.switching();
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        dirs_vpage.setCurrentItem(0);

        switch_btn.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked) {
                if (isChecked) {
                    nt_btn();
                }else{
                    local_btn();
                }
            }
        });

//        switch_btn.set
//        switch_btn.setOnSwitchListener(new SwitchView.OnSwitchListener() {
//            @Override
//            public void onCheck(SwitchView sv, boolean checkLeft, boolean checkRight) {
//                if (checkLeft) {
//                    nt_btn();
//                }
//                if (checkRight) {
//                    local_btn();
//                }
//            }
//        });
    }

    public static class MyFragmentAdapter extends FragmentPagerAdapter {
        List<Fragment> fList;

        public MyFragmentAdapter(FragmentManager fm, List<Fragment> fList) {
            super(fm);
            this.fList = fList;
        }

        @Override
        public Fragment getItem(int index) {
            return fList.get(index);
        }

        @Override
        public int getCount() {
            return fList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

    }

    private final int NETWORK_DIR_STATE = 0;
    private final int LOCAL_DIR_STATE = 1;

    /**
     * 网络目录列表.
     */
    @Click
    void nt_btn() {
        dirs_vpage.setCurrentItem(NETWORK_DIR_STATE);
    }

    /**
     * 本地目录列表.
     */
    @Click
    void local_btn() {
        dirs_vpage.setCurrentItem(LOCAL_DIR_STATE);
    }

    /**
     * 标题栏 "+" <br>
     * 添加网络目录操作.<br>
     */
    @Click
    void title_login_btn() {
        startFragment(new CreateNetWorkDirFragment_(), null);
    }

}
