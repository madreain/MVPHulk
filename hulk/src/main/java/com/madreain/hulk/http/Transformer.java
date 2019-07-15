package com.madreain.hulk.http;

import com.madreain.hulk.base.LibActivity;
import com.madreain.hulk.base.LibDialogFragment;
import com.madreain.hulk.base.LibFragment;
import com.madreain.hulk.config.HulkConfig;
import com.madreain.hulk.http.exception.NetWorkException;
import com.madreain.hulk.http.exception.ResultException;
import com.madreain.hulk.http.exception.ReturnCodeException;
import com.madreain.hulk.http.interceptor.IVersionDiffInterceptor;
import com.madreain.hulk.mvp.IRes;
import com.madreain.hulk.mvp.IView;
import com.madreain.hulk.utils.LogUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：线程切换，flatMap检测Response model是否为null
 */

public class Transformer {

    public static <T> FlowableTransformer<T, T> ioMain() {
        return flowable -> flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> LifecycleTransformer<T> bindToLifecycle(IView view) {
        if (view instanceof LibActivity) {
            return ((LibActivity) view).bindUntilEvent(ActivityEvent.DESTROY);
        } else if (view instanceof LibFragment) {
            return ((LibFragment) view).bindUntilEvent(FragmentEvent.DESTROY);
        } else if (view instanceof LibDialogFragment) {
            return ((LibDialogFragment) view).bindUntilEvent(FragmentEvent.DESTROY);
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }
    }

    /**
     * @param <T> BaseRes类中的泛型 -即有result
     */
    public static <T> FlowableTransformer<IRes<T>, T> retrofit(final IView iView) {
        return flowable -> {
            return flowable
                    .compose(Transformer.retrofitIo(iView))
                    .observeOn(AndroidSchedulers.mainThread());
        };
    }

    /**
     * 接口响应只有BaseRes,内部的泛型为null
     */
    public static <B extends IRes> FlowableTransformer<B, B> retrofitBaseRes(final IView iView) {
        return flowable -> flowable
                .compose(retrofitBaseResIo(iView))
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param <T> BaseRes类中的泛型 -即有result
     */
    private static <T> FlowableTransformer<IRes<T>, T> retrofitIo(final IView iView) {
        return flowable -> {
            return flowable.flatMap(baseRes -> {
                if (baseRes == null) {//当作没有网络
                    return Flowable.error(new NetWorkException());
                } else {//有网络
                    //版本号不一致
//                    isVersionDiff(iView, baseRes.getVersion());
                    LogUtils.d("11111111","成功码： "+HulkConfig.getRetSuccess());
                    LogUtils.d("11111111","返回码： "+baseRes.getCode());
                    if (HulkConfig.getRetSuccess().equals(baseRes.getCode())) {
                        T t = baseRes.getResult();
                        if (t == null || (t instanceof List && ((List) t).size() == 0)) {
                            return Flowable.error(new ResultException("response's model is null"));
                        } else {
                            return Flowable.just(baseRes.getResult());
                        }
                    } else {
//                        如果网络未连接不会调用flatMap,所以网络未连接不会出现ServerException错误
                        return Flowable.error(new ReturnCodeException(baseRes.getCode(), baseRes.getMsg()));
                    }
                }
            }).compose(Transformer.bindToLifecycle(iView)).subscribeOn(Schedulers.io());//线程切换
        };
    }

    /**
     * 接口响应只有BaseRes,内部的泛型为null
     */
    private static <B extends IRes> FlowableTransformer<B, B> retrofitBaseResIo(final IView iView) {
        return flowable ->
                flowable.flatMap(baseRes -> {
                    if (baseRes == null) {//当作没有网络
                        return Flowable.error(new NetWorkException());
                    } else {//有网络
                        //版本号不一致
//                        isVersionDiff(iView, baseRes.getVersion());
                        if (HulkConfig.getRetSuccess().equals(baseRes.getCode())) {//数据请求正常
                            return Flowable.just(baseRes);
                        } else {
                            //如果网络未连接不会调用flatMap,所以网络未连接不会出现ServerException错误
                            return Flowable.error(new ReturnCodeException(baseRes.getCode(), baseRes.getMsg()));
                        }
                    }
                }).compose(Transformer.bindToLifecycle(iView)).subscribeOn(Schedulers.io());
    }

    /**
     * 版本不一致处理
     *
     * @param iView       IView
     * @param serviceVersion serviceVersion
     */
    private static void isVersionDiff(IView iView, String serviceVersion) {
        for (IVersionDiffInterceptor interceptor : HulkConfig.getVersionDiffInterceptors()) {
            if (interceptor.intercept(serviceVersion)) {
                interceptor.todo(iView, serviceVersion);
                break;
            }
        }
    }
}
