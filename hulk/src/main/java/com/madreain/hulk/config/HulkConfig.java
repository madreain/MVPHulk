package com.madreain.hulk.config;

import android.app.Application;
import android.graphics.drawable.Drawable;

import com.madreain.hulk.http.interceptor.IReturnCodeErrorInterceptor;
import com.madreain.hulk.http.interceptor.IVersionDiffInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import retrofit2.Retrofit;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：
 */
public class HulkConfig {
    private static Application application;
    private static String baseUrl;//服务地址
    private static String retSuccess;//returnCode 正常态的值
    private static boolean logOpen;//日志开关
    private static boolean changeBaseUrl;//切换环境
    private static Retrofit retrofit;
    private static List<Interceptor> okHttpInterceptors = new ArrayList<>();//oKHttp拦截器
    private static List<IReturnCodeErrorInterceptor> retCodeInterceptors = new ArrayList<>();//接口返回returnCode不是正常态拦截器
    private static List<IVersionDiffInterceptor> versionDiffInterceptors = new ArrayList<>();//服务端版本和本地不一致插入处理代码
    private static long connectTimeout = 15;//连接超时时间：单位秒
    private static long readTimeout = 30;//单位秒
    private static long writeTimeout = 60;//单位秒
    private static Drawable glidePlaceHolder;//默认图
    private static Drawable glideHeaderPlaceHolder;//默认头像占位图
    private static Builder builder;

    public synchronized static Builder builder() {
        if (builder == null) {
            builder = new Builder();
        }
        return builder;
    }

    public static class Builder {

        public Builder setApplication(Application application) {
            HulkConfig.application = application;
            return this;
        }

        public Builder setBaseUrl(String baseUrl) {
            HulkConfig.baseUrl = baseUrl;
            return this;
        }

        public Builder setRetSuccess(String retSuccess) {
            HulkConfig.retSuccess = retSuccess;
            return this;
        }

        public Builder setRetrofit(Retrofit retrofit) {
            HulkConfig.retrofit = retrofit;
            return this;
        }

        public Builder setOpenLog(boolean logOpen) {
            HulkConfig.logOpen = logOpen;
            return this;
        }

        public Builder setChangeBaseUrl(boolean changeBaseUrl) {
            HulkConfig.changeBaseUrl = changeBaseUrl;
            return this;
        }

        /**
         * 接口请求响应 returnCode不是正确的情况 拦截器.md
         *
         * @param interceptor IReturnCodeErrorInterceptor
         */
        public Builder addRetCodeInterceptor(IReturnCodeErrorInterceptor interceptor) {
            HulkConfig.retCodeInterceptors.add(interceptor);
            return this;
        }

        /**
         * @param interceptor okhttp 拦截器.md
         */
        public Builder addOkHttpInterceptor(Interceptor interceptor) {
            HulkConfig.okHttpInterceptors.add(interceptor);
            return this;
        }

        /**
         * @param mSwitch     开关控制拦截器是否添加
         * @param interceptor okhttp 拦截器.md
         */
        public Builder addOkHttpInterceptor(boolean mSwitch, Interceptor interceptor) {
            if (mSwitch)
                HulkConfig.okHttpInterceptors.add(interceptor);
            return this;
        }

        public Builder setConnectTimeout(long connectTimeout) {
            HulkConfig.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setReadTimeout(long readTimeout) {
            HulkConfig.readTimeout = readTimeout;
            return this;
        }

        public Builder setWriteTimeout(long writeTimeout) {
            HulkConfig.writeTimeout = writeTimeout;
            return this;
        }

        public Builder addVersionDiffInterceptors(IVersionDiffInterceptor interceptor) {
            HulkConfig.versionDiffInterceptors.add(interceptor);
            return this;
        }


        public Builder setGlidePlaceHolder(Drawable glidePlaceHolder) {
            HulkConfig.glidePlaceHolder = glidePlaceHolder;
            return this;
        }

        public Builder setGlideHeaderPlaceHolder(Drawable glideHeaderPlaceHolder) {
            HulkConfig.glideHeaderPlaceHolder = glideHeaderPlaceHolder;
            return this;
        }


        public void build() {
        }
    }

    public static Application getApplication() {
        return application;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        HulkConfig.baseUrl = baseUrl;
    }

    public static String getRetSuccess() {
        return retSuccess;
    }

    public static boolean isLogOpen() {
        return logOpen;
    }

    public static boolean isChangeBaseUrl() {
        return changeBaseUrl;
    }

    public static void setChangeBaseUrl(boolean changeBaseUrl) {
        HulkConfig.changeBaseUrl = changeBaseUrl;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static List<IReturnCodeErrorInterceptor> getInterceptors() {
        return retCodeInterceptors;
    }

    public static List<Interceptor> getOkHttpInterceptors() {
        return okHttpInterceptors;
    }

    public static long getConnectTimeout() {
        return connectTimeout;
    }

    public static long getReadTimeout() {
        return readTimeout;
    }

    public static long getWriteTimeout() {
        return writeTimeout;
    }

    public static List<IVersionDiffInterceptor> getVersionDiffInterceptors() {
        return versionDiffInterceptors;
    }

    public static Drawable getGlidePlaceHolder() {
        return glidePlaceHolder;
    }

    public static Drawable getGlideHeaderPlaceHolder() {
        return glideHeaderPlaceHolder;
    }
}
