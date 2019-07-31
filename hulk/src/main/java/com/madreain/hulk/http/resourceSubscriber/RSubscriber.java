package com.madreain.hulk.http.resourceSubscriber;

import androidx.annotation.IntDef;

import com.madreain.hulk.config.HulkConfig;
import com.madreain.hulk.http.exception.NetWorkException;
import com.madreain.hulk.http.exception.ResultException;
import com.madreain.hulk.http.exception.ReturnCodeException;
import com.madreain.hulk.http.interceptor.IReturnCodeErrorInterceptor;
import com.madreain.hulk.mvp.IView;
import com.madreain.hulk.utils.NetworkUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：接口数据返回单个对象
 * description：
 */
public abstract class RSubscriber<T> extends RSubscriberAbstract<T>  {

    private IView iView;
    private String emptyMsg;
    private String errorMsg;

    //减缓枚举的使用
    @IntDef({NULL, TOAST, REPLACE})
    //定义被它所注解的注解保留多久
    // SOURCE:被编译器忽略  CLASS :注解将会被保留在Class文件中，但在运行时并不会被VM保留。这是默认行为，所有没有用Retention注解的注解，都会采用这种策略。 RUNTIME :保留至运行时。所以我们可以通过反射去获取注解信息。
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    //自定义Type注解
    @Type
    int type = NULL;//接口操作交互类型
    public static final int NULL = 0;//无交互
    public static final int TOAST = 1;//接口开始showDialogProgress()---->>接口结束 dismissDialog() 错误Toast
    public static final int REPLACE = 2;//接口开始showLoading()---->>接口结束 :成功：restore(),失败：showError();

    protected RSubscriber(IView iView) {
        this.iView = iView;
    }

    /**
     * @param iView
     * @param type     @param type One of {@link #NULL}, {@link #TOAST}, or {@link #REPLACE}.
     */
    public RSubscriber(IView iView, @Type int type) {
        this(iView, type, "暂无数据", "网络错误");
    }

    public RSubscriber(IView iView, @Type int type, String emptyMsg, String errorMsg) {
        this.iView = iView;
        this.type = type;
        this.emptyMsg = emptyMsg;
        this.errorMsg = errorMsg;
    }

    @Override
    protected void onStart() {
        super.onStart();
        switch (type) {
            case NULL:
                break;
            case TOAST:
                iView.showDialogProgress("");
                break;
            case REPLACE:
                if (!iView.isHasRestore()) {
                    iView.showLoading();
                }
                break;
        }
    }

    @Override
    public final void onNext(T t) {
        switch (type) {
            case NULL:
                break;
            case TOAST:
                iView.dismissDialog();
                break;
            case REPLACE:
                iView.restore();
                break;
        }
        _onNext(t);
    }

    @Override
    public void onError(Throwable t) {
        /*如果网络未连接不会调用flatMap 所以网络未连接不会出现ServerException错误*/
        if (!NetworkUtils.isConnected()) {
            _onNetWorkError();//没网
        } else {
            if (t instanceof NetWorkException) {
                _onNetWorkError();//没网
            } else if (t instanceof ReturnCodeException) {
                isIntercepted(t);
                _onReturnCodeError(((ReturnCodeException) t).getReturnCode(), t.getMessage());
            } else if (t instanceof ResultException) {
                _onTEmpty();
            } else {
                _onNetWorkError();//UnknownHostException 1：服务器地址错误；2：网络未连接
            }
        }
    }

    @Override
    public void retry() {
    }

    @Override
    public void _onTEmpty() {
        switch (type) {
            case NULL:
                break;
            case TOAST:
                iView.showToast(emptyMsg);
                iView.dismissDialog();
                break;
            case REPLACE:
                iView.showEmpty(emptyMsg, view -> retry());
                break;
        }
    }

    @Override
    public void _onNetWorkError() {
        switch (type) {
            case NULL:
                break;
            case TOAST:
                iView.showToast(errorMsg);
                iView.dismissDialog();
                break;
            case REPLACE:
                iView.showNetworkError(errorMsg, view -> retry());
                break;
        }
    }

    @Override
    public void _onReturnCodeError(String returnCode, String msg) {
        switch (type) {
            case NULL:
                break;
            case TOAST:
                iView.showToast(msg);
                iView.dismissDialog();
                break;
            case REPLACE:
                iView.showNetworkError(msg, view -> retry());
                break;
        }
    }

    @Override
    public void onComplete() {
        switch (type) {
            case NULL:
                break;
            case TOAST:
                iView.dismissDialog();
                break;
            case REPLACE:
                break;
        }
        dispose();
    }

    /**
     * 判断是否被拦截
     *
     * @param t Throwable
     */
    private boolean isIntercepted(Throwable t) {
        boolean isIntercepted = false;//是否被拦截了
        for (IReturnCodeErrorInterceptor interceptor : HulkConfig.getInterceptors()) {
            if (interceptor.intercept(((ReturnCodeException) t).getReturnCode())) {
                isIntercepted = true;
                interceptor.todo(iView, ((ReturnCodeException) t).getReturnCode(), t.getMessage());
                break;
            }
        }
        return isIntercepted;
    }

}
