package com.madreain.hulk.http.resourceSubscriber;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：配合Retrofit使用 网络请求使用的RxJava观察者
 *              ResourceSubscriber：允许异步取消其订阅相关资源，节省内存而且是线程安全。
 */
public abstract class RSubscriberAbstract<T> extends ResourceSubscriber<T> {

    /**
     * 请求接口返回的数据
     * @param t
     */
    public abstract void _onNext(T t);

    /**
     * 返回的BaseRes 中的泛型T为空
     */
    public abstract void _onTEmpty();//返回的BaseRes 中的泛型T为空

    /**
     * 网络错误
     */
    public abstract void _onNetWorkError();

    /**
     * 接口调用操作出现异常，返回异常的响应码
     * @param returnCode
     * @param msg
     */
    public abstract void _onReturnCodeError(String returnCode, String msg);

    /**
     * 重试（网络请求返回数据为空或网络异常时，重新请求接口）
     */
    public abstract void retry();//空页面或者网络出错，重新请求

}
