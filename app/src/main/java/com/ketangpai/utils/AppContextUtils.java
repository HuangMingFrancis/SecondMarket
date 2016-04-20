package com.ketangpai.utils;

import android.content.Context;

/**
 * Created by nan on 2016/3/10.
 */
public class AppContextUtils {
    private  static Context mContext;

    public static void initContext(Context Context){
        mContext=Context;
    }

    public  static  Context getInstance(){
        if(mContext==null){
            throw new NullPointerException("context is null in appcontxtutils,please init");
        }
        return  mContext;


    }

}
