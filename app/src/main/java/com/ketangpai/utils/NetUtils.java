package com.ketangpai.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by nan on 2016/3/10.
 */
public class NetUtils {


    public static boolean hasNetworkConnection() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) AppContextUtils.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        boolean connected = (null != mNetworkInfo)
                && mNetworkInfo.isConnected();

        if (!connected) {
            return false;
        }


        return (connected);
    }

    public static boolean isWifiReachable() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) AppContextUtils.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

        if (mNetworkInfo == null) {
            return false;
        }

        return (mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }

}
