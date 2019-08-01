package com.madreain.mvphulk.application;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.request.target.ViewTarget;
import com.madreain.hulk.application.HulkApplication;
import com.madreain.hulk.config.HulkConfig;
import com.madreain.hulk.utils.Utils;
import com.madreain.mvphulk.BuildConfig;
import com.madreain.mvphulk.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;


import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author madreain
 * @date 2019-07-06.
 * module：
 * description：
 */
public class MVPHulkApplication extends HulkApplication {

    @Inject
    DispatchingAndroidInjector<MVPHulkApplication> mvpHulkApplicationDispatchingAndroidInjector;

    private static Appcomponent appcomponent;

    public static Appcomponent getAppcomponent() {
        return appcomponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
   }

    @Override
    protected AndroidInjector<MVPHulkApplication> applicationInjector() {
        return mvpHulkApplicationDispatchingAndroidInjector;
    }

    @Override
    public void initHulkConfig() {
        //DaggerAppComponent的生成make project一下就行
        appcomponent = DaggerAppcomponent.builder().apiModule(new com.madreain.hulk.application.ApiModule()).build();
        //消息拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //配置项
        HulkConfig.builder()
                .setApplication(this)
                //这里只需要选择设置一个
                .setRetSuccess(BuildConfig.CODE_SUCCESS)
//                .setRetSuccessList(BuildConfig.CODELIST_SUCCESS)
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

    //static 代码段可以防止内存泄露
    static {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

}
