package com.madreain.mvphulk.application;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author madreain
 * @date 2019-07-06.
 * module：
 * description：请求头拦截器
 */
public class RequestHeaderInterceptor implements Interceptor {

    //统一请求头的封装根据自身项目添加

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request authorised;
        Headers headers = new Headers.Builder()
                .add("test", "test")
                .build();
        authorised = request.newBuilder().headers(headers).build();
        return chain.proceed(authorised);
    }
}
