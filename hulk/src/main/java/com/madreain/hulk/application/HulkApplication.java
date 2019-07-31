package com.madreain.hulk.application;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.madreain.hulk.utils.ARouterUtils;
import com.madreain.hulk.utils.Utils;

import dagger.android.DaggerApplication;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：
 */
public abstract class HulkApplication extends DaggerApplication {

    public abstract void initHulkConfig();//初始化配置参数

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        initHulkConfig();
//        //测试环境
//        if (BuildConfig.OPEN_LOG) {
//            ARouter.openLog();     // 打印日志
//            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }
        //ARouter的初始化
        ARouter.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouterUtils.destory();
    }

}
