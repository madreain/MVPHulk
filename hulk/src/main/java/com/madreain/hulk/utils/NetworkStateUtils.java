package com.madreain.hulk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：用于检测网路连接状态
 */

public class NetworkStateUtils {

    public static final int TYPE_NONE = -1;

    public static final int TYPE_MOBILE = 0;//当前网络是移动网络

    public static final int TYPE_WIFI = 1;//当前网络是wifi网络

    private ConnectivityManager manager;
    private NetworkInfo info;

    public static NetworkStateUtils networkState;

    public NetworkStateUtils(Context context) {
        manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static NetworkStateUtils getInstance(Context context) {
        if (networkState == null) {
            networkState = new NetworkStateUtils(context);
        }
        return networkState;
    }

    public int getType() {
        info = manager.getActiveNetworkInfo();
        if (info == null) {
            return TYPE_NONE;
        }
        return info.getType();
    }

    //是否有网络状态
    public boolean isConnection() {
        return getType() != TYPE_NONE;
    }

    //否是wifi网络
    public boolean isWifi() {
        info = manager.getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    //否是移动网络
    public boolean isMobile() {
        info = manager.getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }
}
