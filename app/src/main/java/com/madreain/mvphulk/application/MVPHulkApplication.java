package com.madreain.mvphulk.application;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.request.target.ViewTarget;
import com.madreain.hulk.application.HulkApplication;
import com.madreain.hulk.config.HulkConfig;
import com.madreain.hulk.utils.Utils;
import com.madreain.mvphulk.BuildConfig;
import com.madreain.mvphulk.R;

import dagger.android.AndroidInjector;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author madreain
 * @date 2019-07-06.
 * module：
 * description：
 */
public class MVPHulkApplication extends HulkApplication {

    private static Appcomponent appcomponent;

    public static Appcomponent getAppcomponent() {
        return appcomponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }

    @Override
    public void initHulkConfig() {
        //DaggerAppComponent的生成make project一下就行
        appcomponent=DaggerAppcomponent.builder().apiModule(new com.madreain.hulk.application.ApiModule()).build();
        //消息拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //配置项
        HulkConfig.builder()
                .setApplication(this)
                .setRetSuccess(String.valueOf(BuildConfig.CODE_SUCCESS))
                .setBaseUrl(BuildConfig.BASE_URL)
                .setChangeBaseUrl(BuildConfig.OPEN_CHANGE)
                .setOpenLog(BuildConfig.OPEN_LOG)
                .addOkHttpInterceptor(new RequestHeaderInterceptor())//请求头拦截器
                .addOkHttpInterceptor(BuildConfig.OPEN_LOG, logging)//okhttp请求日志开关+消息拦截器.md
                .addRetCodeInterceptor(new SessionInterceptor())// returnCode非正常态拦截器
                .setGlidePlaceHolder(new ColorDrawable(Color.parseColor("#ffffff")))//默认占位图--颜色
                .setGlideHeaderPlaceHolder(getResources().getDrawable(R.mipmap.ic_launcher))//默认头像占位图
                .setRetrofit(appcomponent.getRetrofit())
                .build();
        appcomponent.inject(this);
        //application 上下文
        Utils.init(this);
        //Glide设置tag
        ViewTarget.setTagId(R.id.glide_tag);
        //log日子开关
        initLog();
        //ARouter
        initArouter();
    }

    private void initArouter() {
        //ARouter的初始化
        ARouter.init(this);
    }

    private void initLog() {
        //测试环境
        if (BuildConfig.OPEN_LOG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
    }

}
