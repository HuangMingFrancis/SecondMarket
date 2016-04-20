package com.ketangpai.application;

import android.app.Application;

import com.ketangpai.utils.AppContextUtils;


/**
 * Created by nan on 2016/3/10.
 */
public class kechengpaiApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppContextUtils.initContext(this);

    }
}
