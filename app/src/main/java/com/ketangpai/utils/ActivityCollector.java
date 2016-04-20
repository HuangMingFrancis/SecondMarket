package com.ketangpai.utils;

import com.ketangpai.base.BaseActivity;

import java.util.LinkedList;

/**
 * Created by nan on 2016/3/9.
 */
public class ActivityCollector {
    public static LinkedList<BaseActivity> mActivitys = new LinkedList<>();

    public static void addActivity(BaseActivity activity) {
        mActivitys.add(activity);
    }

    public static void removeActivity(BaseActivity activity) {
        mActivitys.remove(activity);
    }

    public static void finishAllActivity() {
        for (BaseActivity activity : mActivitys) {

            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
