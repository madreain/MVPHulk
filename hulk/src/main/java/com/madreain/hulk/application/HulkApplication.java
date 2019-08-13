package com.madreain.hulk.application;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.madreain.hulk.utils.ARouterUtils;
import com.madreain.hulk.utils.Utils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasBroadcastReceiverInjector;
import dagger.android.HasContentProviderInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.HasServiceInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：
 */
public abstract class HulkApplication extends Application implements
        HasActivityInjector, HasFragmentInjector, HasSupportFragmentInjector, HasBroadcastReceiverInjector, HasServiceInjector, HasContentProviderInjector{

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<android.app.Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<android.support.v4.app.Fragment> fragmentDispatchingAndroidInjectorv4;

    @Inject
    DispatchingAndroidInjector<BroadcastReceiver> broadcastReceiverDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Service> serviceDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<ContentProvider> contentProviderDispatchingAndroidInjector;


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<android.app.Fragment> fragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjectorv4;
    }

    @Override
    public AndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
        return broadcastReceiverDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<ContentProvider> contentProviderInjector() {
        return contentProviderDispatchingAndroidInjector;
    }

    public abstract void initHulkConfig();//初始化配置参数

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        //配置初始化
        initHulkConfig();
        //ARouter的初始化
        ARouter.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouterUtils.destory();
    }

}
